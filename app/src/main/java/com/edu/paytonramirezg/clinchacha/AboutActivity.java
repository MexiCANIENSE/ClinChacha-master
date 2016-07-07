package com.edu.paytonramirezg.clinchacha;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    String ACTIVEUSERNAME;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Intent intentget = getIntent();
        Bundle b = intentget.getExtras();
        if(b!=null)
        {
            ACTIVEUSERNAME = (String)b.get("ACTIVEUSERNAME");
        }

        Typeface normal =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

        TextView textView =(TextView)findViewById(R.id.textView34);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='http://www.clinapp.es'>www.clinapp.es</a>";
        textView.setText(Html.fromHtml(text));
    }

    public void onBackPressed(){
        Intent intent =new Intent(AboutActivity.this, MainActivity.class);
        intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);

        startActivity(intent);
    }


    public void setup(Typeface normal, Typeface bold){
        TextView content = (TextView) findViewById(R.id.content_A);
        TextView ele1 = (TextView) findViewById(R.id.header_AU);

        //ele1.setTextSize(ele1.getWidth());


        content.setTypeface(normal);
        ele1.setTypeface(bold);

    }
}
