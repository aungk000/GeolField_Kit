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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.StringValue;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.database.MyNotesLocationDb;
import me.aungkooo.geologist.dialog.CompassDialog;
import me.aungkooo.geologist.listener.OnCompassDialogListener;
import me.aungkooo.geologist.model.MyNotesLocation;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 11/21/17.
 */

public class LocationNewActivity extends BaseActivity implements LocationListener, OnCompassDialogListener
{
    @BindView(R.id.img_sample_result) ImageView imgSampleResult;
    @BindView(R.id.img_outcrop_result) ImageView imgOutcropResult;

    @BindView(R.id.layout_sample_result) CardView layoutSampleResult;
    @BindView(R.id.layout_outcrop_result) CardView layoutOutcropResult;

    @BindView(R.id.txt_sample_name) TextView txtSampleName;
    @BindView(R.id.txt_outcrop_name) TextView txtOutcropName;
    @BindView(R.id.edit_location_no) TextInputEditText editLocationNo;
    @BindView(R.id.edit_location_date) TextInputEditText editDate;
    @BindView(R.id.edit_location_time) TextInputEditText editTime;
    @BindView(R.id.edit_latitude) TextInputEditText editLatitude;
    @BindView(R.id.edit_longitude) TextInputEditText editLongitude;
    @BindView(R.id.edit_map_no) TextInputEditText editMapNo;
    @BindView(R.id.edit_lithology_note) TextInputEditText edtLithologyNote;
    @BindView(R.id.edit_bedding_foliation) TextInputEditText editBeddingFoliation;
    @BindView(R.id.edit_j1) TextInputEditText editJ1;
    @BindView(R.id.edit_j2) TextInputEditText editJ2;
    @BindView(R.id.edit_j3) TextInputEditText editJ3;
    @BindView(R.id.edit_fold_axis) TextInputEditText editFoldAxis;
    @BindView(R.id.edit_lineation) TextInputEditText editLineation;
    @BindView(R.id.edit_other_note) TextInputEditText editNote;

    @BindView(R.id.btn_gps_location) ImageButton btnGpsLocation;
    @BindView(R.id.btn_take_outcrop) Button btnTakeOutcrop;
    @BindView(R.id.btn_take_sample) Button btnTakeSample;
    @BindView(R.id.edit_outcrop_facing) TextInputEditText editOutcropFacing;
    @BindView(R.id.edit_sample_facing) TextInputEditText editSampleFacing;

    private AutoCompleteTextView autoRockUnit, autoTexture, autoWeatheringColor, autoFreshColor;
    private MultiAutoCompleteTextView multiAutoMineralComposition;
    private Spinner spinnerRockType, spinnerGrainSize;

    private final int REQUEST_IMAGE_SAMPLE = 1, REQUEST_IMAGE_OUTCROP = 2, REQUEST_LOCATION = 3,
            REQUEST_GET_CONTENT = 4;
    private String outcropName, outcropPath, sampleName, samplePath;
    private int traverseId;
    private String traverseTitle;
    private LocationManager locationManager;
    private MyNotesLocationDb locationDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_new);

        ButterKnife.bind(this, this);
        locationDb = new MyNotesLocationDb(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.new_location);
        }

        Intent intent = getIntent();
        if (intent != null) {
            int locationNo = intent.getIntExtra(MyNotesLocation.NO, 0);
            editLocationNo.setText(String.valueOf(locationNo));
            traverseId = intent.getIntExtra(Traverse.ID, 0);
            traverseTitle = intent.getStringExtra(Traverse.TITLE);

        }

        editDate.setText(Utility.getDate());
        editTime.setText(Utility.getTime());

        spinnerRockType = createSpinner(R.id.spinner_rock_type, R.array.rock_type);
        spinnerGrainSize = createSpinner(R.id.spinner_grain_size, R.array.grain_size);

        autoRockUnit = createAutoComplete(R.id.auto_rock_unit, StringValue.rockUnits);
        autoTexture = createAutoComplete(R.id.auto_texture, StringValue.textures);
        autoWeatheringColor = createAutoComplete(R.id.auto_weathering_color, StringValue.rockColors);
        autoFreshColor = createAutoComplete(R.id.auto_fresh_color, StringValue.rockColors);

        multiAutoMineralComposition = createMultiAutoCompleteComma(R.id.multi_auto_mineral_composition,
                StringValue.mineralComposition);

        setExpandToggle(R.id.layout_header_lithology, R.id.layout_lithology, R.id.img_lithology_toggle);
        setExpandToggle(R.id.layout_header_structures, R.id.layout_structures, R.id.img_structures_toggle);

        btnTakeSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureImageSample();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnTakeOutcrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureImageOutcrop();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnGpsLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });
    }

    private void requestLocation() {
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
    public void onDialogDismissed(int compassName, int direction, int axis, int slopeAngle) {
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

            case R.string.outcrop_facing:
                editOutcropFacing.setText(facing);
                break;

            case R.string.sample_facing:
                editSampleFacing.setText(facing);
                break;
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

            case R.id.btn_outcrop_facing:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.outcrop_facing);
                break;

            case R.id.btn_sample_facing:
                args.putInt(CompassDialog.COMPASS_NAME, R.string.sample_facing);
                break;
        }

        CompassDialog compassDialog = new CompassDialog();
        compassDialog.setArguments(args);
        compassDialog.show(getSupportFragmentManager(), CompassDialog.class.getSimpleName());
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case REQUEST_IMAGE_SAMPLE:
                    try {
                        captureImageSample();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_IMAGE_OUTCROP:
                    try {
                        captureImageOutcrop();
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

    private void captureImageOutcrop() throws IOException {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_OUTCROP
            );
        } else {
            /*Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null)
            {
                imageFile = null;
                try {
                    imageFile = createImageFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                if(imageFile != null)
                {
                    imageUri = Uri.fromFile(imageFile);

                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
                }
            }*/

            String imageFileName = "outcrop_" + traverseTitle + "_" + get(editLocationNo) +
                    "_" + Utility.getDay();
            outcropName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/MyNotes");
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            outcropPath = tempFile.getAbsolutePath();

            // put values as much as possible for better result
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Outcrop");
            values.put(MediaStore.Images.Media.DATA, outcropPath);

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_IMAGE_OUTCROP);
        }
    }

    private void captureImageSample() throws IOException {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                        this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_IMAGE_SAMPLE
            );
        } else {

            String imageFileName = "sample_" + traverseTitle + "_" + get(editLocationNo)
                    + "_" + Utility.getDay();
            sampleName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if (!storageDir.exists()) {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/MyNotes");
            if (!parentDir.exists()) {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            samplePath = tempFile.getAbsolutePath();

            // put values as much as possible for better result
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Sample");
            values.put(MediaStore.Images.Media.DATA, samplePath);

            Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            startActivityForResult(intent, REQUEST_IMAGE_SAMPLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_SAMPLE:
                    setImage(layoutSampleResult, imgSampleResult, txtSampleName, samplePath,
                            sampleName);
                    break;

                case REQUEST_IMAGE_OUTCROP:
                    setImage(layoutOutcropResult, imgOutcropResult, txtOutcropName, outcropPath,
                            outcropName);
                    break;

                case REQUEST_GET_CONTENT:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            switch (requestCode) {
                case REQUEST_IMAGE_SAMPLE:
                    try {
                        deleteImageFile(samplePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_IMAGE_OUTCROP:
                    try {
                        deleteImageFile(outcropPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning)
                .setMessage(R.string.discard_message)
                .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        data.putExtra("outcropPath", outcropPath);
                        data.putExtra("samplePath", samplePath);
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

    private void onDone() {
        String locationTitle = "Location " + get(editLocationNo);
        MyNotesLocation location = new MyNotesLocation(
                traverseId, locationTitle, get(editTime), get(editDate), get(editLatitude),
                get(editLongitude),
                get(editMapNo), get(spinnerRockType), get(autoRockUnit), outcropPath, outcropName,
                get(editOutcropFacing),
                get(autoTexture), get(autoWeatheringColor), get(autoFreshColor), get(spinnerGrainSize),
                get(multiAutoMineralComposition), get(edtLithologyNote), samplePath, sampleName,
                get(editSampleFacing),
                get(editBeddingFoliation), get(editJ1), get(editJ2), get(editJ3), get(editFoldAxis),
                get(editLineation), get(editNote)
        );

        int id = (int) locationDb.insertLocation(location);

        Intent intent = new Intent();
        intent.putExtra(MyNotesLocation.TITLE, locationTitle);
        intent.putExtra(MyNotesLocation.TIME, get(editTime));
        intent.putExtra(MyNotesLocation.ID, id);
        setResult(RESULT_OK, intent);

        finish();
        makeShortToast("Saved");
    }

    @Override
    public void onLocationChanged(Location location) {
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
            if (location == null) {
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
