package com.edu.paytonramirezg.clinchacha;

/**
 * Created by PaytonRamirezG on 6/7/16.
 */


import android.text.Layout;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {


    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();


        List<String> ayuda = new ArrayList<String>();
        ayuda.add("Comunicate con nosotros, envia tu comentario o reporte de algun caso.");


        List<String> services = new ArrayList<String>();
        services.add("Servicio estándar:\n\nEl servicio incluye los siguientes servicio: Limpieza General, de Ventanas, de tu Refrigerador y de tu Jardin (sólo la limpieza).\n");
        services.add("Servicio premium:\n\nEl servicio te incluye la limpieza estándar Clin y lavado de hasta una docena de tu ropa\n");

        List<String> Pedidos = new ArrayList<String>();
        Pedidos.add("Departamento: ");
        Pedidos.add("Casa:\nSe considera como una casa de hasta 2 pisos, servicio máx. De 3:00 min\n\t\tDespués de las 3 horas = 50.00 pesos cada 30 min. ");
        Pedidos.add("Servicios Extras:\n\t\tJardín (Sólo limpieza)\n\t\tRefrigerador\n\t\tArmario/Closet\n\t\tPlanchar\n\nLos servicios extras pueden demorar más tiempo y rebasar el servicio estándar de 3:00 hrs.\nSe cobrara 60.00 pesos cada 30 min addicionales.");

        expandableListDetail.put("Nuestros Niveles de servicio", services);
        expandableListDetail.put("Tipos de Servicos", Pedidos);
        expandableListDetail.put("Comunicate con nosotros", ayuda);





        return expandableListDetail;
    }



}