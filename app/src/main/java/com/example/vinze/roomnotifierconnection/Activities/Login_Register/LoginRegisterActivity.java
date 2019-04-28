package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vinze.roomnotifierconnection.Activities.ReminderActivity;
import com.example.vinze.roomnotifierconnection.Activities.SearchActivity;
import com.example.vinze.roomnotifierconnection.Activities.UserDataActivity;
import com.example.vinze.roomnotifierconnection.Entities.User;
import com.example.vinze.roomnotifierconnection.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginRegisterActivity extends AppCompatActivity {

    public Button bt_login;
    public Button bt_register;
    public EditText username;
    public EditText password;

    public SharedPreferences loginID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        loadComponents();

        //SharedPreferences loginFlag = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

   

    public void loadComponents(){
        bt_login = findViewById(R.id.bt_login);
        bt_register = findViewById(R.id.bt_register);
        username = findViewById(R.id.et_usernameInput);
        password = findViewById(R.id.et_passwordInput);
    }

    public void onClickLogin(View v){
        BackgroundWorker bw = new BackgroundWorker();
        bw.execute();
    }

    public void loginAction(Boolean confirmed, User user){
        if(confirmed){
            //loginFlag auf true setzten
            //loginID auf user.getID setzen
            Intent switchToSearch = new Intent(this, SearchActivity.class);
            startActivity(switchToSearch);
        }
        else{
            Toast.makeText(LoginRegisterActivity.this, "Username und Passwort stimmen nicht überein", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickRegsiter(View v){
        Intent swicthToRegister = new Intent(this, RegisterUPActivity.class);
        startActivity(swicthToRegister);
    }

    public void onClickGuest(View v){
        Intent switchToSearch = new Intent(this, SearchActivity.class);
        startActivity(switchToSearch);

    }

    public static User[] JSONToUser(String jsonString) throws JSONException {
        JSONArray jsonArray = new JSONArray(jsonString);
        User[] users = new User[jsonArray.length()];

        for(int i=0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);

            User user = new User();
            user.setId(jsonObject.getString("u_id"));
            user.setUsername(jsonObject.getString("u_username"));
            user.setVorname(jsonObject.getString("u_vorname"));
            user.setNachname(jsonObject.getString("u_nachname"));
            user.setPasswort(jsonObject.getString("u_passwort"));
            user.setGeschlecht(jsonObject.getString("u_geschlecht"));
            user.setGewicht(jsonObject.getString("u_gewicht"));
            user.setGroesse(jsonObject.getString("u_groesse"));

            users[i] = user;
        }

        return users;
    }

    private class BackgroundWorker extends AsyncTask<Void,User,Void> {


        @Override
        protected Void doInBackground(Void... params) {
            String get_all_users_url = "http://lucidus.htl5.org/phpshit/get_all_users.php";

            try {
                URL url = new URL(get_all_users_url);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                br.close();
                connection.disconnect();

                System.out.println(sb.toString());

                String outputString = sb.toString();

                User[] users = UserDataActivity.JSONToUser(outputString);


                publishProgress(users);
                // return sb.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(Void result) {

        }

        @Override
        protected void onProgressUpdate(User... users) {
            for(User user : users){
                if(user.getUsername().equals(username) && user.getPasswort().equals(password)){
                    loginAction(true, user);
                }
                else{
                    loginAction(false, null);
                }
            }
        }



    }


}
