package me.aungkooo.geologist.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.database.MyNotesLocationDb;
import me.aungkooo.geologist.model.FirebaseLocationModel;
import me.aungkooo.geologist.model.MyNotesLocation;

/**
 * Created by Ko Oo on 11/21/17.
 */

public class LocationDetailActivity extends BaseActivity
{
    @BindView(R.id.txt_date) TextView txtDate;
    @BindView(R.id.txt_time) TextView txtTime;
    @BindView(R.id.txt_gps) TextView txtGps;
    @BindView(R.id.txt_map) TextView txtMap;
    @BindView(R.id.txt_rock_type) TextView txtRockType;
    @BindView(R.id.txt_rock_unit) TextView txtRockUnit;
    @BindView(R.id.txt_outcrop) TextView txtOutcrop;
    @BindView(R.id.txt_texture) TextView txtTexture;
    @BindView(R.id.txt_weathering_color) TextView txtWeatheringColor;
    @BindView(R.id.txt_fresh_color) TextView txtFreshColor;
    @BindView(R.id.txt_grain_size) TextView txtGrainSize;
    @BindView(R.id.txt_mineral_composition) TextView txtMineralComposition;
    @BindView(R.id.txt_lithology_note) TextView txtLithologyNote;
    @BindView(R.id.txt_sample) TextView txtSample;
    @BindView(R.id.txt_bedding_foliation) TextView txtBeddingFoliation;
    @BindView(R.id.txt_j1) TextView txtJ1;
    @BindView(R.id.txt_j2) TextView txtJ2;
    @BindView(R.id.txt_j3) TextView txtJ3;
    @BindView(R.id.txt_fold_axis) TextView txtFoldAxis;
    @BindView(R.id.txt_lineation) TextView txtLineation;
    @BindView(R.id.txt_note) TextView txtNote;
    @BindView(R.id.txt_outcrop_facing) TextView txtOutcropFacing;
    @BindView(R.id.txt_sample_facing) TextView txtSampleFacing;

    @BindView(R.id.img_outcrop) ImageView imgOutcrop;
    @BindView(R.id.img_sample) ImageView imgSample;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_location_detail);

        ButterKnife.bind(this, this);
        MyNotesLocationDb locationDb = new MyNotesLocationDb(this);

        Intent intent = getIntent();
        if(intent != null && getSupportActionBar() != null)
        {
            String title = intent.getStringExtra(MyNotesLocation.TITLE);
            getSupportActionBar().setTitle(title);

            int locationId = intent.getIntExtra(MyNotesLocation.ID, 0);
            MyNotesLocation location = locationDb.getLocation(locationId);

            String gps = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();

            txtDate.setText(location.getDate());
            txtTime.setText(location.getTime());
            txtGps.setText(gps);
            txtMap.setText(location.getMap());

            txtRockType.setText(location.getRockType());
            txtRockUnit.setText(location.getRockUnit());
            txtOutcrop.setText(location.getOutcropName());
            String outcropFacing = "Photo facing: " + location.getOutcropFacing();
            txtOutcropFacing.setText(outcropFacing);

            txtTexture.setText(location.getTexture());
            txtWeatheringColor.setText(location.getWeatheringColor());
            txtFreshColor.setText(location.getFreshColor());
            txtGrainSize.setText(location.getGrainSize());
            txtMineralComposition.setText(location.getMineralComposition());
            txtLithologyNote.setText(location.getLithologyNote());
            txtSample.setText(location.getSampleName());
            String sampleFacing = "Photo facing: " + location.getSampleFacing();
            txtSampleFacing.setText(sampleFacing);

            txtBeddingFoliation.setText(location.getBeddingFoliation());
            txtJ1.setText(location.getJ1());
            txtJ2.setText(location.getJ2());
            txtJ3.setText(location.getJ3());
            txtFoldAxis.setText(location.getFoldAxis());
            txtLineation.setText(location.getLineation());

            txtNote.setText(location.getNote());

            String outcropPath = location.getOutcropPath();
            String samplePath = location.getSamplePath();

            if(outcropPath != null)
            {
                try {
                    setScaledImage(imgOutcrop, outcropPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(samplePath != null)
            {
                try {
                    setScaledImage(imgSample, samplePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.location_detail, menu);

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

            case R.id.menu_send:
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference postsRef = database.getReference().child("gpslogist_location");
                DatabaseReference newPostRef = postsRef.push();
                newPostRef.setValue(new FirebaseLocationModel(get(txtRockUnit), get(txtRockType), get(txtGps)));

                makeShortToast("Sent to server");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
