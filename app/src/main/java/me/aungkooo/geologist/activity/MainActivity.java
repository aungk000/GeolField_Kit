package me.aungkooo.geologist.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.aungkooo.geologist.R;
import me.aungkooo.geologist.fragment.FieldNoteFragment;
import me.aungkooo.geologist.fragment.StratigraphyFragment;
import me.aungkooo.geologist.fragment.TapeAndCompassFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.toolbar_main) Toolbar toolbarMain;
    @BindView(R.id.navigation_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    int toolbarTitle = R.string.field_note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(toolbarTitle);
        }

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_main, new FieldNoteFragment())
                    .commit();
        }

        setupDrawerLayout();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            super.onBackPressed();
        }
    }

    private void setupDrawerLayout() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbarMain,
                R.string.open_drawer,
                R.string.close_drawer
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(R.string.app_name);
                }
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(toolbarTitle);
                }
            }
        };

        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_field_note:
                changeNavigationMenu(R.string.field_note, new FieldNoteFragment());
                return true;

            case R.id.menu_tape_and_compass:
                changeNavigationMenu(R.string.tape_and_compass, new TapeAndCompassFragment());
                return true;

            case R.id.menu_stratigraphy:
                changeNavigationMenu(R.string.stratigraphy, new StratigraphyFragment());
                return true;

            default:
                return false;
        }
    }

    private void changeNavigationMenu(@StringRes int title, Fragment fragment) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        toolbarTitle = title;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_main, fragment)
                .commit();

        drawerLayout.closeDrawer(navigationView);
    }
}
