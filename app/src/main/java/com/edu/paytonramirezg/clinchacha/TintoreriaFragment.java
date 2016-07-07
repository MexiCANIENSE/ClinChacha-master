package com.edu.paytonramirezg.clinchacha;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TintoreriaFragment extends Fragment {

    View rootView;
    Spinner spinLavanderia;
    EditText cantLavanderia;
    TextView tvLavanderia;
    int vLavanderia = 27;

    public TintoreriaFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_tintoreria, container, false);

        spinLavanderia = (Spinner) rootView.findViewById(R.id.spinner_lavanderia);
        cantLavanderia = (EditText) rootView.findViewById(R.id.et_lavanderia);
        tvLavanderia = (TextView) rootView.findViewById(R.id.tv_lavanderia);

        initEditListener();
        initSpinners();

        return rootView;
    }

    private void initEditListener(){

        cantLavanderia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvLavanderia.setText("$"+ (vLavanderia * Integer.parseInt(!cantLavanderia.getText().toString().isEmpty() ?
                        cantLavanderia.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }



    private void initSpinners(){

        List<String> precios_lavanderia = new ArrayList<>();
        precios_lavanderia.add("NA");
        precios_lavanderia.add("Precio por Kg $27");
        precios_lavanderia.add("Carga mínima 3Kg $81");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_lavanderia);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLavanderia.setAdapter(dataAdapter);

        spinLavanderia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vLavanderia = 0;
                        tvLavanderia.setText("");
                        break;
                    case 1:
                        vLavanderia = 27;
                        tvLavanderia.setText(""+ (vLavanderia * Integer.parseInt(!cantLavanderia.getText().toString().isEmpty() ?
                                cantLavanderia.getText().toString() : "0") ));
                        break;
                    case 2:
                        vLavanderia = 27;
                        tvLavanderia.setText(""+ (vLavanderia * Integer.parseInt(!cantLavanderia.getText().toString().isEmpty() ?
                                cantLavanderia.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }

}
