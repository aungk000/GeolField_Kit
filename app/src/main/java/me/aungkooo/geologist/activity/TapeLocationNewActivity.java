package me.aungkooo.geologist.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.StringValue;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.database.TapeLocationDb;
import me.aungkooo.geologist.dialog.CompassDialog;
import me.aungkooo.geologist.listener.OnCompassDialogListener;
import me.aungkooo.geologist.model.TapeLocation;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeLocationNewActivity extends BaseActivity implements LocationListener,
        OnCompassDialogListener
{
    @BindView(R.id.edit_location_no) TextInputEditText editLocationNo;
    @BindView(R.id.edit_station_no) TextInputEditText editStationNo;
    @BindView(R.id.edit_location_date) TextInputEditText editLocationDate;
    @BindView(R.id.edit_location_time) TextInputEditText editLocationTime;
    @BindView(R.id.edit_latitude) TextInputEditText editLatitude;
    @BindView(R.id.edit_longitude) TextInputEditText editLongitude;
    @BindView(R.id.edit_slope_distance) TextInputEditText editSlopDistance;
    @BindView(R.id.edit_bearing_slope) TextInputEditText editBearingSlope;
    @BindView(R.id.edit_horizontal_distance) TextInputEditText editHorizontalDistance;
    @BindView(R.id.edit_note) TextInputEditText editNote;
    @BindView(R.id.edit_bedding_foliation) TextInputEditText editBeddingFoliation;
    @BindView(R.id.edit_j1) TextInputEditText editJ1;
    @BindView(R.id.edit_j2) TextInputEditText editJ2;
    @BindView(R.id.edit_j3) TextInputEditText editJ3;
    @BindView(R.id.edit_fold_axis) TextInputEditText editFoldAxis;
    @BindView(R.id.edit_lineation) TextInputEditText editLineation;
    @BindView(R.id.edit_photo_facing) TextInputEditText editPhotoFacing;

    @BindView(R.id.layout_photo_result) CardView layoutPhotoResult;
    @BindView(R.id.img_photo_result) ImageView imgPhotoResult;
    @BindView(R.id.txt_photo_name) TextView txtPhotoName;

    @BindView(R.id.btn_take_photo) Button btnTakePhoto;
    @BindView(R.id.btn_gps_location) ImageButton btnGpsLocation;

    private MultiAutoCompleteTextView autoLithology;

    private final int REQUEST_GET_CONTENT = 1, REQUEST_LOCATION = 2, REQUEST_PHOTO = 3;
    private TapeLocationDb locationDb;
    private LocationManager locationManager;
    private int traverseId;
    private String traverseTitle;
    private String photoPath, photoName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape_location_new);

        ButterKnife.bind(this, this);
        locationDb = new TapeLocationDb(this);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(R.string.new_location);
        }

        Intent intent = getIntent();
        if(intent != null)
        {
            int locationNo = intent.getIntExtra(TapeLocation.NO, 0);
            editLocationNo.setText(String.valueOf(locationNo));
            traverseId = intent.getIntExtra(Traverse.ID, 0);
            int stationNo = intent.getIntExtra(TapeLocation.STATION_NO, 0);
            editStationNo.setText(String.valueOf(stationNo));
            traverseTitle = intent.getStringExtra(Traverse.TITLE);
        }

        autoLithology = createMultiAutoCompleteComma(R.id.auto_lithology, StringValue.rockUnits);

        editLocationDate.setText(Utility.getDate());
        editLocationTime.setText(Utility.getTime());

        btnGpsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    capturePhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setExpandToggle(R.id.layout_header_structures, R.id.layout_structures, R.id.img_structures_toggle);
    }

    @Override
    protected void onStart() {
        super.onStart();

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        locationManager.removeUpdates(this);
        locationDb.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_PHOTO:
                    setImage(layoutPhotoResult, imgPhotoResult, txtPhotoName, photoPath, photoName);
                    break;

                case REQUEST_GET_CONTENT:
                    break;
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case REQUEST_PHOTO:
                    try {
                        deleteImageFile(photoPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            switch (requestCode)
            {
                case REQUEST_PHOTO:
                    try {
                        capturePhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_LOCATION:
                    requestLocation();
                    break;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void capturePhoto() throws IOException {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PHOTO
            );
        }
        else {

            String imageFileName = "TP_" + traverseTitle + "_" + get(editLocationNo)
                    + "_" + Utility.getDay();
            photoName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/Tape");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            photoPath = tempFile.getAbsolutePath();

            // put values as much as possible for better result
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "TP");
            values.put(MediaStore.Images.Media.DATA, photoPath);

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_PHOTO);
        }
    }

    private void requestLocation()
    {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION
            );
        } else {
            locationManager.requestLocationUpdates(LOCATION_PROVIDER, 15000, 10, this);
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning)
                .setMessage(R.string.discard_message)
                .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        data.putExtra("photoPath", photoPath);
                        setResult(RESULT_CANCELED, data);

                        finish();
                        makeShortToast("Discarded");
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_attach:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, REQUEST_GET_CONTENT);
                return true;

            case R.id.menu_location_done:
                onDone();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onDone()
    {
        if(get(editSlopDistance).isEmpty())
        {
            editSlopDistance.setError("This field is required");
            editSlopDistance.requestFocus();
            return;
        }
        else if(get(editHorizontalDistance).isEmpty())
        {
            editHorizontalDistance.setError("This field is required");
            editHorizontalDistance.requestFocus();
            return;
        }

        String locationTitle = "Location " + get(editLocationNo);
        TapeLocation location = new TapeLocation(
                traverseId, getInt(editStationNo), locationTitle, get(editLocationDate),
                get(editLocationTime), get(editLatitude), get(editLongitude), get(editBearingSlope),
                get(autoLithology), photoPath, photoName, get(editPhotoFacing),
                get(editBeddingFoliation), get(editJ1),
                get(editJ2), get(editJ3), get(editFoldAxis), get(editLineation), get(editNote),
                getDouble(editHorizontalDistance), getDouble(editSlopDistance)
        );

        int id = (int) locationDb.insertLocation(location);

        Intent intent = new Intent();
        intent.putExtra(TapeLocation.TITLE, locationTitle);
        intent.putExtra(TapeLocation.TIME, get(editLocationTime));
        intent.putExtra(TapeLocation.ID, id);
        setResult(RESULT_OK, intent);

        finish();
        makeShortToast("Saved");
    }

    @Override
    public void onDialogDismissed(int compassName, int direction, int axis, int slopeAngle)
    {
        String value = axis + DEGREE + "/" + direction + DEGREE;
        String facing = direction + DEGREE;

        switch (compassName)
        {
            case R.string.bedding_foliation:
                String bedding = "S0: " + value;
                editBeddingFoliation.setText(bedding);
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

            case R.string.bearing_and_slope_angle:
                calcHorizontalDistance(direction, slopeAngle);
                break;

            case R.string.photo_facing:
                editPhotoFacing.setText(facing);
                break;
        }
    }

    private void calcHorizontalDistance(int direction, int slopeAngle)
    {
        if (!get(editSlopDistance).isEmpty())
        {
            String bearingSlope = direction + DEGREE + "/" + slopeAngle + DEGREE;
            editBearingSlope.setText(bearingSlope);

            double slopeDistance = getDouble(editSlopDistance);
            double angle = Math.toRadians(slopeAngle);
            if(slopeAngle != 0)
            {
                double horizontalDistance = slopeDistance * Math.cos(angle);
                editHorizontalDistance.setText(String.format(Locale.ENGLISH, "%.4f", horizontalDistance));
            }
            else {
                editBearingSlope.setError("Slope angle can not be zero");
            }
        }
        else {
            editSlopDistance.setError("This field is required");
            editSlopDistance.requestFocus();
        }
    }

    public void onCompassClick(View v)
    {
        Bundle args = new Bundle();
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_bedding_foliation:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.bedding_foliation);
                break;

            case R.id.btn_j1:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.j1);
                break;

            case R.id.btn_j2:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.j2);
                break;

            case R.id.btn_j3:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.j3);
                break;

            case R.id.btn_fold_axis:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.fold_axis);
                break;

            case R.id.btn_lineation:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.lineation);
                break;

            case R.id.btn_bearing_slope:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.bearing_and_slope_angle);
                break;

            case R.id.btn_photo_facing:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.photo_facing);
                break;
        }

        CompassDialog compassDialog = new CompassDialog();
        compassDialog.setArguments(args);
        compassDialog.show(getSupportFragmentManager(), CompassDialog.class.getSimpleName());
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION
            );
        } else {
            if(location == null)
            {
                location = locationManager.getLastKnownLocation(LOCATION_PROVIDER);
            }

            editLatitude.setText(String.valueOf(location.getLatitude()));
            editLongitude.setText(String.valueOf(location.getLongitude()));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        requestLocation();
    }

    @Override
    public void onProviderDisabled(String provider) {
        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }
}
