package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vinze.roomnotifierconnection.Activities.SearchActivity;
import com.example.vinze.roomnotifierconnection.R;
import com.google.android.material.chip.Chip;

public class RegisterRiskActivity extends AppCompatActivity {

    private Button createUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_risk);
        loadcomponents();
    }

    public void loadcomponents(){
        createUser = findViewById(R.id.bt_createUser);
    }

    public void onClickCreateUser(View V){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);
    }
}
