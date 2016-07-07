package com.edu.paytonramirezg.clinchacha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.stripe.android.Stripe;
import com.stripe.android.TokenCallback;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import static com.edu.paytonramirezg.clinchacha.R.id;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    public String ACTIVEUSERNAME;
    private ProgressDialog loading;

    Button edit;
    private int editstatus = 0;


    EditText eleET1;
    EditText eleET2;
    //EditText eleET3;
    EditText eleET4;
    EditText eleET5;
    EditText eleET6;

    public static final String EDIT_URL = "http://www.clinapp.es/php/profileedit.php";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_NAME = "name";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_CARDTYPE = "address";
    public static final String KEY_NAMEONCARD = "phone";

    Button submitButton;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*if(LoginManager.getInstance()!=null) {
            Profile profile = Profile.getCurrentProfile();
            cur_user1 = profile.getName();
}*/

        eleET1 = (EditText) findViewById(id.info_field_name);
        eleET2 = (EditText) findViewById(id.info_field_address);
        //eleET3 = (EditText)findViewById(id.info_field_cardnum);
        eleET4 = (EditText) findViewById(id.info_field_email);
        eleET5 = (EditText) findViewById(id.info_field_gender);
        eleET6 = (EditText) findViewById(id.info_field_phone);

        edit = (Button) findViewById(id.btn_editar);
        edit.setOnClickListener(this);


        //get current username and input into get data function
        Intent intentget = getIntent();
        Bundle b = intentget.getExtras();
        if (b != null) {
            ACTIVEUSERNAME = (String) b.get("ACTIVEUSERNAME");
        }

        getData();
        //end retrieve data base info


        Typeface normal = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }//end oncreate method

    private void getData() {
        loading = ProgressDialog.show(this, "Favor de esperar...", "Cargando Datos...", false, false);

        String url = Config.DATA_URL + ACTIVEUSERNAME;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfileActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String response) {
        String name = "";
        String address = "";
        String gender = "";
        String email = "";
        String phone = "";
        //String typeofpayment = "";
        String CARDNUM = "";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(Config.JSON_ARRAY);
            JSONObject userdata = result.getJSONObject(0);

            name = userdata.getString(Config.KEY_NAME);
            address = userdata.getString(Config.KEY_ADDRESS);
            gender = userdata.getString(Config.KEY_GENDER);
            email = userdata.getString(Config.KEY_EMAIL);
            phone = userdata.getString(Config.KEY_PHONE);
            //typeofpayment = collegeData.getString(Config.KEY_TOP);
            CARDNUM = userdata.getString(Config.KEY_CARDNUM);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        eleET1.setText(name);
        eleET2.setText(address);
        //eleET3.setText(CARDNUM);
        eleET4.setText(email);
        if (gender.equals("H")) {
            eleET5.setText("Hombre");
        } else if (gender.equals("M")) {
            eleET5.setText("Mujer");
        } else {
            eleET5.setText(gender);
        }

        eleET6.setText(phone);


    }//end server call method

    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("ACTIVEUSERNAME", ACTIVEUSERNAME);
        intent.putExtra("stripeToken", KEY_TOKEN);
        startActivity(intent);
    }


    public void setup(Typeface normal, Typeface bold) {

        TextView ele1 = (TextView) findViewById(id.info_name);

        TextView ele2 = (TextView) findViewById(id.info_address);
        //TextView ele3 = (TextView)findViewById(id.info_cardnum);
        TextView ele4 = (TextView) findViewById(id.info_email);
        TextView ele5 = (TextView) findViewById(id.info_gender);
        TextView ele6 = (TextView) findViewById(id.info_phone);
        TextView ele7 = (TextView) findViewById(id.subhead_InPago);
        TextView ele8 = (TextView) findViewById(id.subhead_IP);
        TextView ele9 = (TextView) findViewById(id.header_P);
        TextView btn1 = (TextView) findViewById(id.btn_editar);

        EditText eleET1 = (EditText) findViewById(id.info_field_name);
        //eleET1.setText(cur_user1);
        EditText eleET2 = (EditText) findViewById(id.info_field_address);
        //EditText eleET3 = (EditText)findViewById(id.info_field_cardnum);
        EditText eleET4 = (EditText) findViewById(id.info_field_email);
        //eleET4.setText(user_email);
        EditText eleET5 = (EditText) findViewById(id.info_field_gender);
        EditText eleET6 = (EditText) findViewById(id.info_field_phone);

        eleET1.setEnabled(false);
        eleET2.setEnabled(false);
        //eleET3.setEnabled(false);
        eleET4.setEnabled(false);
        eleET5.setEnabled(false);
        eleET6.setEnabled(false);

        eleET1.setTypeface(normal);
        eleET2.setTypeface(normal);
        //eleET3.setTypeface(normal);
        eleET4.setTypeface(normal);
        eleET5.setTypeface(normal);
        eleET6.setTypeface(normal);

        // eleET5.setWidth(eleET5.getText().length());


        ele1.setTypeface(normal);
        ele2.setTypeface(normal);
        //ele3.setTypeface(normal);
        ele4.setTypeface(normal);
        ele5.setTypeface(normal);
        ele6.setTypeface(normal);
        ele7.setTypeface(bold);
        ele8.setTypeface(bold);
        ele9.setTypeface(bold);
        btn1.setTypeface(bold);

    }


    @Override
    public void onClick(View v) {
        if (v == edit) {
            if (editstatus == 0) {
                edit.setText("GuardarCambios");
                editstatus = 1;
                submitButton=(Button)findViewById(id.submitButton);
                submitButton.setVisibility(View.VISIBLE);
                eleET1.setEnabled(true);
                eleET2.setEnabled(true);
                //eleET3.setEnabled(true);
                eleET4.setEnabled(true);
                eleET5.setEnabled(true);
                eleET6.setEnabled(true);


                return;
            } else if (editstatus == 1) {
                edit.setText("Editar");
                editstatus = 0;
                submitButton=(Button)findViewById(id.submitButton);
                submitButton.setVisibility(View.VISIBLE);
                SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
                String prefString = userProfile.getString("User", "");

                //final String username = ACTIVEUSERNAME.trim();
                final String username = prefString;
                final String eleEt1 = eleET1.getText().toString().trim();
                final String eleEt2 = eleET2.getText().toString().trim();
                //final  String eleEt3 = eleET3.getText().toString().trim();
                final String eleEt4 = eleET4.getText().toString().trim();
                final String eleEt5 = eleET5.getText().toString().trim();
                final String eleEt6 = eleET6.getText().toString().trim();

                disableSSLCertificateChecking();





                StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KEY_USERNAME, username);
                        params.put(KEY_NAME, eleEt1);
                        params.put(KEY_GENDER, eleEt5);
                        params.put(KEY_EMAIL, eleEt4);
                        params.put(KEY_PHONE, eleEt6);
                        params.put(KEY_ADDRESS, eleEt2);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

                eleET1.setEnabled(false);
                eleET2.setEnabled(false);
                //eleET3.setEnabled(false);
                eleET4.setEnabled(false);
                eleET5.setEnabled(false);
                eleET6.setEnabled(false);

                return;
            }

        }
    }

    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    //Tripe token

    public void submitCard(View view) {
        // TODO: replace with your own test key
        final String publishableApiKey = BuildConfig.DEBUG ?
                "pk_test_6pRNASCoBOKtIshFeQd4XMUh" :
                getString(R.string.com_stripe_publishable_key);

        TextView cardNumberField = (TextView) findViewById(id.cardNumber);
        TextView monthField = (TextView) findViewById(id.month);
        TextView yearField = (TextView) findViewById(id.year);
        TextView cvcField = (TextView) findViewById(id.cvc);

        Card card = new Card(cardNumberField.getText().toString(),
                Integer.valueOf(monthField.getText().toString()),
                Integer.valueOf(yearField.getText().toString()),
                cvcField.getText().toString());

        Stripe stripe = new Stripe();
        stripe.createToken(card, publishableApiKey, new TokenCallback() {
            public void onSuccess(Token token) {
                // TODO: Send Token information to your backend to initiate a charge

                final String TOKEN = token.getId();

                disableSSLCertificateChecking();

                StringRequest stringRequest = new StringRequest(Request.Method.POST, EDIT_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(ProfileActivity.this, response, Toast.LENGTH_LONG).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(ProfileActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put(KEY_USERNAME, ACTIVEUSERNAME.trim());
                        params.put(KEY_TOKEN, TOKEN);

                        return params;
                    }

                };

                RequestQueue requestQueue = Volley.newRequestQueue(ProfileActivity.this);
                requestQueue.add(stringRequest);

                Toast.makeText(
                        getApplicationContext(),
                        "Token created: " + token.getId(),
                        Toast.LENGTH_LONG).show();
            }

            public void onError(Exception error) {
                Log.d("Stripe", error.getLocalizedMessage());
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Profile Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
