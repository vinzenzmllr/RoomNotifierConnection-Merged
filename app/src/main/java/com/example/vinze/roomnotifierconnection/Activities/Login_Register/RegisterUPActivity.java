package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.vinze.roomnotifierconnection.R;

public class RegisterUPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_up);
    }

    public void OnClickUserData(View v){
        Intent switchToUserData = new Intent(this,RegisterDataActivity.class);
        startActivity(switchToUserData);
    }
}
