package me.aungkooo.geologist.dialog;


import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;

import static android.content.Context.SENSOR_SERVICE;

public class CompassDialog extends DialogFragment implements SensorEventListener
{
    private SensorManager sensorManager;
    private float[] gravity = new float[3];
    private float[] geomagnetic = new float[3];
    private float[] rotationMatrix = new float[9];
    private float[] orientationAngles = new float[3];
    private Sensor accelerometer, magnetometer;
    private float[] acceleration = new float[3];
    private TextView txtDirection, txtAxis, txtTitle;
    private String axis;
    private EditText editBeddingFoliation, editJ1, editJ2, editJ3, editFoldAxis, editLineation,
    editBeddingPlane, editFault, editJoint;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int typeAccelerometer = Sensor.TYPE_ACCELEROMETER;
        int typeMagneticField = Sensor.TYPE_MAGNETIC_FIELD;
        sensorManager = (SensorManager) getContext().getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(typeAccelerometer);
        magnetometer = sensorManager.getDefaultSensor(typeMagneticField);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_compass, (ViewGroup) getView());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        AlertDialog dialog = builder.create();

        editBeddingFoliation = getActivity().findViewById(R.id.edit_bedding_foliation);
        editJ1 = getActivity().findViewById(R.id.edit_j1);
        editJ2 = getActivity().findViewById(R.id.edit_j2);
        editJ3 = getActivity().findViewById(R.id.edit_j3);
        editFoldAxis = getActivity().findViewById(R.id.edit_fold_axis);
        editLineation = getActivity().findViewById(R.id.edit_lineation);
        editBeddingPlane = getActivity().findViewById(R.id.edit_bedding_plane);
        editFault = getActivity().findViewById(R.id.edit_fault);
        editJoint = getActivity().findViewById(R.id.edit_joint);

        txtTitle = view.findViewById(R.id.txt_compass_title);
        txtDirection = view.findViewById(R.id.txt_compass_direction);
        txtAxis = view.findViewById(R.id.txt_compass_axis);
        Button btnSet = view.findViewById(R.id.btn_compass_set);

        final int name = getArguments().getInt("compassName");
        txtTitle.setText(name);

        btnSet.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                String direction = txtDirection.getText().toString();
                if(axis != null)
                {
                    String value = axis + "/" + direction;
                    switch (name)
                    {
                        case R.string.bedding_foliation:
                            editBeddingFoliation.setText("S0: " + value);
                            break;

                        case R.string.j1:
                            editJ1.setText(value);
                            break;

                        case R.string.j2:
                            editJ2.setText(value);
                            break;

                        case R.string.j3:
                            editJ3.setText(value);
                            break;

                        case R.string.fold_axis:
                            editFoldAxis.setText(value);
                            break;

                        case R.string.lineation:
                            editLineation.setText(value);
                            break;

                        case R.string.bedding_plane:
                            editBeddingPlane.setText(value);
                            break;

                        case R.string.fault:
                            editFault.setText(value);
                            break;

                        case R.string.joint:
                            editJoint.setText(value);
                            break;
                    }
                }
                dismiss();
            }
        });

        if (dialog.getWindow() != null)
        {
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if (event.sensor == accelerometer)
        {
            gravity = Utility.withLowPassFilter(event.values.clone());
            acceleration = Utility.withHighPassFilter(event.values.clone(), gravity);
        }
        else if (event.sensor == magnetometer)
        {
            System.arraycopy(event.values, 0, geomagnetic, 0, geomagnetic.length);
        }

        orientationAngles = Utility.updateOrientationAngles(rotationMatrix, acceleration, geomagnetic);

        for (int i = 0; i < 3; i++) {
            orientationAngles[i] = Utility.convertToDegree(orientationAngles[i]);
        }

        orientationAngles[0] = Utility.normalizeDegree(orientationAngles[0], 360.0f);
        orientationAngles[1] = Math.abs(orientationAngles[1]);
        orientationAngles[2] = Math.abs(orientationAngles[2]);

        orientationAngles[1] = Utility.normalizeAxis(orientationAngles[1], 90.0f);
        orientationAngles[2] = Utility.normalizeAxis(orientationAngles[2], 90.0f);

        int heading = (int) orientationAngles[0];
        int y = (int) orientationAngles[1];
        int x = (int) orientationAngles[2];

        /*if (y > x) {
            txtAxis.setText("X: " + String.format(Locale.US, "%.1f \u00b0", x) +
                    "\nY: " + String.format(Locale.US, "%.1f \u00b0", y));

            axis = String.format(Locale.US, "%.1f \u00b0", y);
        }
        else if (x > y) {
            txtAxis.setText("X: " + String.format(Locale.US, "%.1f \u00b0", x) +
                    "\nY: " + String.format(Locale.US, "%.1f \u00b0", y));

            axis = String.format(Locale.US, "%.1f \u00b0", x);
        }*/

        txtAxis.setText("X: " + x + "\u00b0" + "\nY: " + y + "\u00b0");

        if (y > x) {
            axis = y + "\u00b0";
        }
        else if (x > y) {
            axis = x + "\u00b0";
        }
        else {
            axis = x + "\u00b0";
        }

        txtDirection.setText(heading + "\u00b0");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);

        sensorManager.unregisterListener(this);
    }
}
