package me.aungkooo.geologist.dialog;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.listener.OnCompassDialogListener;


public class CompassDialog extends DialogFragment implements SensorEventListener
{
    @BindView(R.id.txt_compass_title) TextView txtCompassTitle;
    @BindView(R.id.txt_compass_direction) TextView txtCompassDirection;
    @BindView(R.id.txt_compass_axis) TextView txtCompassAxis;
    @BindView(R.id.btn_compass_set) Button btnCompassSet;
    Unbinder unbinder;

    public static final String COMPASS_NAME = "compassName";
    private String DEGREE = "\u00b0";
    private SensorManager sensorManager;
    private float[] gravity = new float[3], geomagnetic = new float[3], rotationMatrix = new float[9],
            orientationAngles = new float[3], acceleration = new float[3];
    private Sensor accelerometer, magnetometer;
    private int axis, direction, slope;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int typeAccelerometer = Sensor.TYPE_ACCELEROMETER;
        int typeMagneticField = Sensor.TYPE_MAGNETIC_FIELD;
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(typeAccelerometer);
            magnetometer = sensorManager.getDefaultSensor(typeMagneticField);
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_compass, (ViewGroup) getView());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        unbinder = ButterKnife.bind(this, view);

        final int compassName = getArguments().getInt(COMPASS_NAME);
        txtCompassTitle.setText(compassName);

        final OnCompassDialogListener listener = (OnCompassDialogListener) getActivity();
        btnCompassSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listener.onDialogDismissed(compassName, direction, axis, slope);

                dismiss();
            }
        });

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setWindowAnimations(R.style.DialogAnimation);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == accelerometer) {
            gravity = Utility.withLowPassFilter(event.values.clone());
            acceleration = Utility.withHighPassFilter(event.values.clone(), gravity);
        } else if (event.sensor == magnetometer) {
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

        direction = heading;
        slope = y;

        String xy = "X: " + x + DEGREE + "\nY: " + y + DEGREE;
        String bearing = heading + DEGREE;

        txtCompassAxis.setText(xy);
        txtCompassDirection.setText(bearing);

        if (y > x) {
            axis = y;
        } else if (x > y) {
            axis = x;
        } else {
            axis = x;
        }
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
