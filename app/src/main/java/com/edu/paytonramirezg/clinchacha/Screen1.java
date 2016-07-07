package com.edu.paytonramirezg.clinchacha;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.crashlytics.android.Crashlytics;
import com.facebook.appevents.AppEventsLogger;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;


public class Screen1 extends AppCompatActivity {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "gothardpr@gmail.com";
    private static final String TWITTER_SECRET = "DKWpt467";
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);








        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    SharedPreferences userProfile = getSharedPreferences("tokenUser", Context.MODE_PRIVATE);

                    if(userProfile.contains("Token")){

                     //request Location Permissuin
                        if (ContextCompat.checkSelfPermission(Screen1.this,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {

                            // Should we show an explanation?
                            if (ActivityCompat.shouldShowRequestPermissionRationale(Screen1.this,
                                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                                // Show an expanation to the user *asynchronously* -- don't block
                                // this thread waiting for the user's response! After the user
                                // sees the explanation, try again to request the permission.

                            } else {

                                // No explanation needed, we can request the permission.

                                ActivityCompat.requestPermissions(Screen1.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);


                                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                                // app-defined int constant. The callback method gets the
                                // result of the request.
                            }
                        }





                        Intent i = new Intent(Screen1.this, MainActivity.class);
                        startActivity(i);
                    }/*else{
                        Intent i = new Intent(Screen1.this, LoginActivity.class);
                        startActivity(i);
                    }*/

                }
            }
        };
        timerThread.start();



        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
        setContentView(R.layout.activity_screen1);

        splashPlayer();

        Typeface normal = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold = Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    public void onLogin(View view){
        Intent intent = new Intent(Screen1.this, LoginActivity.class);
startActivity(intent);

    }

    public void onRegister(View view){
        Intent intent = new Intent(Screen1.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void setup(Typeface normal, Typeface bold){
        TextView ele1 = (TextView) findViewById(R.id.tv_add);
        Button btn_L = (Button)findViewById(R.id.bt_iniciar);
        Button btn_R = (Button)findViewById(R.id.bt_registrar);
        TextView ele2 = (TextView) findViewById(R.id.tv_webpagelynk);

        String linkText = "Vista nuestra pagina: www.clinapp.es<a href='http://www.clinapp.es/</a> web page.";
        ele2.setText(Html.fromHtml(linkText));
        ele2.setMovementMethod(LinkMovementMethod.getInstance());


        btn_L.setTypeface(bold);
        btn_R.setTypeface(bold);
        ele1.setTypeface(normal);
        ele2.setTypeface(normal);


    }

    public void splashPlayer() {
        final VideoView videoView = (VideoView) findViewById(R.id.videoView);
        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.clin_app_mpeg_4);
        videoView.setVideoURI(video);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });

        ImageView imageView = (ImageView)findViewById(R.id.imageView22);
        imageView.requestFocus();

       /* DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        videoView.setLayoutParams(new RelativeLayout.LayoutParams(screenHeight, metrics.heightPixels));
*/
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        videoView.start();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
