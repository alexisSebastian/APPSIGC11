package com.ejemplo.alexis_caballero.sigc11app.adaptadores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresastotales;

import java.util.ArrayList;

/**
 * Created by Alejandro on 15/03/2017.
 */

public class AdapterEmpresasTotales extends BaseAdapter {
    protected AppCompatActivity empresasActivity;
    protected ArrayList<ItemEmpresastotales> itemEmpresase1s;

    public AdapterEmpresasTotales(AppCompatActivity empresasActivity, ArrayList<ItemEmpresastotales> itemEmpresase1s){
        this.empresasActivity = empresasActivity;
        this.itemEmpresase1s = itemEmpresase1s;
    }

    @Override
    public int getCount(){
        return itemEmpresase1s.size();
    }

    //Sirve para regresar un elemento del adaptador (especifico)
    @Override
    public Object getItem(int i) {
        return itemEmpresase1s.get(i);
    }

    //Obtiene el identificador del elemento seleccionado
    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent){
        View v = convertView;

        if (convertView == null){
            LayoutInflater inf = (LayoutInflater)empresasActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_empresastotales,null);
        }
        ItemEmpresastotales myItemEmpresa = itemEmpresase1s.get(i);
        TextView txtEmpresas, txtSolicitudes;

        txtEmpresas = (TextView)v.findViewById(R.id.txtEmpresa);
        txtSolicitudes =(TextView)v.findViewById(R.id.txtSolicitudes);

        txtEmpresas.setText("Empresa: " + myItemEmpresa.getEmpresa());
        txtSolicitudes.setText("Solicitudes: " + myItemEmpresa.getSolicitudes());
        return v;
    }


}
