package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.Activities.SearchActivity;
import com.example.vinze.roomnotifierconnection.R;

public class LoginActivity extends AppCompatActivity {

    TextView tv_usernameInput;
    TextView tv_passwordInput;
    Button bt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        loadComponents();
    }

    public void loadComponents(){
        tv_usernameInput = findViewById(R.id.tv_usernameInput);
        tv_passwordInput = findViewById(R.id.tv_passwordInput);
        bt_login = findViewById(R.id.bt_login);

    }

    public void onClick(View v){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);
    }

    public void onClickGuest(View v){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);

    }
}
