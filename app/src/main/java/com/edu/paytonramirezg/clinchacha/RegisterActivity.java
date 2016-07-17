package com.edu.paytonramirezg.clinchacha;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edu.paytonramirezg.clinchacha.utils.ClinValidations;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity
        implements AdapterView.OnItemSelectedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    private Spinner spinner;
    private CheckBox termsNcond;

    private EditText username;
    private EditText password;
    private EditText confPassword;
    private EditText email;
    private EditText dob;
    private EditText address;
    private EditText name;

    private String enteredName;
    private String enteredUsername;
    private String enteredConfPassword;
    private String enteredPassword;
    private String enteredEmail;
    private String enteredGender;
    private String enteredDOB;
    private String enteredAddress;

    private String latitude;
    private String longitude;

    private Button btConf;

    private static final String REGISTER_URL = "http://clinapp.es/php/register2.php";

    Typeface normal, bold;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();


        normal = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        bold = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");

        // Spinner element
        spinner = (Spinner) findViewById(R.id.spinner_genderR);
        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("------");
        categories.add("Hombre");
        categories.add("Mujer");
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //find variable elements
        initValues();


        //button listener
        btConf = (Button) findViewById(R.id.bt_confirmarR);
        btConf.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == btConf) {
            registerUser();
        }
    }

    private void initValues() {
        name = (EditText) findViewById(R.id.et_fullname);
        username = (EditText) findViewById(R.id.et_usernameR);
        password = (EditText) findViewById(R.id.et_passwordR);
        confPassword = (EditText) findViewById(R.id.et_passwrodCR);
        email = (EditText) findViewById(R.id.et_emailR);
        address = (EditText) findViewById(R.id.et_addressR);
        dob = (EditText) findViewById(R.id.et_birthdayR);
        termsNcond = (CheckBox) findViewById(R.id.checkBox);
    }

    private void registerUser() {

        if (ValidateData()) {
            enteredName = name.getText().toString();
            enteredUsername = username.getText().toString();
            enteredPassword = password.getText().toString();
            enteredEmail = email.getText().toString();
            enteredConfPassword = confPassword.getText().toString();
            enteredGender = spinner.getSelectedItem().toString();
            enteredDOB = dob.getText().toString();
            enteredAddress = address.getText().toString();

            register(enteredName, enteredUsername, enteredPassword, enteredEmail, enteredAddress, enteredDOB, enteredGender, latitude, longitude);
        } else {
            Toast.makeText(getApplicationContext(), "Ingrese correctamente sus datos", Toast.LENGTH_LONG).show();
        }


    }

    /*VALIDATE INFO*/

    private boolean ValidateData() {
        boolean status = false;

        if (name.getText().toString().isEmpty())
            name.setHintTextColor(getResources().getColor(R.color.red_color));
        else if (username.getText().toString().isEmpty())
            username.setHintTextColor(getResources().getColor(R.color.red_color));
        else if (password.getText().toString().isEmpty())
            password.setHintTextColor(getResources().getColor(R.color.red_color));
        else if (!ClinValidations.isSamePassword(password.getText().toString(), confPassword.getText().toString())) {
            Toast.makeText(getApplicationContext(), "Su contraseña no coincide", Toast.LENGTH_LONG).show();
            confPassword.setHintTextColor(getResources().getColor(R.color.red_color));
        } else if (!ClinValidations.isValidEmail(email.getText().toString()))
            email.setHintTextColor(getResources().getColor(R.color.red_color));
        else
            status = true;

        return status;
    }

    private void register(String name, String username, String password, String email, String address, String dob, String gender, String latitude, String longitude) {
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Procesando", null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();


                if (termsNcond.isChecked() == true) {
                    if (s.equalsIgnoreCase("success")) {
                        Intent intent = new Intent(RegisterActivity.this, Screen1.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Favor de aceptar las Condiciones de Términos de Uso", Toast.LENGTH_LONG).show();
                }


            }

            @Override
            protected String doInBackground(String... params) {


                HashMap<String, String> data = new HashMap<>();
                data.put("name", params[0]);
                data.put("username", params[1]);
                data.put("password", params[2]);
                data.put("email", params[3]);
                data.put("address", params[4]);
                data.put("dob", params[5]);
                data.put("gender", params[6]);
                data.put("latitude", params[7]);
                data.put("longitude", params[8]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;


            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, username, password, email, address, dob, gender, latitude, longitude);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        //Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onBackPressed() {
        Intent intent_register = new Intent(RegisterActivity.this, Screen1.class);
        startActivity(intent_register);

    }

    public void setup(Typeface normal, Typeface bold) {
        /*username = (EditText) findViewById(R.id.et_usernameR);
        password  = (EditText) findViewById(R.id.et_passwordR);
        confPassword  = (EditText) findViewById(R.id.et_passwrodCR);
        email  = (EditText) findViewById(R.id.et_emailR);*/
        //dob  = (EditText) findViewById(R.id.et_birthdayR);
        //address  = (EditText) findViewById(R.id.et_addressR);

        TextView instruct = (TextView) findViewById(R.id.tv_reg_instruct);

        instruct.setTypeface(normal);
        username.setTypeface(normal);
        password.setTypeface(normal);
        confPassword.setTypeface(normal);
        email.setTypeface(normal);
        //dob.setTypeface(normal);
        address.setTypeface(normal);
        btConf.setTypeface(bold);

    }



    /*
    * LOCATION SERVICE
    * */

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location){
        //Log.i(LOG_TAG, location.toString());
        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
    }

    @Override
    public void onConnectionSuspended(int i){
        //Log.i(LOG_TAG, "Google Api Client connection has been suspended");

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //Log.e(LOG_TAG, "Connection has failed : "+connectionResult.getErrorCode());
    }


    @Override
    protected void onStart(){
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }


    }

}
