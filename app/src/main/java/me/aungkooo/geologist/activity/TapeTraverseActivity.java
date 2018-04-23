package me.aungkooo.geologist.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.adapter.TapeLocationAdapter;
import me.aungkooo.geologist.database.TapeLocationDb;
import me.aungkooo.geologist.model.TapeLocation;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 20/4/2018.
 */

public class TapeTraverseActivity extends BaseActivity
{
    @BindView(R.id.recycler_view_location) RecyclerView recyclerView;
    @BindView(R.id.fab_location_add) FloatingActionButton fabAdd;

    private TapeLocationDb locationDb;
    private TapeLocationAdapter locationAdapter;
    private String traverseTitle;
    private int traverseId;
    private final int REQUEST_ACTIVITY = 1, REQUEST_FILE_PRINT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traverse);

        ButterKnife.bind(this, this);
        locationDb = new TapeLocationDb(this);

        Intent intent = getIntent();
        if(intent != null && getSupportActionBar() != null)
        {
            traverseTitle = intent.getStringExtra(Traverse.TITLE);
            getSupportActionBar().setTitle(traverseTitle);
            traverseId = intent.getIntExtra(Traverse.ID, 0);
        }

        ArrayList<TapeLocation> locationList = locationDb.getAllLocation(traverseId);
        locationAdapter = new TapeLocationAdapter(this, locationList, locationDb);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(locationAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState)
            {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState > 0)
                {
                    fabAdd.hide();
                }
                else {
                    fabAdd.show();
                }
            }
        });

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TapeTraverseActivity.this,
                        TapeLocationNewActivity.class);
                intent.putExtra(TapeLocation.STATION_NO, locationAdapter.getItemCount());
                intent.putExtra(TapeLocation.NO, locationAdapter.getItemCount() + 1);
                intent.putExtra(Traverse.ID, traverseId);

                if(intent.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(intent, REQUEST_ACTIVITY);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        locationDb.close();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case REQUEST_ACTIVITY:
                    addLocation(data);
                    break;
            }
        }
        else if(resultCode == RESULT_CANCELED)
        {
            switch (requestCode)
            {
                case REQUEST_ACTIVITY:
                    try {
                        removeImage(data);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void addLocation(Intent data)
    {
        int id = data.getIntExtra(TapeLocation.ID, 0);
        String title = data.getStringExtra(TapeLocation.TITLE);
        String time = data.getStringExtra(TapeLocation.TIME);

        locationAdapter.addLocation(new TapeLocation(id, title, time));
    }

    private void removeImage(Intent data) throws IOException
    {
        String photoPath = data.getStringExtra("photoPath");

        if(photoPath != null)
        {
            deleteImageFile(photoPath);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.traverse, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_print:
                try {
                    printTraverse(traverseTitle);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            switch (requestCode)
            {
                case REQUEST_FILE_PRINT:
                    try {
                        printTraverse(traverseTitle);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void printTraverse(String fileName) throws IOException
    {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_FILE_PRINT
            );
        }
        else {
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

            File tempFile = new File(parentDir, fileName + ".txt");
            tempFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            ArrayList<TapeLocation> printList = locationDb.printAllLocation(traverseId);

            for(TapeLocation each: printList)
            {
                String temp = each.getTitle() + "\n---------------\n" +
                        "Station no: " + each.getStationNo() + "\n" +
                        "Date: " + each.getDate() + "\n" +
                        "Time: " + each.getTime() + "\n" +
                        "Latitude: " + each.getLatitude() + "\n" +
                        "Longitude: " + each.getLongitude() + "\n" +

                        "Slope distance: " + each.getSlopeDistance() + "\n" +
                        "Bearing and Slope angle: " + each.getBearingSlope() + "\n" +
                        "Horizontal distance: " + each.getHorizontalDistance() + "\n" +
                        "Lithology: " + each.getLithology() + "\n" +
                        "Photo: " + each.getPhotoName() + "\n" +

                        "Bedding / Foliation: " + each.getBeddingFoliation() + "\n" +
                        "J1: " + each.getJ1() + "\n" +
                        "J2: " + each.getJ2() + "\n" +
                        "J3: " + each.getJ3() + "\n" +
                        "Fold axis: " + each.getFoldAxis() + "\n" +
                        "Lineation: " + each.getLineation() + "\n" +

                        "Note: " + each.getNote() + "\n";

                printWriter.println(temp);
            }

            printWriter.flush();
            printWriter.close();
            fileOutputStream.close();

            makeLongToast("File printed: " + tempFile.getAbsolutePath());
            addToMediaDatabase(tempFile.getAbsolutePath());
        }
    }
}
