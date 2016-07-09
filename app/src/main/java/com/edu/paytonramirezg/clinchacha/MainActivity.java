package com.edu.paytonramirezg.clinchacha;import android.Manifest;import android.content.Context;import android.content.Intent;import android.content.SharedPreferences;import android.content.pm.PackageManager;import android.graphics.Typeface;import android.location.Address;import android.location.Geocoder;import android.location.Location;import android.location.LocationListener;import android.location.LocationManager;import android.net.Uri;import android.os.AsyncTask;import android.os.Bundle;import android.support.design.widget.FloatingActionButton;import android.support.design.widget.NavigationView;import android.support.design.widget.Snackbar;import android.support.design.widget.TabLayout;import android.support.v4.app.ActivityCompat;import android.support.v4.app.FragmentManager;import android.support.v4.view.GravityCompat;import android.support.v4.view.PagerAdapter;import android.support.v4.view.ViewPager;import android.support.v4.widget.DrawerLayout;import android.support.v7.app.ActionBarDrawerToggle;import android.support.v7.app.AppCompatActivity;import android.support.v7.widget.Toolbar;import android.util.Log;import android.view.LayoutInflater;import android.view.Menu;import android.view.MenuItem;import android.view.View;import android.widget.Button;import android.widget.CheckBox;import android.widget.CompoundButton;import android.widget.EditText;import android.widget.Filter;import android.widget.Filterable;import android.widget.ImageButton;import android.widget.RadioButton;import android.widget.RadioGroup;import android.widget.RelativeLayout;import android.widget.SeekBar;import android.widget.TextView;import android.widget.Toast;import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.VolleyError;import com.android.volley.toolbox.StringRequest;import com.android.volley.toolbox.Volley;import com.facebook.CallbackManager;import com.facebook.FacebookSdk;import com.facebook.login.LoginManager;import com.google.android.gms.appindexing.Action;import com.google.android.gms.appindexing.AppIndex;import com.google.android.gms.common.api.GoogleApiClient;import com.google.android.gms.common.api.Status;import com.google.android.gms.location.places.Place;import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;import com.google.android.gms.location.places.ui.PlaceSelectionListener;import com.google.android.gms.maps.CameraUpdate;import com.google.android.gms.maps.CameraUpdateFactory;import com.google.android.gms.maps.GoogleMap;import com.google.android.gms.maps.OnMapReadyCallback;import com.google.android.gms.maps.SupportMapFragment;import com.google.android.gms.maps.model.BitmapDescriptorFactory;import com.google.android.gms.maps.model.LatLng;import com.google.android.gms.maps.model.Marker;import com.google.android.gms.maps.model.MarkerOptions;import com.sothree.slidinguppanel.SlidingUpPanelLayout;import org.json.JSONObject;import java.io.BufferedReader;import java.io.IOException;import java.io.InputStream;import java.io.InputStreamReader;import java.io.PrintStream;import java.io.UnsupportedEncodingException;import java.net.HttpURLConnection;import java.net.URL;import java.net.URLEncoder;import java.security.KeyManagementException;import java.security.NoSuchAlgorithmException;import java.util.ArrayList;import java.util.HashMap;import java.util.List;import java.util.Locale;import java.util.Map;import javax.net.ssl.HttpsURLConnection;import javax.net.ssl.SSLContext;import javax.net.ssl.TrustManager;import javax.net.ssl.X509TrustManager;import io.fabric.sdk.android.Fabric;import android.support.v4.app.Fragment;import android.support.v4.app.FragmentPagerAdapter;import android.widget.ToggleButton;public class MainActivity extends AppCompatActivity        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, LocationListener, Filterable, GoogleMap.OnMarkerDragListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, PlaceSelectionListener, View.OnClickListener {    //ORDER FUNCTIONDECLARATIONS    public static final String ORDER_URL = "http://www.clinapp.es/php/order.php";    public static final String KEY_USERNAME = "username";    public static final String KEY_SERVICELVL = "servicelvl";    public static final String KEY_SERVICES = "services";    public static final String KEY_COSTO = "total";    public static final String KEY_TYPEOFHOME = "home";    public String KEY_TOKEN;    private EditText editTextUsername;    private Button orderbtn;    //END DECLARATIONS        SupportMapFragment sMapFragment;    private GoogleMap map;    private LocationManager locationManager;    private static final long MIN_TIME = 400;    private static final float MIN_DISTANCE = 1000;    SeekBar seekBar;    private ArrayList<LatLng> locations;    private Button conf, conf2;    RadioButton dom1, dom2;    CheckBox rb1, rb2, rb3, rb4, rb5;    TextView header1, header2, TV30,TV31,TV32;    CallbackManager callbackManager;    String cur_user;    RelativeLayout receipt;    RelativeLayout mapCon, auto_comp_frag;    String COSTO, SERVICELVL, SERVICES ="...", TYPEOFHOME;    public String ACTIVEUSERNAME;    TextView servicelvl_receipt;    TextView services_receipt;    TextView typehome_receipt;    TextView cost_receipt;    TextView typehomeheader_receipt;    RadioGroup radiogrouphome;    ToggleButton toggLimpieza, toggTintoreria;    TintoreriaFragment tintoreriaF;    LimpiezaFragment limpiezaF;    private static final String LOGOUT_URL = "http://www.clinapp.es/php/Logout.php";    //private static final String ORDER_URL = "https://clinapp.es/php/order.php";    ImageButton locbtn;    SlidingUpPanelLayout slidingUpPanelLayout;    PlaceAutocompleteFragment autocompleteFragment;    /**     * ATTENTION: This was auto-generated to implement the App Indexing API.     * See https://g.co/AppIndexing/AndroidStudio for more information.     */    private GoogleApiClient client;    @Override    protected void onCreate(Bundle savedInstanceState) {        FacebookSdk.sdkInitialize(getApplicationContext());        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);        setSupportActionBar(toolbar);        Fabric.with(this);        tintoreriaF = new TintoreriaFragment();        limpiezaF = new LimpiezaFragment();        SharedPreferences prefs = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);        ACTIVEUSERNAME = prefs.getString("User", null);        KEY_TOKEN = prefs.getString("StripeToken", null);        toggLimpieza = (ToggleButton) findViewById(R.id.toggle_limpieza);        toggTintoreria = (ToggleButton) findViewById(R.id.toggle_tintoreria);        toggLimpieza.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {                if(b) {                    ShowStatus(b,"Limpieza Activado");                    limpiezaF.ChangeStatusLimpieza(b);                }else{                    ShowStatus(b,"Limpieza Desactivado");                    limpiezaF.ChangeStatusLimpieza(b);                }            }        });        toggTintoreria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {            @Override            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {                if(b){                    ShowStatus(b,"Tintoreria Activado");                    tintoreriaF.ChangeStatusTintoreria(b);                }else{                    ShowStatus(b,"Tintoreria Desactivado");                    tintoreriaF.ChangeStatusTintoreria(b);                }            }        });        //order button located in the receipt to confirm order        orderbtn = (Button) findViewById(R.id.conf_btn2);        orderbtn.setOnClickListener(this);        Typeface bold = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");        conf = (Button) findViewById(R.id.bt_confirm_purch);//receipt confirm        TV30 = (TextView) findViewById(R.id.textView30);        TV31 = (TextView) findViewById(R.id.textView31);        TV32 = (TextView) findViewById(R.id.textView32);        TV30.setTypeface(bold);        TV31.setTypeface(bold);        TV32.setTypeface(bold);        //autocomplete frag        autocompleteFragment = (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);        autocompleteFragment.setOnPlaceSelectedListener(this);        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);        mapCon = (RelativeLayout) findViewById(R.id.map_container);        receipt = (RelativeLayout) findViewById(R.id.receipt_rl);        locbtn = (ImageButton) findViewById(R.id.locate_btn);        //final FrameLayout frameLayout_blurr = (FrameLayout) findViewById(R.id.frameLayout_blurr);        servicelvl_receipt = (TextView)findViewById(R.id.textViewlvlServInfo);        services_receipt = (TextView)findViewById(R.id.textViewlvlServInfo);        typehome_receipt = (TextView)findViewById(R.id.textViewHomeInfo);        typehomeheader_receipt = (TextView)findViewById(R.id.textViewHome);        cost_receipt = (TextView)findViewById(R.id.textViewcosttotal);        //radiogrouphome =(RadioGroup)findViewById(R.id.radiogroub_home);        auto_comp_frag = (RelativeLayout)findViewById(R.id.auto_comp_frag);        conf.setTypeface(bold);        /*header1.setTypeface(bold);        header2.setTypeface(bold);        dom1.setTypeface(bold);        dom2.setTypeface(bold);*/        servicelvl_receipt.setTypeface(bold);        services_receipt.setTypeface(bold);        typehome_receipt.setTypeface(bold);        typehomeheader_receipt.setTypeface(bold);        conf.setOnClickListener(this);        //TextView headerSev = (TextView) findViewById(R.id.header_services);        /*headerSev.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Toast.makeText(                        getApplicationContext(),                        "Limpiar ventanas (Al seleccionar ventanas aparece “Recuerda que los " +                                "servicios extras pueden demorar más tiempo y rebasar el servicio "                                + "estándar de tiempo de dos horas y media).",                        Toast.LENGTH_SHORT).show();            }        });*/        // Markerslist();        sMapFragment = SupportMapFragment.newInstance();        map = sMapFragment.getMap();       /* LatLng initpos = new LatLng(19.4326, -99.1332);        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(initpos, 16);        map.animateCamera(cameraUpdate);*/        //location        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {            // TODO: Consider calling            //ActivityCompat#requestPermissions            // here to request the missing permissions, and then overriding            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,            //                                          int[] grantResults)            // to handle the case where the user grants the permission. See the documentation            // for ActivityCompat#requestPermissions for more details.            return;        }        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,                MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and Lo        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);        fab.setOnClickListener(new View.OnClickListener() {            @Override            public void onClick(View view) {                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)                        .setAction("Action", null).show();            }        });        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);        drawer.setDrawerListener(toggle);        toggle.syncState();        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);        navigationView.setNavigationItemSelectedListener(this);        setUpMap();//Map initiate        sMapFragment.getMapAsync(this);        // Setting ViewPager for each Tabs        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);        PagerAdapter pagerAdapter =                new PagerAdapter(getSupportFragmentManager(), MainActivity.this);        viewPager.setAdapter(pagerAdapter);        // Give the TabLayout the ViewPager        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);        tabLayout.setupWithViewPager(viewPager);        // Iterate over all tabs and set the custom view        for (int i = 0; i < tabLayout.getTabCount(); i++) {            TabLayout.Tab tab = tabLayout.getTabAt(i);            tab.setCustomView(pagerAdapter.getTabView(i));        }        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();    }    private void copyInputStreamToOutputStream(InputStream in, PrintStream out) {    }    public void ShowStatus(boolean isChecked, String message){        Toast.makeText(this,message,Toast.LENGTH_LONG).show();    }    @Override    public void onBackPressed() {        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        if (drawer.isDrawerOpen(GravityCompat.START)) {            drawer.closeDrawer(GravityCompat.START);        } else {            // super.onBackPressed();        }    }    @Override    public boolean onCreateOptionsMenu(Menu menu) {        // Inflate the menu; this adds items to the action bar if it is present.        getMenuInflater().inflate(R.menu.main, menu);        return true;    }    @Override    public boolean onOptionsItemSelected(MenuItem item) {        // Handle action bar item clicks here. The action bar will        // automatically handle clicks on the Home/Up button, so long        // as you specify a parent activity in AndroidManifest.xml.        int id = item.getItemId();        //noinspection SimplifiableIfStatement        if (id == R.id.action_settings) {            return true;        }        return super.onOptionsItemSelected(item);    }    @Override    public boolean onNavigationItemSelected(MenuItem item) {        // Handle navigation view item clicks here.        int id = item.getItemId();        if (id == R.id.nav_profile) {            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);            //intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_calander) {            Intent intent = new Intent(MainActivity.this, CalanderActivity.class);            intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_history) {            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);            intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_promotions) {            Intent intent = new Intent(MainActivity.this, PromotionsActivity.class);            intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_about) {            Intent intent = new Intent(MainActivity.this, AboutActivity.class);            intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_help) {            Intent intent = new Intent(MainActivity.this, HelpActivity.class);            intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);            startActivity(intent);        } else if (id == R.id.nav_exit) {            if(LoginManager.getInstance()!=null) {            LoginManager.getInstance().logOut();                Intent intent = new Intent(MainActivity.this,Screen1.class);                startActivity(intent);            }            //Intent intent = new Intent(MainActivity.this,Screen1.class);            //startActivity(intent);                logoutUser();        }        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);        drawer.closeDrawer(GravityCompat.START);        return true;    }    @Override    public void onMapReady(GoogleMap googleMap) {    }    public void setUpMap() {        FragmentManager sfm = getSupportFragmentManager();        if (!sMapFragment.isAdded()) {            sfm.beginTransaction().add(R.id.map_container, sMapFragment).commit();        } else {            sfm.beginTransaction().show(sMapFragment).commit();        }    }    @Override    public void onLocationChanged(Location location) {        locations = new ArrayList();        //Add muchacha locations here        locations.add(new LatLng(19.345793, -99.274066));        locations.add(new LatLng(19.344568, -99.275579));        locations.add(new LatLng(19.409961, -99.231645));        locations.add(new LatLng(99.217978, 19.414452));        locations.add(new LatLng(19.430437, -99.202330));        locations.add(new LatLng(19.409562, -99.253761));        locations.add(new LatLng(19.451412, -99.141683));        locations.add(new LatLng(19.433991, -99.143106));        locations.add(new LatLng(19.454517, -99.259103));        map = sMapFragment.getMap();        //map = sMapFragment.getMapAsync();        final LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());        map.clear();        for (int i = 0; i < locations.size(); i++) {            map.addMarker(new MarkerOptions().position(locations.get(i)).draggable(false).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_muchacha1)).snippet(locations.get(i).toString()));        }        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16);        map.addMarker(new MarkerOptions().position(latLng).draggable(true).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_location_mk_pin)).snippet("casa"));        map.animateCamera(cameraUpdate);        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {            // TODO: Consider calling            //    ActivityCompat#requestPermissions            // here to request the missing permissions, and then overriding            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,            //                                          int[] grantResults)            // to handle the case where the user grants the permission. See the documentation            // for ActivityCompat#requestPermissions for more details.            return;        }        locationManager.removeUpdates(this);    }    @Override    public void onStatusChanged(String provider, int status, Bundle extras) {    }    @Override    public void onProviderEnabled(String provider) {    }    @Override    public void onProviderDisabled(String provider) {    }    public void onLocateClick(View view) {        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {            // TODO: Consider calling            //ActivityCompat#requestPermissions            // here to request the missing permissions, and then overriding            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,            //                                          int[] grantResults)            // to handle the case where the user grants the permission. See the documentation            // for ActivityCompat#requestPermissions for more details.            return;        }        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME,                MIN_DISTANCE, this); //You can also use LocationManager.GPS_PROVIDER and Lo    }    @Override    public Filter getFilter() {        return null;    }    @Override    public void onMarkerDragStart(Marker marker) {    }    @Override    public void onMarkerDrag(Marker marker) {        EditText locOut = (EditText) findViewById(R.id.et_locationOutput);        LatLng dragPosition = marker.getPosition();        Geocoder geocoder;        List<Address> addresses;        geocoder = new Geocoder(MainActivity.this, Locale.getDefault());        try {            addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);            String city = addresses.get(0).getAddressLine(1);            //Toast.makeText(MainActivity.this, city, Toast.LENGTH_SHORT).show();            locOut.setText(city);        } catch (IOException e) {            e.printStackTrace();        }       /* double dragLat = dragPosition.latitude;        double dragLong = dragPosition.longitude;        //Log.i("info", "on drag end :" + dragLat + " dragLong :" + dragLong);        Toast.makeText(getApplicationContext(), "Marker Dragged..!", Toast.LENGTH_LONG).show();*/    }    @Override    public void onMarkerDragEnd(Marker marker) {    }    @Override    public void onMapClick(LatLng latLng) {        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));    }    @Override    public void onMapLongClick(LatLng latLng) {        map.addMarker(new MarkerOptions().position(latLng).draggable(true)                .icon(BitmapDescriptorFactory                        .fromResource(R.mipmap.ic_location_mk_pin))                .snippet("casa")).showInfoWindow();    }    //*****************************************************************************    //places functions    private String downloadUrl(String strUrl) throws IOException {        String data = "";        InputStream iStream = null;        HttpURLConnection urlConnection = null;        try {            URL url = new URL(strUrl);            // Creating an http connection to communicate with url            urlConnection = (HttpURLConnection) url.openConnection();            // Connecting to url            urlConnection.connect();            // Reading data from url            iStream = urlConnection.getInputStream();            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));            StringBuffer sb = new StringBuffer();            String line = "";            while ((line = br.readLine()) != null) {                sb.append(line);            }            data = sb.toString();            br.close();        } catch (Exception e) {            Log.d("Excep downloading url", e.toString());        } finally {            iStream.close();            urlConnection.disconnect();        }        return data;    }    @Override    public void onPlaceSelected(Place place) {        Toast.makeText(                getApplicationContext(), "Success", Toast.LENGTH_LONG).show();        String location = place.getAddress().toString();        if (location == null || location.equals("")) {            Toast.makeText(getBaseContext(), "No se ha ingresado una dirección.", Toast.LENGTH_SHORT).show();            return;        }        String url = "https://maps.googleapis.com/maps/api/geocode/json?";        try {            // encoding special characters like space in the user input place            location = URLEncoder.encode(location, "utf-8");        } catch (UnsupportedEncodingException e) {            e.printStackTrace();        }        String address = "address=" + location;        String sensor = "sensor=false";        // url , from where the geocoding data is fetched        url = url + address + "&" + sensor;        // Instantiating DownloadTask to get places from Google Geocoding service        // in a non-ui thread        DownloadTask downloadTask = new DownloadTask();        // Start downloading the geocoding places        downloadTask.execute(url);    }    @Override    public void onError(Status status) {        Toast.makeText(                getApplicationContext(),                "ERROR",                Toast.LENGTH_LONG).show();    }    @Override    public void onStart() {        super.onStart();        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        client.connect();        Action viewAction = Action.newAction(                Action.TYPE_VIEW, // TODO: choose an action type.                "Main Page", // TODO: Define a title for the content shown.                // TODO: If you have web page content that matches this app activity's content,                // make sure this auto-generated web page URL is correct.                // Otherwise, set the URL to null.                Uri.parse("http://host/path"),                // TODO: Make sure this auto-generated app deep link URI is correct.                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")        );        AppIndex.AppIndexApi.start(client, viewAction);    }    @Override    public void onStop() {        super.onStop();        // ATTENTION: This was auto-generated to implement the App Indexing API.        // See https://g.co/AppIndexing/AndroidStudio for more information.        Action viewAction = Action.newAction(                Action.TYPE_VIEW, // TODO: choose an action type.                "Main Page", // TODO: Define a title for the content shown.                // TODO: If you have web page content that matches this app activity's content,                // make sure this auto-generated web page URL is correct.                // Otherwise, set the URL to null.                Uri.parse("http://host/path"),                // TODO: Make sure this auto-generated app deep link URI is correct.                Uri.parse("android-app://com.edu.paytonramirezg.clinchacha/http/host/path")        );        AppIndex.AppIndexApi.end(client, viewAction);        client.disconnect();    }    /**     * A class, to download Places from Geocoding webservice     */    private class DownloadTask extends AsyncTask<String, Integer, String> {        String data = null;        // Invoked by execute() method of this object        @Override        protected String doInBackground(String... url) {            try {                data = downloadUrl(url[0]);            } catch (Exception e) {                Log.d("Background Task", e.toString());            }            return data;        }        // Executed after the complete execution of doInBackground() method        @Override        protected void onPostExecute(String result) {            // Instantiating ParserTask which parses the json data from Geocoding webservice            // in a non-ui thread            ParserTask parserTask = new ParserTask();            // Start parsing the places in JSON format            // Invokes the "doInBackground()" method of the class ParseTask            parserTask.execute(result);        }    }    /**     * A class to parse the Geocoding Places in non-ui thread     */    class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {        JSONObject jObject;        // Invoked by execute() method of this object        @Override        protected List<HashMap<String, String>> doInBackground(String... jsonData) {            List<HashMap<String, String>> places = null;            GeocodeJSONParser parser = new GeocodeJSONParser();            try {                jObject = new JSONObject(jsonData[0]);                /** Getting the parsed data as a an ArrayList */                places = parser.parse(jObject);            } catch (Exception e) {                Log.d("Exception", e.toString());            }            return places;        }        // Executed after the complete execution of doInBackground() method        @Override        protected void onPostExecute(List<HashMap<String, String>> list) {            // Clears all the existing markers            map.clear();            for (int i = 0; i < list.size(); i++) {                // Creating a marker                MarkerOptions markerOptions = new MarkerOptions();                // Getting a place from the places list                HashMap<String, String> hmPlace = list.get(i);                // Getting latitude of the place                double lat = Double.parseDouble(hmPlace.get("lat"));                // Getting longitude of the place                double lng = Double.parseDouble(hmPlace.get("lng"));                // Getting name                String name = hmPlace.get("formatted_address");                LatLng latLng = new LatLng(lat, lng);                // Setting the position for the marker                markerOptions.position(latLng);                // Setting the title for the marker                markerOptions.title(name);                // Placing a marker on the touched position                map.addMarker(markerOptions.draggable(true)                        .icon(BitmapDescriptorFactory                                .fromResource(R.mipmap.ic_location_mk_pin))                        .snippet("Tu Ubicacion")).showInfoWindow();                // Locate the first location                if (i == 0)                    map.animateCamera(CameraUpdateFactory.newLatLng(latLng));            }        }    }    //logout connect to server    private void logoutUser(){        //stringdata used in param map        final String username = ACTIVEUSERNAME != null ? ACTIVEUSERNAME.trim() : "";        SharedPreferences tokenUser = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);        SharedPreferences.Editor editor = tokenUser.edit();        editor.clear();        editor.commit();        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGOUT_URL,                new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();                    }                },                new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();                    }                }){            @Override            protected Map<String,String> getParams(){                Map<String,String> params = new HashMap<String, String>();                params.put(KEY_USERNAME,username);                return params;            }        };        RequestQueue requestQueue = Volley.newRequestQueue(this);        requestQueue.add(stringRequest);    }    //end logout    //register ORDER    private void orderUser() {        //string data used in param map        //final String username = editTextUsername.getText().toString().trim();        final String username = ACTIVEUSERNAME.trim();        final String servicelvl = SERVICELVL.trim();        final String services = SERVICES.trim();        final String total = COSTO.trim();        final String home = TYPEOFHOME.trim();        disableSSLCertificateChecking();        //stripe        //end        StringRequest stringRequest = new StringRequest(Request.Method.POST, ORDER_URL,                new Response.Listener<String>() {                    @Override                    public void onResponse(String response) {                        Toast.makeText(MainActivity.this,response,Toast.LENGTH_LONG).show();                    }                },                new Response.ErrorListener() {                    @Override                    public void onErrorResponse(VolleyError error) {                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();                    }                }){            @Override            protected Map<String,String> getParams(){                Map<String,String> params = new HashMap<String, String>();                params.put(KEY_USERNAME,username);                params.put(KEY_SERVICELVL,servicelvl);                params.put(KEY_TYPEOFHOME,home);                params.put(KEY_SERVICES,services);                params.put(KEY_COSTO,total);                return params;            }        };        RequestQueue requestQueue = Volley.newRequestQueue(this);        requestQueue.add(stringRequest);    }    @Override    public void onClick(View v) {        if(v==orderbtn){//orderButton onClick()            orderUser();            TextView textView35 = (TextView)findViewById(R.id.textView35);            TextView textView37 = (TextView)findViewById(R.id.textView37);            TextView textView38 = (TextView)findViewById(R.id.textView38);            textView35.setVisibility(View.VISIBLE);            textView37.setVisibility(View.VISIBLE);            textView38.setVisibility(View.VISIBLE);            textView38.setText("550000000");            Button cancel = (Button)findViewById(R.id.button2);            cancel.setVisibility(View.VISIBLE);            //receipt.setVisibility(View.GONE);            auto_comp_frag.setVisibility(View.VISIBLE);            locbtn.setVisibility(View.VISIBLE);        }        if(v==conf){            final String prem = "premium";            final String stan = "estandar";            //String Jardin, Refrigerador, Armario, Planchar, Planchado;            /*            if (seekBar.getProgress() == 1) {                SERVICELVL="Premium";                servicelvl_receipt.setText(SERVICELVL);                COSTO="$300.00";                cost_receipt.setText(COSTO);                switch (radiogrouphome.getCheckedRadioButtonId()){                    case R.id.dom_cas:                        TYPEOFHOME="Casa";                        typehome_receipt.setText(TYPEOFHOME);                        break;                    case R.id.dom_dep:                        TYPEOFHOME="Departamento";                        typehome_receipt.setText(TYPEOFHOME);                        break;                }            } else {                SERVICELVL = "Standard";                servicelvl_receipt.setText(SERVICELVL);                COSTO = "$302.00";                cost_receipt.setText(COSTO);                switch (radiogrouphome.getCheckedRadioButtonId()){                    case R.id.dom_cas:                        TYPEOFHOME="Casa";                        typehome_receipt.setText(TYPEOFHOME);                        break;                    case R.id.dom_dep:                        TYPEOFHOME="Departamento";                        typehome_receipt.setText(TYPEOFHOME);                        break;                }            }*/            receipt.setVisibility(View.VISIBLE);            auto_comp_frag.setVisibility(View.GONE);            locbtn.setVisibility(View.GONE);            slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);        }    }//end order    /**     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to     * aid testing on a local box, not for use on production.     */    private static void disableSSLCertificateChecking() {        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {            @Override            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {            }            @Override            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {            }            @Override            public java.security.cert.X509Certificate[] getAcceptedIssuers() {                return new java.security.cert.X509Certificate[0];            }        } };        try {            SSLContext sc = SSLContext.getInstance("TLS");            sc.init(null, trustAllCerts, new java.security.SecureRandom());            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());        } catch (KeyManagementException e) {            e.printStackTrace();        } catch (NoSuchAlgorithmException e) {            e.printStackTrace();        }    }    class PagerAdapter extends FragmentPagerAdapter {        String tabTitles[] = new String[] { "Limpieza", "Tintorería" };        Context context;        public PagerAdapter(FragmentManager fm, Context context) {            super(fm);            this.context = context;        }        @Override        public int getCount() {            return tabTitles.length;        }        @Override        public Fragment getItem(int position) {            switch (position) {                case 0:                    return limpiezaF = new LimpiezaFragment();                case 1:                    return tintoreriaF = new TintoreriaFragment();            }            return null;        }        @Override        public CharSequence getPageTitle(int position) {            // Generate title based on item position            return tabTitles[position];        }        public View getTabView(int position) {            View tab = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);            TextView tv = (TextView) tab.findViewById(R.id.custom_text);            tv.setText(tabTitles[position]);            return tab;        }    }}