package me.aungkooo.geologist;

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
import me.aungkooo.geologist.activity.BaseActivity;
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

            Cursor cursor = locationDb.getLocation(locationId);
            cursor.moveToFirst();

            String date = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_DATE));
            String time = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_TIME));
            String latitude = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_LATITUDE));
            String longitude = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_LONGITUDE));
            String map = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_MAP));

            String rockType = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_ROCK_TYPE));
            String rockUnit = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_ROCK_UNIT));
            String outcropPath = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_OUTCROP_PATH));
            String outcropName = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_OUTCROP_NAME));

            String texture = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_TEXTURE));
            String weatheringColor = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_WEATHERING_COLOR));
            String freshColor = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_FRESH_COLOR));
            String grainSize = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_GRAIN_SIZE));
            String mineralComposition = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_MINERAL_COMPOSITION));
            String lithologyNote = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_LITHOLOGY_NOTE));
            String samplePath = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_SAMPLE_PATH));
            String sampleName = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_SAMPLE_NAME));

            String beddingFoliation = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_BEDDING_FOLIATION));
            String j1 = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_J1));
            String j2 = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_J2));
            String j3 = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_J3));
            String foldAxis = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_FOLD_AXIS));
            String lineation = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_LINEATION));

            String note = cursor.getString(cursor.getColumnIndex(MyNotesLocationDb.KEY_NOTE));

            cursor.close();

            String gps = "Latitude: " + latitude + "\nLongitude: " + longitude;

            txtDate.setText(date);
            txtTime.setText(time);
            txtGps.setText(gps);
            txtMap.setText(map);

            txtRockType.setText(rockType);
            txtRockUnit.setText(rockUnit);
            txtOutcrop.setText(outcropName);

            txtTexture.setText(texture);
            txtWeatheringColor.setText(weatheringColor);
            txtFreshColor.setText(freshColor);
            txtGrainSize.setText(grainSize);
            txtMineralComposition.setText(mineralComposition);
            txtLithologyNote.setText(lithologyNote);
            txtSample.setText(sampleName);

            txtBeddingFoliation.setText(beddingFoliation);
            txtJ1.setText(j1);
            txtJ2.setText(j2);
            txtJ3.setText(j3);
            txtFoldAxis.setText(foldAxis);
            txtLineation.setText(lineation);

            txtNote.setText(note);

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
