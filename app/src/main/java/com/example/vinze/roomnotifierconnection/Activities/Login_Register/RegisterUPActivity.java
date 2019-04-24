package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.R;
import com.example.vinze.roomnotifierconnection.UserProcessor.UserBuilder;

import static com.example.vinze.roomnotifierconnection.R.color.colorAccent;

public class RegisterUPActivity extends AppCompatActivity {

    public EditText et_username;
    public EditText et_password;
    public TextView tv_usernameDescription;
    public TextView tv_passwordDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_up);

        et_username = findViewById(R.id.et_usernameInput);
        et_password = findViewById(R.id.et_passwordInput);
        tv_usernameDescription = findViewById(R.id.tv_usernameDescription);
        tv_passwordDescription = findViewById(R.id.tv_passwordDescription);

    }


    private void handleError(int errorFlag){
        if(errorFlag == 1){
            tv_usernameDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_usernameDescription.setText("Bitte geben Sie einen Usernamen ein");
        }
        else if(errorFlag == 2){
            tv_passwordDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_passwordDescription.setText("Bitte geben Sie ein Passwort ein");
        }

        else if(errorFlag == 3){
            tv_usernameDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_usernameDescription.setText("Bitte geben Sie einen Usernamen ein");
            tv_passwordDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_passwordDescription.setText("Bitte geben Sie ein Passwort ein");
        }
    }

    public void onClickUserData(View v){
        if(et_username.getText().toString().matches("")){
            handleError(1);
        }
        else if (et_password.getText().toString().matches("")){
            handleError(2);
        }
        else if (et_password.getText().toString().matches("") && et_username.getText().toString().matches("")){
            handleError(3);
        }
        else
        {
            UserBuilder.buildUser.setUsername(et_username.getText().toString());
            UserBuilder.buildUser.setPassword(et_password.getText().toString());
            Intent switchToUserData = new Intent(this,RegisterDataActivity.class);
            startActivity(switchToUserData);
        }

    }
}
