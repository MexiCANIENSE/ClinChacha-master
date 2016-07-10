package com.edu.paytonramirezg.clinchacha;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LimpiezaFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    View rootView;
    LinearLayout llLimpieza;
    RadioGroup radiog;
    CheckBox barrer,recamara,banios,recoger,basura,trastes,perro,inodoros,lavarropa,plancharropa,ventanas,cocinar,armarios,refrigerador;

    public SharedPreferences prefServicesLimp;
    SharedPreferences.Editor editor;

    public LimpiezaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        /*checkBox = (CheckBox)v.findViewById(R.id.approved_checkbox);
        checkBox.setChecked(true);

        checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // update your model (or other business logic) based on isChecked
            }
        });*/

        rootView = inflater.inflate(R.layout.fragment_limpieza, container, false);

        prefServicesLimp = getActivity().getSharedPreferences("servLimpieza", Context.MODE_PRIVATE);
        editor = prefServicesLimp.edit();

        InitValues();
        InitListener();

        return rootView;
    }

    private void InitValues(){

        llLimpieza = (LinearLayout) rootView.findViewById(R.id.ll_limpieza);
        radiog = (RadioGroup) rootView.findViewById(R.id.radiogroub_home);
        barrer = (CheckBox) rootView.findViewById(R.id.serv1);
        recamara = (CheckBox) rootView.findViewById(R.id.serv2);
        banios = (CheckBox) rootView.findViewById(R.id.serv3);
        recoger = (CheckBox) rootView.findViewById(R.id.serv4);
        basura = (CheckBox) rootView.findViewById(R.id.serv5);
        trastes = (CheckBox) rootView.findViewById(R.id.serv6);
        perro = (CheckBox) rootView.findViewById(R.id.serv7);
        inodoros = (CheckBox) rootView.findViewById(R.id.serv8);
        lavarropa = (CheckBox) rootView.findViewById(R.id.serv9);
        plancharropa = (CheckBox) rootView.findViewById(R.id.serv10);
        ventanas = (CheckBox) rootView.findViewById(R.id.serv11);
        cocinar = (CheckBox) rootView.findViewById(R.id.serv12);
        armarios = (CheckBox) rootView.findViewById(R.id.serv13);
        refrigerador = (CheckBox) rootView.findViewById(R.id.serv14);

    }

    private void InitListener(){

        editor.putString("TipoDom", "Casa");
        editor.putBoolean("BARRER", true);
        editor.putBoolean("RECAMARA",true);
        editor.putBoolean("BANIOS",true);
        editor.putBoolean("RECOGER",true);
        editor.putBoolean("BASURA",true);
        editor.putBoolean("TRASTES",true);
        editor.putBoolean("PERRO",false);
        editor.putBoolean("INODOROS",false);
        editor.putBoolean("LAVARROPA",false);
        editor.putBoolean("PLANCHARROPA",false);
        editor.putBoolean("VENTANAS",false);
        editor.putBoolean("COCINAR",false);
        editor.putBoolean("ARMARIOS",false);
        editor.putBoolean("REFRIGERADOR",false);
        editor.commit();

        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.dom_cas:
                        editor.putString("TipoDom", "Casa");
                        editor.commit();
                        break;
                    case R.id.dom_dep:
                        editor.putString("TipoDom", "Depto");
                        editor.commit();
                        break;
                }
            }
        });

        barrer.setChecked(true);
        barrer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("BARRER",true);
                else
                    editor.putBoolean("BARRER", false);
                editor.commit();
            }
        });

        recamara.setChecked(true);
        recamara.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("RECAMARA",true);
                else
                    editor.putBoolean("RECAMARA",false);
                editor.commit();
            }
        });

        banios.setChecked(true);
        banios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("BANIOS",true);
                else
                    editor.putBoolean("BANIOS",false);
                editor.commit();
            }
        });

        recoger.setChecked(true);
        recoger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("RECOGER",true);
                else
                    editor.putBoolean("RECOGER",false);
                editor.commit();
            }
        });

        basura.setChecked(true);
        basura.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("BASURA",true);
                else
                    editor.putBoolean("BASURA",false);
                editor.commit();
            }
        });

        trastes.setChecked(true);
        trastes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("TRASTES",true);
                else
                    editor.putBoolean("TRASTES",false);
                editor.commit();
            }
        });

        perro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("PERRO",true);
                else
                    editor.putBoolean("PERRO",false);
                editor.commit();
            }
        });

        inodoros.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("INODOROS",true);
                else
                    editor.putBoolean("INODOROS",false);
                editor.commit();
            }
        });

        lavarropa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("LAVARROPA",true);
                else
                    editor.putBoolean("LAVARROPA",false);
                editor.commit();
            }
        });

        plancharropa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("PLANCHARROPA",true);
                else
                    editor.putBoolean("PLANCHARROPA",false);
                editor.commit();
            }
        });

        ventanas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("VENTANAS",true);
                else
                    editor.putBoolean("VENTANAS",false);
                editor.commit();
            }
        });

        cocinar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("COCINAR",true);
                else
                    editor.putBoolean("COCINAR",false);
                editor.commit();
            }
        });

        armarios.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("ARMARIOS",true);
                else
                    editor.putBoolean("ARMARIOS",false);
                editor.commit();
            }
        });

        refrigerador.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    editor.putBoolean("REFRIGERADOR",true);
                else
                    editor.putBoolean("REFRIGERADOR",false);
                editor.commit();
            }
        });

    }

    public void ChangeStatusLimpieza(boolean isChecked){

        if(isChecked) llLimpieza.setVisibility(View.VISIBLE);
        else llLimpieza.setVisibility(View.GONE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
