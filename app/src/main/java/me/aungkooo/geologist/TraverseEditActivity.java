package me.aungkooo.geologist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import me.aungkooo.geologist.model.Traverse;


public class TraverseEditActivity extends AppCompatActivity
{
    private TextInputEditText editTitle, editDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traverse_edit);

        editTitle = (TextInputEditText) findViewById(R.id.edit_traverse_title_edit);
        editDate = (TextInputEditText) findViewById(R.id.edit_traverse_date_edit);

        Intent intent = getIntent();
        if(intent != null)
        {
            editTitle.setText(intent.getStringExtra(Traverse.TITLE));
            editDate.setText(intent.getStringExtra(Traverse.DATE));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.traverse_edit, menu);
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

            case R.id.menu_traverse_edit_done:
                onBackPressed();
                Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
