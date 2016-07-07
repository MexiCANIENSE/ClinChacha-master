package com.edu.paytonramirezg.clinchacha;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import io.fabric.sdk.android.Fabric;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.stripe.android.*;
import com.stripe.android.model.*;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HelpActivity extends AppCompatActivity{


    ImageButton help_email;
    String ACTIVEUSERNAME;

    TextView header_help, hel_msg;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        Intent intentget = getIntent();
        Bundle b = intentget.getExtras();
        if(b!=null)
        {
            ACTIVEUSERNAME = (String)b.get("ACTIVEUSERNAME");
        }

        expandableListView = (ExpandableListView)findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new CustomExpandableListAdapter(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {


            @Override
            public void onGroupExpand(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                /*Toast.makeText(getApplicationContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();*/

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();
                return false;
            }
        });










        help_email = (ImageButton)findViewById(R.id.imageButton);
        //hel_msg = (TextView)findViewById(R.id.help_info_msg);
        help_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_CC,"clinmx@gmail.com");
                i.putExtra(Intent.EXTRA_SUBJECT, "Atencion a cliente - Reporte de Usuario - AYUDA");
                i.putExtra(Intent.EXTRA_TEXT, "---Reporte AYUDA---");
                try {
                    startActivity(Intent.createChooser(i, "Enviar Correo..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(HelpActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }

            }
        });





        Typeface bold =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/Ubahn.ttf");
        Typeface normal =  Typeface.createFromAsset(getBaseContext().getAssets(), "fonts/ubahn_light.ttf");



        header_help = (TextView)findViewById(R.id.header_help);
        header_help.setTypeface(bold);
       // hel_msg.setTypeface(normal);

    }
    @Override
    public void onBackPressed(){
        Intent intent = new Intent (HelpActivity.this, MainActivity.class);
        intent.putExtra("ACTIVEUSERNAME",ACTIVEUSERNAME);
        startActivity(intent);
    }

}
