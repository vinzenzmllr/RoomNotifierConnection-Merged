package com.example.vinze.roomnotifierconnection.Activities.Login_Register;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vinze.roomnotifierconnection.JSONParser;
import com.example.vinze.roomnotifierconnection.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class RegisterDataActivity extends AppCompatActivity {

    final Calendar myCalendar = Calendar.getInstance();
    public EditText et_birthday;
    public EditText et_groesse;
    public EditText et_gewicht;
    public EditText et_geschlecht;

    public TextView tv_birthdateDescription;
    public TextView tv_heightDescription;
    public TextView tv_weightDescription;
    public TextView tv_genderDescription;

    String username;

    JSONParser jsonParser = new JSONParser();

    private static final String url_update_product = "http://lucidus.htl5.org/phpshit/update_user.php";

    private static final String TAG_SUCCESS = "success";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerdata);
        loadComponents();
        et_birthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterDataActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Intent intent = getIntent();
        username = intent.getStringExtra("EXTRA_USERNAME");
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        et_birthday.setText(sdf.format(myCalendar.getTime()));
    }


    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    public void handleError(int errorFlag){
        if(errorFlag == 1){
            tv_birthdateDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_birthdateDescription.setText("Bitte geben Sie ein Geburtsdatum ein");
        }
        else if(errorFlag == 2){
            tv_heightDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_heightDescription.setText("Bitte geben Sie Ihre Größe ein");
        }
        else if(errorFlag == 3){
            tv_weightDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_weightDescription.setText("Bitte geben Sie Ihr Gewicht ein");
        }
        else if(errorFlag == 4){
            tv_genderDescription.setTextColor(Color.parseColor("#ef403d"));
            tv_genderDescription.setText("Bitte geben Sie Ihr Geschlecht an");
        }
    }



    public void onClickRisk(View v){
        if(et_birthday.getText().toString().matches("")){
            handleError(1);
        }
        else if(et_groesse.getText().toString().matches("")){
            handleError(2);
        }
        else if(et_gewicht.getText().toString().matches("")){
            handleError(3);
        }
        else if(et_geschlecht.getText().toString().matches("")){
            handleError(4);
        }
        else{
            /*UserBuilder.buildUser.setBirthdate(myCalendar.getTime());
            UserBuilder.buildUser.setGroesse(Double.parseDouble(et_groesse.getText().toString()));
            UserBuilder.buildUser.setGewicht(Double.parseDouble(et_gewicht.getText().toString()));
            UserBuilder.buildUser.setGeschlecht(et_geschlecht.getText().toString());
            UserBuilder.printUser();*/

            new SaveUserDetails().execute();

            Intent switchToRisk = new Intent(this, RegisterRiskActivity.class);
            startActivity(switchToRisk);
        }

    }

    public void loadComponents(){
        et_birthday  = findViewById(R.id.et_birthdayInput);
        et_birthday.setInputType(InputType.TYPE_NULL);
        et_groesse = findViewById(R.id.et_groesseInput);
        et_gewicht = findViewById(R.id.et_gewichtInput);
        et_geschlecht = findViewById(R.id.et_geschlechtInput);
        tv_birthdateDescription = findViewById(R.id.tv_birthdateDescripton);
        tv_heightDescription = findViewById(R.id.tv_heightDescription);
        tv_weightDescription = findViewById(R.id.tv_weightDescription);
        tv_genderDescription = findViewById(R.id.tv_genderDescription);
    }

    class SaveUserDetails extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... args) {

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("u_username", username));
            params.add(new BasicNameValuePair("u_geschlecht", et_geschlecht.getText().toString()));
            params.add(new BasicNameValuePair("u_gewicht", et_gewicht.getText().toString()));
            params.add(new BasicNameValuePair("u_groesse", et_groesse.getText().toString()));
            params.add(new BasicNameValuePair("u_geburtstag", et_birthday.getText().toString()));

            JSONObject json = jsonParser.makeHttpRequest(url_update_product,"POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    Intent i = getIntent();

                    setResult(100, i);
                    //finish();
                } else {
                    // failed to update product
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
