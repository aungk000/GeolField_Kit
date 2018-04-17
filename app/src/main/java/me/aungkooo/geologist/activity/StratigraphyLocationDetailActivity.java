package me.aungkooo.geologist.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.database.StratigraphyLocationDb;
import me.aungkooo.geologist.model.StratigraphyLocation;

/**
 * Created by Ko Oo on 10/4/2018.
 */

public class StratigraphyLocationDetailActivity extends BaseActivity
{
    @BindView(R.id.txt_location_date) TextView txtDate;
    @BindView(R.id.txt_location_time) TextView txtTime;
    @BindView(R.id.txt_gps_location) TextView txtGps;
    @BindView(R.id.txt_formation_name) TextView txtFormation;
    @BindView(R.id.txt_lithology) TextView txtLithology;
    @BindView(R.id.txt_index_fossil) TextView txtFossil;
    @BindView(R.id.txt_age) TextView txtAge;
    @BindView(R.id.txt_formation_photo) TextView txtFormationPhoto;
    @BindView(R.id.txt_bedding_plane) TextView txtBeddingPlane;
    @BindView(R.id.txt_fold_axis) TextView txtFoldAxis;
    @BindView(R.id.txt_fault) TextView txtFault;
    @BindView(R.id.txt_joint) TextView txtJoint;
    @BindView(R.id.txt_rock_sample) TextView txtRockSample;
    @BindView(R.id.txt_fossil) TextView txtFossilPhoto;
    @BindView(R.id.txt_mineralization) TextView txtMineralization;
    @BindView(R.id.txt_ore) TextView txtOre;
    @BindView(R.id.txt_min_nature) TextView txtMinNature;
    @BindView(R.id.txt_ore_sample) TextView txtOreSample;
    @BindView(R.id.txt_note) TextView txtNote;

    @BindView(R.id.img_formation) ImageView imgFormation;
    @BindView(R.id.img_rock_sample) ImageView imgRockSample;
    @BindView(R.id.img_fossil) ImageView imgFossil;
    @BindView(R.id.img_ore_sample) ImageView imgOreSample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stratigraphy_location_detail);

        ButterKnife.bind(this, this);

        StratigraphyLocationDb locationDb = new StratigraphyLocationDb(this);

        Intent intent = getIntent();
        if(intent != null && getSupportActionBar() != null)
        {
            String title = intent.getStringExtra(StratigraphyLocation.TITLE);
            getSupportActionBar().setTitle(title);

            int locationId = intent.getIntExtra(StratigraphyLocation.ID, 0);

            Cursor cursor = locationDb.getLocation(locationId);
            cursor.moveToFirst();

            String date = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_DATE));
            String time = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_TIME));
            String latitude = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_LATITUDE));
            String longitude = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_LONGITUDE));

            String formation = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FORMATION));
            String lithology = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_LITHOLOGY));
            String fossil = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FOSSIL));
            String age = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_AGE));
            String fmPath = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FM_PATH));
            String fmName = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FM_NAME));

            String beddingPlane = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_BEDDING_PLANE));
            String foldAxis = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FOLD_AXIS));
            String fault = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_FAULT));
            String joint = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_JOINT));

            String rPath = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_R_PATH));
            String rName = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_R_NAME));
            String fPath = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_F_PATH));
            String fName = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_F_NAME));

            String min = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_MIN));
            String ore = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_ORE));
            String minNature = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_MIN_NATURE));
            String oPath = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_O_PATH));
            String oName = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_O_NAME));

            String note = cursor.getString(cursor.getColumnIndex(StratigraphyLocationDb.KEY_NOTE));

            cursor.close();

            String gps = "Latitude: " + latitude + "\nLongitude: " + longitude;

            txtDate.setText(date);
            txtTime.setText(time);
            txtGps.setText(gps);
            txtFormation.setText(formation);
            txtLithology.setText(lithology);
            txtFossil.setText(fossil);
            txtAge.setText(age);
            txtFormationPhoto.setText(fmName);
            txtBeddingPlane.setText(beddingPlane);
            txtFoldAxis.setText(foldAxis);
            txtFault.setText(fault);
            txtJoint.setText(joint);
            txtRockSample.setText(rName);
            txtFossilPhoto.setText(fName);
            txtMineralization.setText(min);
            txtOre.setText(ore);
            txtMinNature.setText(minNature);
            txtOreSample.setText(oName);
            txtNote.setText(note);

            if(fmPath != null)
            {
                try {
                    setScaledImage(imgFormation, fmPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(rPath != null)
            {
                try {
                    setScaledImage(imgRockSample, rPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fPath != null)
            {
                try {
                    setScaledImage(imgFossil, fPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(oPath != null)
            {
                try {
                    setScaledImage(imgOreSample, oPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
