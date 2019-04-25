package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.annotation.SuppressLint;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.Entities.User;
import com.example.vinze.roomnotifierconnection.JSONParser;
import com.example.vinze.roomnotifierconnection.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.vinze.roomnotifierconnection.R.color.colorAccent;

public class RegisterUPActivity extends AppCompatActivity {

    public EditText et_username;
    public EditText et_password;
    public TextView tv_usernameDescription;
    public TextView tv_passwordDescription;

    JSONParser jsonParser = new JSONParser();

    private static final String url_add_user = "http://lucidus.htl5.org/phpshit/create_user.php";

    private static final String TAG_SUCCESS = "success";

    String username;

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
            //UserBuilder.buildUser.setUsername(et_username.getText().toString());
            //UserBuilder.buildUser.setPassword(et_password.getText().toString());

            new AddNewUser().execute();

            username = et_username.getText().toString();

            System.out.println("************************************************************");
            if (username == null)
                System.out.println("Leer | " + et_username.getText().toString());
            else
                System.out.println(username);
            System.out.println("************************************************************");

            Intent switchToUserData = new Intent(this,RegisterDataActivity.class);
            switchToUserData.putExtra("EXTRA_USERNAME", username);
            startActivity(switchToUserData);
        }

    }

    class AddNewUser extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {
            User user = new User();
            user.setPasswort(et_password.getText().toString());
            user.setUsername(et_username.getText().toString());

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("u_username", user.getUsername()));
            params.add(new BasicNameValuePair("u_passwort", user.getPasswort()));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_add_user,"POST", params);

            System.out.println(params.toString());
            System.out.println("Create Response" + json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    System.out.println("user erstellt");
                } else {
                    System.out.println("fehler beim erstellen des users");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(String file_url) {

        }

    }
}
