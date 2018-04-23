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
import me.aungkooo.geologist.database.TapeLocationDb;
import me.aungkooo.geologist.model.TapeLocation;

/**
 * Created by Ko Oo on 22/4/2018.
 */

public class TapeLocationDetailActivity extends BaseActivity
{
    @BindView(R.id.txt_station_no) TextView txtStationNo;
    @BindView(R.id.txt_date) TextView txtDate;
    @BindView(R.id.txt_time) TextView txtTime;
    @BindView(R.id.txt_gps) TextView txtGps;
    @BindView(R.id.txt_slope_distance) TextView txtSlopeDistance;
    @BindView(R.id.txt_bearing_slope) TextView txtBearingSlope;
    @BindView(R.id.txt_horizontal_distance) TextView txtHorizontalDistance;
    @BindView(R.id.txt_lithology) TextView txtLithology;
    @BindView(R.id.txt_bedding_foliation) TextView txtBeddingFoliation;
    @BindView(R.id.txt_j1) TextView txtJ1;
    @BindView(R.id.txt_j2) TextView txtJ2;
    @BindView(R.id.txt_j3) TextView txtJ3;
    @BindView(R.id.txt_fold_axis) TextView txtFoldAxis;
    @BindView(R.id.txt_lineation) TextView txtLineation;
    @BindView(R.id.img_photo_result) ImageView imgPhotoResult;
    @BindView(R.id.txt_photo_name) TextView txtPhotoName;
    @BindView(R.id.txt_note) TextView txtNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tape_location_detail);
        ButterKnife.bind(this);

        TapeLocationDb locationDb = new TapeLocationDb(this);

        Intent intent = getIntent();
        if(intent != null && getSupportActionBar() != null)
        {
            String title = intent.getStringExtra(TapeLocation.TITLE);
            getSupportActionBar().setTitle(title);

            int locationId = intent.getIntExtra(TapeLocation.ID, 0);
            TapeLocation location = locationDb.getLocation(locationId);

            String gps = "Latitude: " + location.getLatitude() + "\nLongitude: " + location.getLongitude();

            txtStationNo.setText(String.valueOf(location.getStationNo()));
            txtDate.setText(location.getDate());
            txtTime.setText(location.getTime());
            txtGps.setText(gps);

            txtSlopeDistance.setText(String.valueOf(location.getSlopeDistance()));
            txtBearingSlope.setText(location.getBearingSlope());
            txtHorizontalDistance.setText(String.valueOf(location.getHorizontalDistance()));
            txtLithology.setText(location.getLithology());

            txtBeddingFoliation.setText(location.getBeddingFoliation());
            txtJ1.setText(location.getJ1());
            txtJ2.setText(location.getJ2());
            txtJ3.setText(location.getJ3());
            txtFoldAxis.setText(location.getFoldAxis());
            txtLineation.setText(location.getLineation());

            txtPhotoName.setText(location.getPhotoName());
            txtNote.setText(location.getNote());

            String photoPath = location.getPhotoPath();

            if(photoPath != null)
            {
                try {
                    setScaledImage(imgPhotoResult, photoPath);
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
