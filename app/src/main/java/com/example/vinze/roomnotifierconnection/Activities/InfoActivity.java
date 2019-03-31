package com.example.vinze.roomnotifierconnection.Activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.example.vinze.roomnotifierconnection.R;

public class InfoActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private Intent switchToReminder;
    private Intent switchToSearch;
    private Intent switchToInfo;
    private Intent switchToUserData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_reminder) {
            if(switchToReminder == null){
                switchToReminder = new Intent(this, ReminderActivity.class);
                startActivity(switchToReminder);

            }
            else{
                startActivity(switchToReminder);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_search){
            if(switchToSearch == null){
                switchToSearch = new Intent(this, SearchActivity.class);
                startActivity(switchToSearch);

            }
            else{
                startActivity(switchToSearch);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_info){
            if(switchToInfo == null){
                switchToInfo = new Intent(this, InfoActivity.class);
                startActivity(switchToInfo);

            }
            else{
                startActivity(switchToInfo);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_userdata){
            if(switchToUserData == null){
                switchToUserData = new Intent(this, UserDataActivity.class);
                startActivity(switchToUserData);

            }
            else{
                startActivity(switchToUserData);
                System.out.println("not new");
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
