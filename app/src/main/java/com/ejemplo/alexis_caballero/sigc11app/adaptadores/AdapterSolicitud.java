package com.ejemplo.alexis_caballero.sigc11app.adaptadores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemSolicitud;

import java.util.ArrayList;

/**
 * Created by sebastian on 10/03/17.
 */

public class AdapterSolicitud extends BaseAdapter {

    protected AppCompatActivity datosActivity;
    protected ArrayList<ItemSolicitud> itemSolicitudes;

    public AdapterSolicitud (AppCompatActivity datosActivity, ArrayList<ItemSolicitud> itemSolicitudes){
        this.datosActivity = datosActivity;
        this.itemSolicitudes = itemSolicitudes;
    }

    @Override
    public int getCount() {
        return itemSolicitudes.size();
    }

    @Override
    public Object getItem(int i) {
        return itemSolicitudes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View v = convertView;

        if (convertView == null){
            LayoutInflater inf = (LayoutInflater)datosActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_solicitudes, null);
        }

        ItemSolicitud myItemSolicitudes = itemSolicitudes.get(i);
        TextView txtCon, txtCable, txtTipo;

        txtCon = (TextView)v.findViewById(R.id.txtCon);
        txtCable = (TextView)v.findViewById(R.id.txtCable);
        txtTipo = (TextView)v.findViewById(R.id.txtTipo);

        txtCon.setText("Concesionario: " + myItemSolicitudes.getConcesionario());
        txtCable.setText("Cable a instalar: " + myItemSolicitudes.getCable_instalar());
        txtTipo.setText("Tipo de red: " + myItemSolicitudes.getTipo_Red());
        return v;
    }
}
