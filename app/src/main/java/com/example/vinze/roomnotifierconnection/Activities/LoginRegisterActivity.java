package com.example.vinze.roomnotifierconnection.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vinze.roomnotifierconnection.R;

public class LoginRegisterActivity extends AppCompatActivity {

    public Button bt_login;
    public Button bt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        loadComponents();
    }


    public void loadComponents(){
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
    }

    public void onClickLogin(View v){
        Intent swicthToLogin = new Intent(this, LoginActivity.class);
        startActivity(swicthToLogin);
    }

    public void onClickRegsiter(View v){
        Intent swicthToRegister = new Intent(this, RegisterActivity.class);
        startActivity(swicthToRegister);
    }
}
