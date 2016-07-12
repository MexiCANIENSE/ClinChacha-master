package com.edu.paytonramirezg.clinchacha;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.HashMap;

public class ReserveActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener,
        PlaceSelectionListener,
        View.OnClickListener{

    private TextView timeTextView;
    private TextView dateTextView;
    private Button reserve;
    public String ACTIVEUSERNAME, COMMENTS = "";
    public String DATE = "", TIME = "", ADDRESS = "";
    private static final String ReserveURL = "http://clinapp.es/php/reserve.php";


    PlaceAutocompleteFragment autocompleteFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        SharedPreferences prefs = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);
        ACTIVEUSERNAME = prefs.getString("User", null);


        timeTextView = (TextView)findViewById(R.id.time_textview);
        dateTextView = (TextView)findViewById(R.id.date_textview);
        Button timeButton = (Button)findViewById(R.id.time_button);
        Button dateButton = (Button)findViewById(R.id.date_button);
        reserve = (Button) findViewById(R.id.confirm_reserve);
        reserve.setOnClickListener(this);

        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete);
        autocompleteFragment.setOnPlaceSelectedListener(this);

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog tpd = TimePickerDialog.newInstance(
                        ReserveActivity.this,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),true
                );
                tpd.setTitle("Hora");
                tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d("TimePicker", "Dialog was cancelled");
                    }
                });
                tpd.show(getFragmentManager(), "Timepickerdialog");
            }
        });

        // Show a datepicker when the dateButton is clicked
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        ReserveActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setTitle("Fecha");
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+(++monthOfYear)+"/"+year;
        DATE = date;
        String dateFormat = dayOfMonth+" "+monthName(--monthOfYear);
        dateTextView.setText(dateFormat);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
        String minuteString = minute < 10 ? "0"+minute : ""+minute;
        //String secondString = second < 10 ? "0"+second : ""+second;
        String time = hourString+":"+minuteString;
        TIME = time;
        timeTextView.setText(time);
    }

    @Override
    public void onResume() {
        super.onResume();

        DatePickerDialog dpd = (DatePickerDialog) getFragmentManager().findFragmentByTag("Datepickerdialog");
        TimePickerDialog tpd = (TimePickerDialog) getFragmentManager().findFragmentByTag("TimepickerDialog");

        if(tpd != null) tpd.setOnTimeSetListener(this);
        if(dpd != null) dpd.setOnDateSetListener(this);
    }

    public void onBackPressed() {
        finish();
    }

    private String monthName(int month){
        String name="";

        switch(month){
            case 0:
                name = "Enero";
                break;
            case 1:
                name = "Feb";
                break;
            case 2:
                name = "Marzo";
                break;
            case 3:
                name = "Abril";
                break;
            case 4:
                name = "Mayo";
                break;
            case 5:
                name = "Junio";
                break;
            case 6:
                name = "Julio";
                break;
            case 7:
                name = "Agosto";
                break;
            case 8:
                name = "Sept";
                break;
            case 9:
                name = "Oct";
                break;
            case 10:
                name = "Nov";
                break;
            case 11:
                name = "Dic";
                break;
        }

        return name;
    }

    @Override
    public void onPlaceSelected(Place place) {
        /*Toast.makeText(
                getApplicationContext(), "Success", Toast.LENGTH_LONG).show();*/
        String location = place.getAddress().toString();

        if (location == null || location.equals("")) {
            Toast.makeText(getBaseContext(), "No se ha ingresado una dirección.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://maps.googleapis.com/maps/api/geocode/json?";

        try {
            // encoding special characters like space in the user input place
            location = URLEncoder.encode(location, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        ADDRESS = location;
        String address = "address=" + location;

        String sensor = "sensor=false";

        // url , from where the geocoding data is fetched
        url = url + address + "&" + sensor;

        //Debug
        Toast.makeText(
                getApplicationContext(),
                address,
                Toast.LENGTH_LONG).show();


        /*
        DownloadTask downloadTask = new DownloadTask();
        downloadTask.execute(url);
        */
    }

    private void userReserve(final String username, final String date, final String time, final String comments, final String address){
        class UserReserveClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ReserveActivity.this,"Favor de esperar",null,true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if(s.equalsIgnoreCase("success")){

                    Toast.makeText(getApplicationContext(), "Reservado", Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(), "Error de conectividad", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("username", params[0]);
                data.put("date",params[1]);
                data.put("time",params[2]);
                data.put("comments",params[3]);
                data.put("address",params[4]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(ReserveURL,data);

                return result;
            }
        }
        UserReserveClass ulc = new UserReserveClass();
        ulc.execute(username, date, time, comments, address);
    }

    @Override
    public void onError(Status status) {
        Toast.makeText(
                getApplicationContext(),
                "ERROR",
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onClick(View view) {
        if(view == reserve){
            userReserve(ACTIVEUSERNAME,DATE,TIME,COMMENTS,ADDRESS);
        }
    }
}
