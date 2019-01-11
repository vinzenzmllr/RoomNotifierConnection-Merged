package com.example.vinze.roomnotifierconnection.Activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.Adapter.MedikamentAdapter;
import com.example.vinze.roomnotifierconnection.Entities.Medikament;
import com.example.vinze.roomnotifierconnection.R;
import com.example.vinze.roomnotifierconnection.ViewModels.MedikamentViewModel;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String EXTRA_MEDIKAMENT_NAME = "com.daniel.lucidus.EXTRA_MEDIKAMENT_NAME";
    public static final String EXTRA_MEDIKAMENT_WIRKSTOFF = "com.daniel.lucidus.EXTRA_MEDIKAMENT_WIRKSTOFF";
    public static final String EXTRA_MEDIKAMENT_ANWENDUNG = "com.daniel.lucidus.EXTRA_MEDIKAMENT_ANWENDUNG";
    public static final String EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG = "com.daniel.lucidus.EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG";

    private MedikamentViewModel medikamentViewModel;

    private RelativeLayout relativeLayout;

    private static SearchActivity instance;
    private TextView begruessungTextView;

    final MedikamentAdapter adapter = new MedikamentAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        instance = this;

        begruessungTextView = findViewById(R.id.begruesungText);

        // ----------------------------------------------------------------

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        final ArrayList<Medikament> unfilteredList = new ArrayList<>();

        medikamentViewModel = ViewModelProviders.of(this).get(MedikamentViewModel.class);
        medikamentViewModel.getAllMedikamente().observe(this, new Observer<List<Medikament>>() {
            @Override
            public void onChanged(@Nullable List<Medikament> medikamente) {
                adapter.setMedikamente(medikamente);
                unfilteredList.addAll(adapter.getMedikamente());
            }
        });

        recyclerView.setVisibility(View.INVISIBLE);

        // ----------------------------------------------------------------

        final EditText suchText = findViewById(R.id.searchText);

        suchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("") || s.toString().isEmpty()) {
                    adapter.setMedikamente(unfilteredList);
                    recyclerView.setVisibility(View.INVISIBLE);
                    begruessungTextView.setVisibility(View.INVISIBLE);
                } else {
                    filter(s.toString());
                    recyclerView.setVisibility(View.VISIBLE);
                    begruessungTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        // -----------------------------------------------------------------------

        adapter.setOnItemClickListener(new MedikamentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Medikament medikament) {
                startActivity(medikament);
            }
        });

    }

    public void startActivity(Medikament medikament) {
        Intent medikamentIntent = new Intent(this, MedikamentActivity.class);

        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_NAME, medikament.getName());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_WIRKSTOFF, medikament.getWirkstoff());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_ANWENDUNG, medikament.getAnwendungsgebiet());
        medikamentIntent.putExtra(EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG, medikament.getVerschreibungspflichtig());

        startActivity(medikamentIntent);

    }

    private void filter(String text) {
        ArrayList<Medikament> filteredList = new ArrayList<>();

        for(Medikament med : adapter.getMedikamente()) {
            if (med.getName().toLowerCase().startsWith(text.toLowerCase())) {
                if (!filteredList.contains(med)) {
                    filteredList.add(med);
                }
            } else if (med.getName().toLowerCase().contains(text.toLowerCase())) {
                if (!filteredList.contains(med)) {
                    filteredList.add(med);
                }
            }
        }

        adapter.filterList(filteredList);
    }

    public List<Medikament> readCSV() {

        InputStream is = getResources().openRawResource(R.raw.medikamente);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8")));
        String line = "";
        String cvsSplitBy = ";";

        List<Medikament> medikamentList = new ArrayList<>();

        try {
            while ((line = br.readLine()) != null) {

                String[] a = line.split(cvsSplitBy);

                Medikament medikament = new Medikament(a[2], a[6], a[15], a[13]);

                medikamentList.add(medikament);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error: File not found");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return medikamentList;

    }

    public static SearchActivity getInstance() {
        return instance;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    Intent switcher = null;

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_reminder) {
            switcher = switcher == null? new Intent(this, ReminderActivity.class): switcher;
            startActivity(switcher);
            finish();

        }

        if(id == R.id.nav_search){
            switcher = switcher == null? new Intent(this, SearchActivity.class): switcher;
            startActivity(switcher);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}