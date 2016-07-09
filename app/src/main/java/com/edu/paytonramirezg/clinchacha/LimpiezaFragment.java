package com.edu.paytonramirezg.clinchacha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LimpiezaFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    View rootView;
    LinearLayout llLimpieza;
    RadioGroup radiog;

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

        InitValues();
        InitListener();

        return rootView;
    }

    private void InitValues(){

        llLimpieza = (LinearLayout) rootView.findViewById(R.id.ll_limpieza);
        radiog = (RadioGroup) rootView.findViewById(R.id.radiogroub_home);

    }

    private void InitListener(){
        radiog.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.dom_cas:
                        break;
                    case R.id.dom_dep:
                        break;
                }
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
