package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.vinze.roomnotifierconnection.Activities.SearchActivity;
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

    public void onClick(View v){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);
    }

    public void onClickRegsiter(View v){
        Intent swicthToRegister = new Intent(this, RegisterUPActivity.class);
        startActivity(swicthToRegister);
    }

    public void onClickGuest(View v){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);

    }
}
