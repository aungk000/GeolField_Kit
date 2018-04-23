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
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.StringValue;
import me.aungkooo.geologist.Utility;
import me.aungkooo.geologist.database.StratigraphyLocationDb;
import me.aungkooo.geologist.dialog.CompassDialog;
import me.aungkooo.geologist.listener.OnCompassDialogListener;
import me.aungkooo.geologist.model.StratigraphyLocation;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 10/4/2018.
 */

public class StratigraphyLocationNewActivity extends BaseActivity implements LocationListener,
        OnCompassDialogListener
{
    @BindView(R.id.edit_location_no) TextInputEditText editLocationNo;
    @BindView(R.id.edit_location_date) TextInputEditText editDate;
    @BindView(R.id.edit_location_time) TextInputEditText editTime;
    @BindView(R.id.edit_latitude) TextView editLatitude;
    @BindView(R.id.edit_longitude) TextView editLongitude;
    @BindView(R.id.edit_bedding_plane) TextInputEditText editBeddingPlane;
    @BindView(R.id.edit_fold_axis) TextInputEditText editFoldAxis;
    @BindView(R.id.edit_fault) TextInputEditText editFault;
    @BindView(R.id.edit_joint) TextInputEditText editJoint;
    @BindView(R.id.edit_economic_note) TextInputEditText editNote;

    private AutoCompleteTextView autoFormation;
    private AutoCompleteTextView autoIndexFossil;
    private AutoCompleteTextView autoAge;
    private MultiAutoCompleteTextView autoLithology;
    private AutoCompleteTextView autoMineralization;
    private AutoCompleteTextView autoOre;
    private AutoCompleteTextView autoMineralizationNature;

    @BindView(R.id.layout_photo_result) CardView layoutPhotoResult;
    @BindView(R.id.layout_rock_sample_result) CardView layoutRockSample;
    @BindView(R.id.layout_fossil_result) CardView layoutFossil;
    @BindView(R.id.layout_ore_sample_result) CardView layoutOreSample;

    @BindView(R.id.img_photo_result) ImageView imgPhotoResult;
    @BindView(R.id.img_rock_sample_result) ImageView imgRockSample;
    @BindView(R.id.img_fossil_result) ImageView imgFossil;
    @BindView(R.id.img_ore_sample_result) ImageView imgOreSample;

    @BindView(R.id.txt_photo_name) TextView txtPhotoName;
    @BindView(R.id.txt_rock_sample_name) TextView txtRockSample;
    @BindView(R.id.txt_fossil_name) TextView txtFossil;
    @BindView(R.id.txt_ore_sample_name) TextView txtOreSample;

    private final int REQUEST_LOCATION = 1, REQUEST_GET_CONTENT = 2, REQUEST_PHOTO = 3, REQUEST_ROCK_SAMPLE = 4,
            REQUEST_FOSSIL = 5, REQUEST_ORE_SAMPLE = 6;
    private LocationManager locationManager;
    private int locationNo, traverseId;
    private String photoName, photoPath, rockSampleName, rockSamplePath, fossilName, fossilPath,
    oreSampleName, oreSamplePath;
    private StratigraphyLocationDb locationDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stratigraphy_location_new);

        ButterKnife.bind(this, this);
        locationDb = new StratigraphyLocationDb(this);

        if(getSupportActionBar() != null)
        {
            getSupportActionBar().setTitle(R.string.new_location);
        }

        Intent intent = getIntent();
        if(intent != null)
        {
            locationNo = intent.getIntExtra(StratigraphyLocation.NO, 0);
            editLocationNo.setText(String.valueOf(locationNo));
            traverseId = intent.getIntExtra(Traverse.ID, 0);
        }

        autoFormation = createAutoComplete(R.id.auto_formation_name, StringValue.formationName);
        autoIndexFossil = createAutoComplete(R.id.auto_index_fossil, StringValue.fossil);
        autoAge = createAutoComplete(R.id.auto_age, StringValue.age);
        autoMineralization = createAutoComplete(R.id.auto_mineralization, StringValue.mineralization);
        autoOre = createAutoComplete(R.id.auto_ore_name, StringValue.oreName);
        autoMineralizationNature = createAutoComplete(R.id.auto_mineralization_nature,
                StringValue.mineralizationNature);

        autoLithology = createMultiAutoCompleteComma(R.id.auto_lithology, StringValue.lithology);

        Button btnTakePhoto = findViewById(R.id.btn_take_photo);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureFormationPhoto();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnTakeRockSample = findViewById(R.id.btn_take_rock_sample);
        btnTakeRockSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureRockSample();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnTakeFossil = findViewById(R.id.btn_take_fossil);
        btnTakeFossil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureFossil();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Button btnTakeOreSample = findViewById(R.id.btn_take_ore_sample);
        btnTakeOreSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    captureOreSample();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        editDate.setText(Utility.getDate());
        editTime.setText(Utility.getTime());

        ImageButton btnLocation = findViewById(R.id.btn_gps_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestLocation();
            }
        });

        setExpandToggle(R.id.layout_header_structures, R.id.layout_structures, R.id.img_structures_toggle);
        setExpandToggle(R.id.layout_header_economic, R.id.layout_economic, R.id.img_economic_toggle);
    }

    @Override
    public void onBackPressed() {
        createConfirmDialog().show();
    }

    private AlertDialog createConfirmDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.warning)
                .setMessage(R.string.discard_message)
                .setPositiveButton(R.string.discard, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent data = new Intent();
                        data.putExtra("photoPath", photoPath);
                        data.putExtra("rockSamplePath", rockSamplePath);
                        data.putExtra("fossilPath", fossilPath);
                        data.putExtra("oreSamplePath", oreSamplePath);
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

        return builder.create();
    }

    @Override
    protected void onStart() {
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        super.onStart();
    }

    @Override
    protected void onStop() {
        locationManager.removeUpdates(this);
        locationDb.close();

        super.onStop();
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

                case REQUEST_ROCK_SAMPLE:
                    setImage(layoutRockSample, imgRockSample, txtRockSample, rockSamplePath, rockSampleName);
                    break;

                case REQUEST_FOSSIL:
                    setImage(layoutFossil, imgFossil, txtFossil, fossilPath, fossilName);
                    break;

                case REQUEST_ORE_SAMPLE:
                    setImage(layoutOreSample, imgOreSample, txtOreSample, oreSamplePath, oreSampleName);
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

                case REQUEST_ROCK_SAMPLE:
                    try {
                        deleteImageFile(rockSamplePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_FOSSIL:
                    try {
                        deleteImageFile(fossilPath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_ORE_SAMPLE:
                    try {
                        deleteImageFile(oreSamplePath);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            switch (requestCode)
            {
                case REQUEST_PHOTO:
                    try {
                        captureFormationPhoto();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_ROCK_SAMPLE:
                    try {
                        captureRockSample();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_FOSSIL:
                    try {
                        captureFossil();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_ORE_SAMPLE:
                    try {
                        captureOreSample();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case REQUEST_LOCATION:
                    requestLocation();
                    break;
            }
        }
        else {
            makeShortToast("Permission denied");
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
    public void onDialogDismissed(int compassName, int direction, int axis, int slopeAngle)
    {
        String value = axis + DEGREE + "/" + direction + DEGREE;

        switch (compassName)
        {
            case R.string.bedding_plane:
                editBeddingPlane.setText(value);
                break;

            case R.string.fold_axis:
                editFoldAxis.setText(value);
                break;

            case R.string.fault:
                editFault.setText(value);
                break;

            case R.string.joint:
                editJoint.setText(value);
                break;
        }
    }

    public void onCompassClick(View v)
    {
        Bundle args = new Bundle();
        int id = v.getId();
        switch (id)
        {
            case R.id.btn_bedding_plane:
                args.putInt("compassName", R.string.bedding_plane);
                break;

            case R.id.btn_fold_axis:
                args.putInt("compassName", R.string.fold_axis);
                break;

            case R.id.btn_fault:
                args.putInt("compassName", R.string.fault);
                break;

            case R.id.btn_joint:
                args.putInt("compassName", R.string.joint);
                break;
        }

        CompassDialog compassDialog = new CompassDialog();
        compassDialog.setArguments(args);
        compassDialog.show(getSupportFragmentManager(), "CompassDialog");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        switch (id)
        {
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void captureFormationPhoto() throws IOException {
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
        else
        {
            String imageFileName = "FM_" + String.valueOf(traverseId) + "_" + String.valueOf(locationNo) +
                    "_" + Utility.getDay();
            photoName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/Stratigraphy");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            photoPath = tempFile.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Formation");
            values.put(MediaStore.Images.Media.DATA, photoPath);

            Uri photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, REQUEST_PHOTO);
        }
    }

    private void captureRockSample() throws IOException {
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
                    REQUEST_ROCK_SAMPLE
            );
        }
        else
        {
            String imageFileName = "R_" + String.valueOf(traverseId) + "_" + String.valueOf(locationNo) +
                    "_" + Utility.getDay();
            rockSampleName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/Stratigraphy");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            rockSamplePath = tempFile.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Rock Sample");
            values.put(MediaStore.Images.Media.DATA, rockSamplePath);

            Uri photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, REQUEST_ROCK_SAMPLE);
        }
    }

    private void captureFossil() throws IOException {
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
                    REQUEST_FOSSIL
            );
        }
        else
        {
            String imageFileName = "F_" + String.valueOf(traverseId) + "_" + String.valueOf(locationNo) +
                    "_" + Utility.getDay();
            fossilName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/Stratigraphy");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }
            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            fossilPath = tempFile.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Fossil");
            values.put(MediaStore.Images.Media.DATA, fossilPath);

            Uri photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, REQUEST_FOSSIL);
        }
    }

    private void captureOreSample() throws IOException {
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
                    REQUEST_ORE_SAMPLE
            );
        }
        else
        {
            String imageFileName = "O_" + String.valueOf(traverseId) + "_" + String.valueOf(locationNo) +
                    "_" + Utility.getDay();
            oreSampleName = imageFileName;

            File storageDir = new File(Environment.getExternalStorageDirectory(), "/Geologist");
            if(!storageDir.exists())
            {
                storageDir.mkdir();
            }
            File parentDir = new File(storageDir, "/Stratigraphy");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }
            File tempFile = new File(parentDir, imageFileName + JPG_FORMAT);
            tempFile.createNewFile();

            String dirPath = storageDir.getPath();
            String dirName = storageDir.getName();
            oreSamplePath = tempFile.getAbsolutePath();

            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, imageFileName);
            values.put(MediaStore.Images.Media.BUCKET_ID, dirPath.hashCode());
            values.put(MediaStore.Images.Media.BUCKET_DISPLAY_NAME, dirName);
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.DESCRIPTION, "Ore Sample");
            values.put(MediaStore.Images.Media.DATA, oreSamplePath);

            Uri photoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            startActivityForResult(intent, REQUEST_ORE_SAMPLE);
        }
    }

    private void onDone()
    {
        String locationTitle = "Location " + get(editLocationNo);
        StratigraphyLocation location = new StratigraphyLocation(
                traverseId, locationTitle, get(editTime), get(editDate), get(editLatitude),
                get(editLongitude), get(autoFormation), get(autoLithology), get(autoIndexFossil),
                get(autoAge), photoPath, photoName, get(editBeddingPlane), get(editFoldAxis),
                get(editFault), get(editJoint), rockSamplePath, rockSampleName, fossilPath,
                fossilName, get(autoMineralization), get(autoOre), get(autoMineralizationNature),
                oreSamplePath, oreSampleName, get(editNote)
        );

        int id = (int) locationDb.insertLocation(location);

        Intent intent = new Intent();
        intent.putExtra(StratigraphyLocation.TITLE, locationTitle);
        intent.putExtra(StratigraphyLocation.TIME, get(editTime));
        intent.putExtra(StratigraphyLocation.ID, id);
        setResult(RESULT_OK, intent);

        finish();
        makeShortToast("Saved");
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
        }
        else {
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
