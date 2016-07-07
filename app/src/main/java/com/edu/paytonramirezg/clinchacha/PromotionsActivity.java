package com.edu.paytonramirezg.clinchacha;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.tweetcomposer.ComposerActivity;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.lang.reflect.Array;

public class PromotionsActivity extends AppCompatActivity {

    int[] promocodes = {0};
    String ACTIVEUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);

        Intent intentget = getIntent();
        Bundle b = intentget.getExtras();
        if(b!=null)
        {
            ACTIVEUSERNAME = (String)b.get("ACTIVEUSERNAME");
        }

        Typeface normal =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

        ImageButton b1 = (ImageButton)findViewById(R.id.tweet_share);
        ImageButton b2 = (ImageButton)findViewById(R.id.butt_share);

        TextView outputcode = (TextView)findViewById(R.id.Output_Promocode);
        TextView contentPromo = (TextView)findViewById(R.id.content_promo);
       contentPromo.setTypeface(bold);
        final int code = promocode();

        outputcode.setText("#" + Integer.toString(code));
        outputcode.setTypeface(bold);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myImageFile = new File("/path/to/image");
                Uri myImageUri = Uri.fromFile(myImageFile);

                TweetComposer.Builder builder = new TweetComposer.Builder(PromotionsActivity.this)
                        .text("Recuerda compartir tu código y recibir 20% de desc. En tu servicio Clin. #" + Integer.toString(code))
                        .image(myImageUri);
                builder.show();



            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                //Uri screenshotUri = Uri.parse("android.resource://comexample.sairamkrishna.myapplication/*");
                String content = "Recuerda compartir tu código y recibir 20% de desc. En tu servicio Clin. #" + Integer.toString(code);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, content);
                startActivity(Intent.createChooser(sharingIntent, "Comparte tu codigo usando: "));


            }
        });

    }


    public void onBack(View view){
        Intent intent =  new Intent(PromotionsActivity.this, MainActivity.class);
        intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);
        startActivity(intent);
    }
    public void setup(Typeface normal, Typeface bold){
        TextView content = (TextView) findViewById(R.id.content_promo);
        TextView ele1 = (TextView) findViewById(R.id.header_Promo);


        content.setTypeface(normal);
        ele1.setTypeface(bold);

    }

    public void onBackPressed(){
        Intent intent =new Intent(PromotionsActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public int promocode(){
        int code = (int) (999999 * Math.random()) + 100000;



        for (int i = 0; i<promocodes.length;i++){
            while (promocodes[i]!=0) {
                if (code == promocodes[i]) {
                    promocode();
                } else {
                    promocodes[i] = code;
                    break;
                }
            }
        }


        return code;
    }


}
