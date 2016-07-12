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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TintoreriaFragment extends Fragment {

    View rootView;
    Spinner spinLavanderia, spinDelicado, spin2Piezas, spinAbrigo, spinEdredon, spinPlanchado, spinChaleco, spinChamarra, spinFalda, spinSueter, spinPalatzo, spinVestido;
    EditText cantLavanderia, cantDelicado, cant2Piezas, cantAbrigo, cantEdredon, cantPlanchado, cantChaleco, cantChamarra, cantFalda, cantSueter, cantPalatzo, cantVestido;
    TextView tvLavanderia, tvDelicado, tv2Piezas, tvAbrigo, tvEdredon, tvPlanchado, tvChaleco, tvChamarra, tvFalda, tvSueter, tvPalatzo, tvVestido;
    int vLavanderia = 0, vDelicado = 0, v2Piezas = 0, vAbrigo = 0, vEdredon = 0, vPlanchado = 15, vChaleco = 42, vChamarra = 57, vFalda = 42, vSueter = 42, vPalatzo = 74, vVestido = 74;

    LinearLayout llTintoreria;
    public static HashMap<Integer,Integer> SelectedTinto = new HashMap<>();
    public static HashMap<Integer,Integer> PriceTinto = new HashMap<>();

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

        initValues();
        initEditListener();
        initSpinners();

        ChangeStatusTintoreria(false);
        return rootView;
    }

    private void initValues(){

        llTintoreria = (LinearLayout) rootView.findViewById(R.id.ll_tintoreria);

        spinLavanderia = (Spinner) rootView.findViewById(R.id.spinner_lavanderia);
        cantLavanderia = (EditText) rootView.findViewById(R.id.et_lavanderia);
        tvLavanderia = (TextView) rootView.findViewById(R.id.tv_lavanderia);

        spinDelicado = (Spinner) rootView.findViewById(R.id.spinner_delicado);
        cantDelicado = (EditText) rootView.findViewById(R.id.et_delicado);
        tvDelicado = (TextView) rootView.findViewById(R.id.tv_delicado);

        spin2Piezas = (Spinner) rootView.findViewById(R.id.spinner_traje2piezas);
        cant2Piezas = (EditText) rootView.findViewById(R.id.et_traje2piezas);
        tv2Piezas = (TextView) rootView.findViewById(R.id.tv_traje2piezas);

        spinAbrigo = (Spinner) rootView.findViewById(R.id.spinner_abrigo);
        cantAbrigo = (EditText) rootView.findViewById(R.id.et_abrigo);
        tvAbrigo = (TextView) rootView.findViewById(R.id.tv_abrigo);

        spinEdredon = (Spinner) rootView.findViewById(R.id.spinner_edredon);
        cantEdredon = (EditText) rootView.findViewById(R.id.et_edredon);
        tvEdredon = (TextView) rootView.findViewById(R.id.tv_edredon);

        spinPlanchado = (Spinner) rootView.findViewById(R.id.spinner_planchado);
        cantPlanchado = (EditText) rootView.findViewById(R.id.et_planchado);
        tvPlanchado = (TextView) rootView.findViewById(R.id.tv_planchado);

        spinChaleco = (Spinner) rootView.findViewById(R.id.spinner_chaleco);
        cantChaleco = (EditText) rootView.findViewById(R.id.et_chaleco);
        tvChaleco = (TextView) rootView.findViewById(R.id.tv_chaleco);

        spinChamarra = (Spinner) rootView.findViewById(R.id.spinner_chamarra);
        cantChamarra = (EditText) rootView.findViewById(R.id.et_chamarra);
        tvChamarra = (TextView) rootView.findViewById(R.id.tv_chamarra);

        spinFalda = (Spinner) rootView.findViewById(R.id.spinner_falda);
        cantFalda = (EditText) rootView.findViewById(R.id.et_falda);
        tvFalda = (TextView) rootView.findViewById(R.id.tv_falda);

        spinSueter = (Spinner) rootView.findViewById(R.id.spinner_sueter);
        cantSueter = (EditText) rootView.findViewById(R.id.et_sueter);
        tvSueter = (TextView) rootView.findViewById(R.id.tv_sueter);

        spinPalatzo = (Spinner) rootView.findViewById(R.id.spinner_palatzo);
        cantPalatzo = (EditText) rootView.findViewById(R.id.et_palatzo);
        tvPalatzo = (TextView) rootView.findViewById(R.id.tv_palatzo);

        spinVestido = (Spinner) rootView.findViewById(R.id.spinner_vestido);
        cantVestido = (EditText) rootView.findViewById(R.id.et_vestido);
        tvVestido = (TextView) rootView.findViewById(R.id.tv_vestido);
    }

    private void initEditListener(){

        cantLavanderia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvLavanderia.setText("$" + (vLavanderia * Integer.parseInt(!cantLavanderia.getText().toString().isEmpty() ?
                        cantLavanderia.getText().toString() : "0")));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(0, Integer.valueOf(editable.toString()));
            }
        });

        cantDelicado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvDelicado.setText("$"+ (vDelicado * Integer.parseInt(!cantDelicado.getText().toString().isEmpty() ?
                        cantDelicado.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(1, Integer.valueOf(editable.toString()));
            }
        });

        cant2Piezas.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tv2Piezas.setText("$"+ (v2Piezas * Integer.parseInt(!cant2Piezas.getText().toString().isEmpty() ?
                        cant2Piezas.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(2, Integer.valueOf(editable.toString()));
            }
        });

        cantAbrigo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvAbrigo.setText("$"+ (vAbrigo * Integer.parseInt(!cantAbrigo.getText().toString().isEmpty() ?
                        cantAbrigo.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(3, Integer.valueOf(editable.toString()));
            }
        });

        cantEdredon.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvEdredon.setText("$"+ (vEdredon * Integer.parseInt(!cantEdredon.getText().toString().isEmpty() ?
                        cantEdredon.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(4, Integer.valueOf(editable.toString()));
            }
        });

        cantPlanchado.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvPlanchado.setText("$"+ (vPlanchado * Integer.parseInt(!cantPlanchado.getText().toString().isEmpty() ?
                        cantPlanchado.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(5, Integer.valueOf(editable.toString()));
            }
        });

        cantChaleco.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvChaleco.setText("$"+ (vChaleco * Integer.parseInt(!cantChaleco.getText().toString().isEmpty() ?
                        cantChaleco.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(6, Integer.valueOf(editable.toString()));
            }
        });

        cantChamarra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvChamarra.setText("$"+ (vChamarra * Integer.parseInt(!cantChamarra.getText().toString().isEmpty() ?
                        cantChamarra.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(7, Integer.valueOf(editable.toString()));
            }
        });

        cantFalda.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvFalda.setText("$"+ (vFalda * Integer.parseInt(!cantFalda.getText().toString().isEmpty() ?
                        cantFalda.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(8, Integer.valueOf(editable.toString()));
            }
        });

        cantSueter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvSueter.setText("$"+ (vSueter * Integer.parseInt(!cantSueter.getText().toString().isEmpty() ?
                        cantSueter.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(9, Integer.valueOf(editable.toString()));
            }
        });

        cantPalatzo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvPalatzo.setText("$"+ (vPalatzo * Integer.parseInt(!cantPalatzo.getText().toString().isEmpty() ?
                        cantPalatzo.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(10, Integer.valueOf(editable.toString()));
            }
        });

        cantVestido.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvVestido.setText("$"+ (vVestido * Integer.parseInt(!cantVestido.getText().toString().isEmpty() ?
                        cantVestido.getText().toString() : "0") ));
            }

            @Override
            public void afterTextChanged(Editable editable) {
                SelectedTinto.put(11, Integer.valueOf(editable.toString()));
            }
        });

    }

    public void ChangeStatusTintoreria(boolean isChecked){

        if(isChecked) llTintoreria.setVisibility(View.VISIBLE);
        else llTintoreria.setVisibility(View.GONE);
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
                PriceTinto.put(0, vLavanderia);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        List<String> precios_delicado = new ArrayList<>();
        precios_delicado.add("NA");
        precios_delicado.add("Poliéster/Algodón $42");
        precios_delicado.add("Seda/Lino $46");
        ArrayAdapter<String> dataAdapterDel = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_delicado);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDelicado.setAdapter(dataAdapterDel);

        spinDelicado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vDelicado = 0;
                        tvDelicado.setText("");
                        break;
                    case 1:
                        vDelicado = 42;
                        tvDelicado.setText(""+ (vDelicado * Integer.parseInt(!cantDelicado.getText().toString().isEmpty() ?
                                cantDelicado.getText().toString() : "0") ));
                        break;
                    case 2:
                        vDelicado = 46;
                        tvDelicado.setText(""+ (vDelicado * Integer.parseInt(!cantDelicado.getText().toString().isEmpty() ?
                                cantDelicado.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(1, vDelicado);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_2piezas = new ArrayList<>();
        precios_2piezas.add("NA");
        precios_2piezas.add("Poliéster/Algodón $84");
        precios_2piezas.add("Seda/Lino $92");
        ArrayAdapter<String> dataAdapter2p = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_2piezas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2Piezas.setAdapter(dataAdapter2p);

        spin2Piezas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        v2Piezas = 0;
                        tv2Piezas.setText("");
                        break;
                    case 1:
                        v2Piezas = 42;
                        tv2Piezas.setText(""+ (v2Piezas * Integer.parseInt(!cant2Piezas.getText().toString().isEmpty() ?
                                cant2Piezas.getText().toString() : "0") ));
                        break;
                    case 2:
                        v2Piezas = 46;
                        tv2Piezas.setText(""+ (v2Piezas * Integer.parseInt(!cant2Piezas.getText().toString().isEmpty() ?
                                cant2Piezas.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(2, v2Piezas);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_abrigo = new ArrayList<>();
        precios_abrigo.add("NA");
        precios_abrigo.add("Corto(a la cadera) $71");
        precios_abrigo.add("Largo(a la rodilla) $101");
        ArrayAdapter<String> dataAdapterAbr = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_abrigo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAbrigo.setAdapter(dataAdapterAbr);

        spinAbrigo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vAbrigo = 0;
                        tvAbrigo.setText("");
                        break;
                    case 1:
                        vAbrigo = 71;
                        tvAbrigo.setText(""+ (vAbrigo * Integer.parseInt(!cantAbrigo.getText().toString().isEmpty() ?
                                cantAbrigo.getText().toString() : "0") ));
                        break;
                    case 2:
                        vAbrigo = 101;
                        tvAbrigo.setText(""+ (vAbrigo * Integer.parseInt(!cantAbrigo.getText().toString().isEmpty() ?
                                cantAbrigo.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(3, vAbrigo);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        List<String> precios_Edre = new ArrayList<>();
        precios_Edre.add("NA");
        precios_Edre.add("Individual/Matrimonial $97");
        precios_Edre.add("Queen/King Size $120");
        ArrayAdapter<String> dataAdapterEdre = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_Edre);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinEdredon.setAdapter(dataAdapterEdre);

        spinEdredon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vEdredon = 0;
                        tvEdredon.setText("");
                        break;
                    case 1:
                        vEdredon = 97;
                        tvEdredon.setText(""+ (vEdredon * Integer.parseInt(!cantEdredon.getText().toString().isEmpty() ?
                                cantEdredon.getText().toString() : "0") ));
                        break;
                    case 2:
                        vEdredon = 120;
                        tvEdredon.setText(""+ (vEdredon * Integer.parseInt(!cantEdredon.getText().toString().isEmpty() ?
                                cantEdredon.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(4, vEdredon);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_planchado = new ArrayList<>();
        precios_planchado.add("Por pieza $15");
        precios_planchado.add("1/2 Docena $90");
        precios_planchado.add("Docena $180");
        ArrayAdapter<String> dataAdapterPlanc = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_planchado);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPlanchado.setAdapter(dataAdapterPlanc);

        spinPlanchado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vPlanchado = 15;
                        tvPlanchado.setText(""+ (vPlanchado * Integer.parseInt(!cantPlanchado.getText().toString().isEmpty() ?
                                cantPlanchado.getText().toString() : "0") ));
                        break;
                    case 1:
                        vPlanchado = 90;
                        tvPlanchado.setText(""+ (vPlanchado * Integer.parseInt(!cantPlanchado.getText().toString().isEmpty() ?
                                cantPlanchado.getText().toString() : "0") ));
                        break;
                    case 2:
                        vPlanchado = 180;
                        tvPlanchado.setText(""+ (vPlanchado * Integer.parseInt(!cantPlanchado.getText().toString().isEmpty() ?
                                cantPlanchado.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(5, vPlanchado);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_chaleco = new ArrayList<>();
        precios_chaleco.add("Vestir $42");
        precios_chaleco.add("Lana/Estambre $42");
        precios_chaleco.add("Relleno (tipo michellin) $57");
        ArrayAdapter<String> dataAdapterChal = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_chaleco);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChaleco.setAdapter(dataAdapterChal);

        spinChaleco.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vChaleco = 42;
                        tvChaleco.setText(""+ (vChaleco * Integer.parseInt(!cantChaleco.getText().toString().isEmpty() ?
                                cantChaleco.getText().toString() : "0") ));
                        break;
                    case 1:
                        vChaleco = 42;
                        tvChaleco.setText(""+ (vChaleco * Integer.parseInt(!cantChaleco.getText().toString().isEmpty() ?
                                cantChaleco.getText().toString() : "0") ));
                        break;
                    case 2:
                        vChaleco = 57;
                        tvChaleco.setText(""+ (vChaleco * Integer.parseInt(!cantChaleco.getText().toString().isEmpty() ?
                                cantChaleco.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(6, vChaleco);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_chamarra = new ArrayList<>();
        precios_chamarra.add("Sencilla $57");
        precios_chamarra.add("Forro/felpa/sintético $67");
        precios_chamarra.add("Rellena de pluma $77");
        ArrayAdapter<String> dataAdapterCham = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_chamarra);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinChamarra.setAdapter(dataAdapterCham);

        spinChamarra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vChamarra = 57;
                        tvChamarra.setText(""+ (vChamarra * Integer.parseInt(!cantChamarra.getText().toString().isEmpty() ?
                                cantChamarra.getText().toString() : "0") ));
                        break;
                    case 1:
                        vChamarra = 67;
                        tvChamarra.setText(""+ (vChamarra * Integer.parseInt(!cantChamarra.getText().toString().isEmpty() ?
                                cantChamarra.getText().toString() : "0") ));
                        break;
                    case 2:
                        vChamarra = 77;
                        tvChamarra.setText(""+ (vChamarra * Integer.parseInt(!cantChamarra.getText().toString().isEmpty() ?
                                cantChamarra.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(7, vChamarra);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_falda = new ArrayList<>();
        precios_falda.add("Sencilla $42");
        precios_falda.add("Forro/felpa/sintético $49");
        precios_falda.add("Rellena de pluma $57");
        ArrayAdapter<String> dataAdapterFalda = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_falda);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFalda.setAdapter(dataAdapterFalda);

        spinFalda.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vFalda = 42;
                        tvFalda.setText(""+ (vFalda * Integer.parseInt(!cantFalda.getText().toString().isEmpty() ?
                                cantFalda.getText().toString() : "0") ));
                        break;
                    case 1:
                        vFalda = 49;
                        tvFalda.setText(""+ (vFalda * Integer.parseInt(!cantFalda.getText().toString().isEmpty() ?
                                cantFalda.getText().toString() : "0") ));
                        break;
                    case 2:
                        vFalda = 57;
                        tvFalda.setText(""+ (vFalda * Integer.parseInt(!cantFalda.getText().toString().isEmpty() ?
                                cantFalda.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(8, vFalda);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_sueter = new ArrayList<>();
        precios_sueter.add("Corto $42");
        precios_sueter.add("Largo $49");
        precios_sueter.add("Con aplicación $59");
        ArrayAdapter<String> dataAdapterSueter = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_sueter);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinSueter.setAdapter(dataAdapterSueter);

        spinSueter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vSueter = 42;
                        tvSueter.setText(""+ (vSueter * Integer.parseInt(!cantSueter.getText().toString().isEmpty() ?
                                cantSueter.getText().toString() : "0") ));
                        break;
                    case 1:
                        vSueter = 49;
                        tvSueter.setText(""+ (vSueter * Integer.parseInt(!cantSueter.getText().toString().isEmpty() ?
                                cantSueter.getText().toString() : "0") ));
                        break;
                    case 2:
                        vSueter = 59;
                        tvSueter.setText(""+ (vSueter * Integer.parseInt(!cantSueter.getText().toString().isEmpty() ?
                                cantSueter.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(9, vSueter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });


        List<String> precios_palatzo = new ArrayList<>();
        precios_palatzo.add("Sencillo $74");
        precios_palatzo.add("Largo $95");
        precios_palatzo.add("Con aplicación $120");
        ArrayAdapter<String> dataAdapterPalatzo = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_palatzo);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPalatzo.setAdapter(dataAdapterPalatzo);

        spinPalatzo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vPalatzo = 74;
                        tvPalatzo.setText(""+ (vPalatzo * Integer.parseInt(!cantPalatzo.getText().toString().isEmpty() ?
                                cantPalatzo.getText().toString() : "0") ));
                        break;
                    case 1:
                        vPalatzo = 95;
                        tvPalatzo.setText(""+ (vPalatzo * Integer.parseInt(!cantPalatzo.getText().toString().isEmpty() ?
                                cantPalatzo.getText().toString() : "0") ));
                        break;
                    case 2:
                        vPalatzo = 120;
                        tvPalatzo.setText(""+ (vPalatzo * Integer.parseInt(!cantPalatzo.getText().toString().isEmpty() ?
                                cantPalatzo.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(10, vPalatzo);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        List<String> precios_vestido = new ArrayList<>();
        precios_vestido.add("Sencillo $74");
        precios_vestido.add("Largo/Cocktail $95");
        precios_vestido.add("Con aplicación $120");
        ArrayAdapter<String> dataAdapterVest = new ArrayAdapter<>(this.getActivity(), android.R.layout.simple_spinner_item, precios_vestido);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinVestido.setAdapter(dataAdapterVest);

        spinVestido.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                switch (position) {
                    case 0:
                        vVestido = 74;
                        tvVestido.setText(""+ (vVestido * Integer.parseInt(!cantVestido.getText().toString().isEmpty() ?
                                cantVestido.getText().toString() : "0") ));
                        break;
                    case 1:
                        vVestido = 95;
                        tvVestido.setText(""+ (vVestido * Integer.parseInt(!cantVestido.getText().toString().isEmpty() ?
                                cantVestido.getText().toString() : "0") ));
                        break;
                    case 2:
                        vVestido = 120;
                        tvVestido.setText(""+ (vVestido * Integer.parseInt(!cantVestido.getText().toString().isEmpty() ?
                                cantVestido.getText().toString() : "0") ));
                        break;
                    default:
                        break;
                }
                PriceTinto.put(11, vVestido);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

    }

}
