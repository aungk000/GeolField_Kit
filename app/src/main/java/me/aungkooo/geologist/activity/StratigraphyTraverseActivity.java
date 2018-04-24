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
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.adapter.StratigraphyLocationAdapter;
import me.aungkooo.geologist.database.StratigraphyLocationDb;
import me.aungkooo.geologist.model.MyNotesLocation;
import me.aungkooo.geologist.model.StratigraphyLocation;
import me.aungkooo.geologist.model.Traverse;

/**
 * Created by Ko Oo on 9/4/2018.
 */

public class StratigraphyTraverseActivity extends BaseActivity
{
    @BindView(R.id.recycler_view_location) RecyclerView recyclerView;
    @BindView(R.id.fab_location_add) FloatingActionButton fabAdd;

    private StratigraphyLocationAdapter locationAdapter;
    private ArrayList<StratigraphyLocation> locationList;
    private final int REQUEST_ACTIVITY = 1, REQUEST_FILE_PRINT = 2;
    private int traverseId;
    private String traverseTitle;
    private StratigraphyLocationDb locationDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traverse);

        ButterKnife.bind(this, this);
        locationDb = new StratigraphyLocationDb(this);

        Intent intent = getIntent();
        if(intent != null && getSupportActionBar() != null)
        {
            traverseTitle = intent.getStringExtra(Traverse.TITLE);
            getSupportActionBar().setTitle(traverseTitle);
            traverseId = intent.getIntExtra(Traverse.ID, 0);
        }

        locationList = locationDb.getAllLocation(traverseId);
        locationAdapter = new StratigraphyLocationAdapter(this, locationList, locationDb);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,
                false));
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
                Intent intent = new Intent(StratigraphyTraverseActivity.this,
                        StratigraphyLocationNewActivity.class);
                intent.putExtra(StratigraphyLocation.NO, locationList.size() + 1);
                intent.putExtra(Traverse.ID, traverseId);
                intent.putExtra(Traverse.TITLE, traverseTitle);

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
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

    private void removeImage(Intent data) throws IOException
    {
        String photoPath = data.getStringExtra("photoPath");
        String rockSamplePath = data.getStringExtra("rockSamplePath");
        String fossilPath = data.getStringExtra("fossilPath");
        String oreSamplePath = data.getStringExtra("oreSamplePath");

        if(photoPath != null)
        {
            deleteImageFile(photoPath);
        }

        if(rockSamplePath != null)
        {
            deleteImageFile(rockSamplePath);
        }

        if(fossilPath != null)
        {
            deleteImageFile(fossilPath);
        }

        if(oreSamplePath != null)
        {
            deleteImageFile(oreSamplePath);
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

    public void addLocation(Intent data)
    {
        int id = data.getIntExtra(StratigraphyLocation.ID, 0);
        String title = data.getStringExtra(StratigraphyLocation.TITLE);
        String time = data.getStringExtra(StratigraphyLocation.TIME);

        locationList.add(new StratigraphyLocation(id, title, time));
        locationAdapter.notifyDataSetChanged();
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
            File parentDir = new File(storageDir, "/Stratigraphy");
            if(!parentDir.exists())
            {
                parentDir.mkdir();
            }

            File tempFile = new File(parentDir,  fileName + ".txt");
            tempFile.createNewFile();

            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
            PrintWriter printWriter = new PrintWriter(fileOutputStream);

            ArrayList<StratigraphyLocation> printList = locationDb.printAllLocation(traverseId);

            for(StratigraphyLocation each: printList)
            {
                String temp = each.getTitle() + "\n---------------\n" +
                        "Date: " + each.getDate() + "\n" +
                        "Time: " + each.getTime() + "\n" +
                        "Latitude: " + each.getLatitude() + "\n" +
                        "Longitude: " + each.getLongitude() + "\n" +

                        "Formation name: " + each.getFormation() + "\n" +
                        "Lithology: " + each.getLithology() + "\n" +
                        "Index fossil: " + each.getFossil() + "\n" +
                        "Age: " + each.getAge() + "\n" +
                        "Photo: " + each.getFormationName() + "\n" +

                        "Bedding plane: " + each.getBeddingPlane() + "\n" +
                        "Fold axis: " + each.getFoldAxis() + "\n" +
                        "Fault: " + each.getFault() + "\n" +
                        "Joint: " + each.getJoint() + "\n" +

                        "Rock sample: " + each.getRockName() + "\n" +
                        "Fossil: " + each.getFossilName() + "\n" +

                        "Mineralization: " + each.getMineralization() + "\n" +
                        "Ore name: " + each.getOre() + "\n" +
                        "Mineralization nature: " + each.getMineralizationNature() + "\n" +
                        "Ore sample: " + each.getOreName() + "\n" +

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
