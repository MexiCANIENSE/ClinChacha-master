package com.edu.paytonramirezg.clinchacha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.edu.paytonramirezg.clinchacha.utils.ClinValidations;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    LoginButton loginButton;
    CallbackManager callbackManager;
    private Button btnLogin;
    private String usernameL;
    public static final String ACTIVEUSERNAME = "USER_NAME";
    private static final String LOGIN_URL = "http://clinapp.es/php/login.php";
    private ArrayList<String[]> action;

    boolean loginstatus;

    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginstatus = false;
        initValues();

        btnLogin=(Button)findViewById(R.id.bt_ConfirmarLG);
        btnLogin.setOnClickListener(this);

        //fb login
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        updateWithToken(AccessToken.getCurrentAccessToken());


        Typeface normal = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                // App code


                String user_FN = Profile.getCurrentProfile().getFirstName();
                String user_LN = Profile.getCurrentProfile().getLastName();

                String user_FB = user_FN + " " + user_LN;

                Intent fb_Intent = new Intent(LoginActivity.this, MainActivity.class);

                fb_Intent.putExtra("user", user_FB);
                startActivity(fb_Intent);



            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code


            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void initValues(){
        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
    }



    private void login(){

        if(ValidateData()){
            usernameL = username.getText().toString().trim();
            userLogin(username.getText().toString(), password.getText().toString());
        }
        else{
            Toast.makeText(getApplicationContext(),"Ingrese correctamente sus datos",Toast.LENGTH_LONG).show();
        }
        /*EditText usernameinput = (EditText) findViewById(R.id.et_username);
        EditText passwordinput = (EditText) findViewById(R.id.et_password);
        usernameL = usernameinput.getText().toString().trim();
        String passwordL = passwordinput.getText().toString().trim();*/



    }

    private void userLogin(final String username, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this,"Favor de esperar",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){

                    SharedPreferences tokenUser = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = tokenUser.edit();

                    editor.putString("Token", username+password);
                    editor.putString("User", username);
                    editor.commit();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("ACTIVEUSERNAME",usernameL);
                    startActivity(intent);
                }else{
                    Toast.makeText(LoginActivity.this,s,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("username", params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(LOGIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(username, password);
    }

    @Override
    public void onClick(View v) {
        if(v == btnLogin){
            login();
        }
    }


    private boolean ValidateData(){
        boolean status = false;

        if(username.getText().toString().isEmpty())
            username.setHintTextColor(getResources().getColor(R.color.red_color));
        else if(password.getText().toString().isEmpty())
            password.setHintTextColor(getResources().getColor(R.color.red_color));
        else
            status = true;

        return status;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onStop() {
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();

    }



    public void setup(Typeface normal, Typeface bold) {
        EditText ele1 = (EditText) findViewById(R.id.et_username);
        EditText ele2 = (EditText) findViewById(R.id.et_password);

        Button ele3 = (Button) findViewById(R.id.bt_ConfirmarLG);


        ele2.setTypeface(normal);
        ele1.setTypeface(normal);
        ele3.setTypeface(bold);

    }

    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    Intent loging_intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(loging_intent);

                    finish();
                }
            }, 1000);
        } else {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                }
            }, 1000);
        }
    }


    @Override
    public void onBackPressed() {
        Intent intent_register = new Intent(LoginActivity.this, Screen1.class);
        startActivity(intent_register);

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }



}

