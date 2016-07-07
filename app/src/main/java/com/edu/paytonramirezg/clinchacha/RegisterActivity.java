package com.edu.paytonramirezg.clinchacha;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

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


    private Button btConf;

    private static final String REGISTER_URL = "http://clinapp.es/php/register2.php";

    Typeface normal, bold;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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

    private void initValues(){
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

        if (ValidateData()){
            enteredName = name.getText().toString();
            enteredUsername = username.getText().toString();
            enteredPassword = password.getText().toString();
            enteredEmail = email.getText().toString();
            enteredConfPassword = confPassword.getText().toString();
            enteredGender = spinner.getSelectedItem().toString();
            enteredDOB = dob.getText().toString();
            enteredAddress = address.getText().toString();

            register(enteredName, enteredUsername, enteredPassword, enteredEmail, enteredAddress, enteredDOB, enteredGender);
        }else{
            Toast.makeText(getApplicationContext(),"Ingrese correctamente sus datos",Toast.LENGTH_LONG).show();
        }



    }

    /*VALIDATE INFO*/

    private boolean ValidateData(){
        boolean status = false;

        if(name.getText().toString().isEmpty())
            name.setHintTextColor(getResources().getColor(R.color.red_color));
        else if(username.getText().toString().isEmpty())
            username.setHintTextColor(getResources().getColor(R.color.red_color));
        else if(password.getText().toString().isEmpty())
            password.setHintTextColor(getResources().getColor(R.color.red_color));
        else if(!ClinValidations.isSamePassword(password.getText().toString(), confPassword.getText().toString())){
            Toast.makeText(getApplicationContext(),"Su contraseña no coincide",Toast.LENGTH_LONG).show();
            confPassword.setHintTextColor(getResources().getColor(R.color.red_color));
        }
        else if( !ClinValidations.isValidEmail(email.getText().toString()))
            email.setHintTextColor(getResources().getColor(R.color.red_color));
        else
            status = true;

        return status;
    }

    private void register(String name, String username, String password, String email, String address, String dob, String gender ) {
        class RegisterUser extends AsyncTask<String, Void, String> {

            ProgressDialog loading;
            RegisterUserClass ruc = new RegisterUserClass();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(RegisterActivity.this, "Procesando",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


                if (termsNcond.isChecked()==true){
                    if(s.equalsIgnoreCase("success")) {
                        Intent intent = new Intent(RegisterActivity.this, Screen1.class);
                        startActivity(intent);
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Favor de aceptar las Condiciones de Términos de Uso",Toast.LENGTH_LONG).show();
                }



            }

            @Override
            protected String doInBackground(String... params) {


                HashMap<String, String> data = new HashMap<String, String>();
                data.put("name", params[0]);
                data.put("username", params[1]);
                data.put("password", params[2]);
                data.put("email", params[3]);
                data.put("address", params[4]);
                data.put("dob", params[5]);
                data.put("gender", params[6]);

                String result = ruc.sendPostRequest(REGISTER_URL, data);

                return result;


            }
        }

        RegisterUser ru = new RegisterUser();
        ru.execute(name, username, password, email, address, dob, gender);
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
    public void onBackPressed(){
        Intent intent_register = new Intent(RegisterActivity.this, Screen1.class);
        startActivity(intent_register);

    }

    public void setup(Typeface normal, Typeface bold){
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




}
