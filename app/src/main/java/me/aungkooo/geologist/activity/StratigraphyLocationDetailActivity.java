package me.aungkooo.geologist.activity;

import android.content.Intent;
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
    @BindView(R.id.txt_formation_facing) TextView txtFormationFacing;
    @BindView(R.id.txt_rock_facing) TextView txtRockFacing;
    @BindView(R.id.txt_fossil_facing) TextView txtFossilFacing;
    @BindView(R.id.txt_ore_facing) TextView txtOreFacing;

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

            StratigraphyLocation location = locationDb.getLocation(locationId);

            String gps = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();

            txtDate.setText(location.getDate());
            txtTime.setText(location.getTime());
            txtGps.setText(gps);
            txtFormation.setText(location.getFormation());
            txtLithology.setText(location.getLithology());
            txtFossil.setText(location.getFossil());
            txtAge.setText(location.getAge());
            txtFormationPhoto.setText(location.getFormationName());
            String formationFacing = "Photo facing: " + location.getFormationFacing();
            txtFormationFacing.setText(formationFacing);

            txtBeddingPlane.setText(location.getBeddingPlane());
            txtFoldAxis.setText(location.getFoldAxis());
            txtFault.setText(location.getFault());
            txtJoint.setText(location.getJoint());

            txtRockSample.setText(location.getRockName());
            String rockFacing = "Photo facing: " + location.getRockFacing();
            txtRockFacing.setText(rockFacing);
            txtFossilPhoto.setText(location.getFossilName());
            String fossilFacing = "Photo facing: " + location.getFossilFacing();
            txtFossilFacing.setText(fossilFacing);

            txtMineralization.setText(location.getMineralization());
            txtOre.setText(location.getOre());
            txtMinNature.setText(location.getMineralizationNature());
            txtOreSample.setText(location.getOreName());
            String oreFacing = "Photo facing: " + location.getOreFacing();
            txtOreFacing.setText(oreFacing);

            txtNote.setText(location.getNote());

            String formationPath = location.getFormationPath();
            String rockPath = location.getRockPath();
            String fossilPath = location.getFossilPath();
            String orePath = location.getOrePath();

            if(formationPath != null)
            {
                try {
                    setScaledImage(imgFormation, formationPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(rockPath != null)
            {
                try {
                    setScaledImage(imgRockSample, rockPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fossilPath != null)
            {
                try {
                    setScaledImage(imgFossil, fossilPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(orePath != null)
            {
                try {
                    setScaledImage(imgOreSample, orePath);
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
