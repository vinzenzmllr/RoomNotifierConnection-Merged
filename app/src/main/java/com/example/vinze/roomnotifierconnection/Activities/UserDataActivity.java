package com.example.vinze.roomnotifierconnection.Activities;

import android.content.Intent;

import com.example.vinze.roomnotifierconnection.Entities.User;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

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

public class UserDataActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TextView username;
    private TextView geschlecht;
    private TextView groesse;
    private TextView gewicht;


    private Intent switchToReminder;
    private Intent switchToSearch;
    private Intent switchToInfo;
    private Intent switchToUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        loadComponents();
        BackgroundWorker bw = new BackgroundWorker();
        bw.execute();
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
            loadData(users,1);
        }



    }

    public void loadData(User[] users, int id){
        for(User user : users){
            if(Integer.parseInt(user.getId()) == id){
                User loggedInUser = user;
                username.setText(loggedInUser.getUsername());
                geschlecht.setText(loggedInUser.getGeschlecht());
                groesse.setText(loggedInUser.getGroesse());
                gewicht.setText(loggedInUser.getGewicht());
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_reminder) {
            if(switchToReminder == null){
                switchToReminder = new Intent(this, ReminderActivity.class);
                startActivity(switchToReminder);

            }
            else{
                startActivity(switchToReminder);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_search){
            if(switchToSearch == null){
                switchToSearch = new Intent(this, SearchActivity.class);
                startActivity(switchToSearch);

            }
            else{
                startActivity(switchToSearch);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_info){
            if(switchToInfo == null){
                switchToInfo = new Intent(this, InfoActivity.class);
                startActivity(switchToInfo);

            }
            else{
                startActivity(switchToInfo);
                System.out.println("not new");
            }

        }

        else if(id == R.id.nav_userdata){
            if(switchToUserData == null){
                switchToUserData = new Intent(this, UserDataActivity.class);
                startActivity(switchToUserData);

            }
            else{
                startActivity(switchToUserData);
                System.out.println("not new");
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void loadComponents(){
        username = findViewById(R.id.tv_actUsername);
        geschlecht = findViewById(R.id.tv_actGeschlecht);
        groesse = findViewById(R.id.tv_actGroesse);
        gewicht = findViewById(R.id.tv_actGewicht);
    }

}
