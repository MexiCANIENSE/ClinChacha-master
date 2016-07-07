package com.edu.paytonramirezg.clinchacha;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RatingBar;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    private Object[][][][][][] history;
    private int count;
    private String[] Name;
    private Uri[] Muchacha;
    private String[] Date;
    private int[] stars;
    private double[] cost;
    String dollarS = "$", ACTIVEUSERNAME;


    int COUNT[];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intentget = getIntent();
        Bundle b = intentget.getExtras();
        if(b!=null)
        {
            ACTIVEUSERNAME = (String)b.get("ACTIVEUSERNAME");
        }

        Typeface normal =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface bold =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");
        setup(normal, bold);

    }


    private void addToArray(String employee , Uri chacha_URI, String Day, String Month, String Year,
                            int rating, double cosTT)
    {


        for (int f = 0; f <=1000; f++)
            for (int i = 0; i <= count; i++) {
                Name[i] = employee;
                Muchacha[i] = chacha_URI;
                Date[i] = Day.concat("/" + Month + "/" + Year);
                stars[i] = rating;
                cost[i] = cosTT;
                COUNT[i] = count + 1;


                //  history[f][f][f][f][f][f] = new Object[COUNT[i]][Integer.parseInt(Name[i])][Integer.parseInt(uri)][Integer.parseInt(Date[i]][stars[i]][Integer.parseInt(cost[i]];


                TextView emply = (TextView) findViewById(R.id.tv_nameH);
                TextView cost = (TextView) findViewById(R.id.tv_costH);
                TextView date = (TextView) findViewById(R.id.tv_date_H);
                //RatingBar rate = (RatingBar) findViewById(R.id.rb_ratingH);




            }
    }

    private void createElememt()
    {

    }


    public void setup(Typeface normal, Typeface bold){
        TextView ele1 = (TextView) findViewById(R.id.header_H);
        TextView ele2 = (TextView) findViewById(R.id.tv_nameH);
        TextView ele3 = (TextView) findViewById(R.id.tv_costH);
        TextView ele4 = (TextView) findViewById(R.id.tv_date_H);




        ele1.setTypeface(bold);
        ele2.setTypeface(normal);
        ele3.setTypeface(normal);
        ele4.setTypeface(normal);



    }

    public void onBackPressed(){
        Intent intent =new Intent(HistoryActivity.this, MainActivity.class);
        intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);

        startActivity(intent);
    }




}
