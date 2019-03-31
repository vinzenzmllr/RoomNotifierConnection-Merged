package com.example.vinze.roomnotifierconnection.Activities;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.R;


import static com.example.vinze.roomnotifierconnection.Activities.SearchActivity.EXTRA_MEDIKAMENT_ANWENDUNG;
import static com.example.vinze.roomnotifierconnection.Activities.SearchActivity.EXTRA_MEDIKAMENT_NAME;
import static com.example.vinze.roomnotifierconnection.Activities.SearchActivity.EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG;
import static com.example.vinze.roomnotifierconnection.Activities.SearchActivity.EXTRA_MEDIKAMENT_WIRKSTOFF;

public class MedikamentActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView wirkstoffTextView;
    private TextView anwendungsTextView;
    private TextView verschreibungTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medikament);

        loadComponents();

        Intent intent = getIntent();

        String name = intent.getStringExtra(EXTRA_MEDIKAMENT_NAME);
        String wirkstoff = intent.getStringExtra(EXTRA_MEDIKAMENT_WIRKSTOFF);
        String anwendung = intent.getStringExtra(EXTRA_MEDIKAMENT_ANWENDUNG);
        String verschreibung = intent.getStringExtra(EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG);

        nameTextView.setText(intent.getStringExtra(EXTRA_MEDIKAMENT_NAME));

        if (wirkstoff.isEmpty() || wirkstoff == null) {
            wirkstoff = "Kein Wirkstoff";
        } else {
            wirkstoff = wirkstoff.toLowerCase();
            wirkstoff = Character.toUpperCase(wirkstoff.charAt(0)) +
                    (wirkstoff.length() > 1 ? wirkstoff.substring(1) : "");
        }

        wirkstoffTextView.setText(wirkstoff);

        anwendungsTextView.setText(intent.getStringExtra(EXTRA_MEDIKAMENT_ANWENDUNG));
        verschreibungTextView.setText(intent.getStringExtra(EXTRA_MEDIKAMENT_VERSCHREIBUNGSPFLICHTIG));

    }

    public void loadComponents() {
        nameTextView = findViewById(R.id.nameText);
        wirkstoffTextView = findViewById(R.id.wirkstoffText);
        anwendungsTextView = findViewById(R.id.anwendungText);
        verschreibungTextView = findViewById(R.id.verschreibungsText);
    }
}
