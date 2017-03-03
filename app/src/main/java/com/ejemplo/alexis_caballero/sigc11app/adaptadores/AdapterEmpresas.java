package com.ejemplo.alexis_caballero.sigc11app.adaptadores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by alexis-caballero on 17/02/17.
 */

public class AdapterEmpresas extends BaseAdapter {

    protected AppCompatActivity datosAtivity;
    protected ArrayList<ItemEmpresas> itemEmpresas;

    public AdapterEmpresas (AppCompatActivity datosAtivity, ArrayList<ItemEmpresas> itemEmpresas){
        this.datosAtivity = datosAtivity;
        this.itemEmpresas = itemEmpresas;
    }

    @Override
    public int getCount() {
        return itemEmpresas.size();
    }

    @Override
    public Object getItem(int i) {
        return itemEmpresas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup parent) {

        View v = convertview;

        if (convertview == null){
            LayoutInflater inf = (LayoutInflater)datosAtivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_empresas, null);
        }

        ItemEmpresas myItemEmpresas = itemEmpresas.get(i);
        TextView txtEmpresa;

        txtEmpresa = (TextView)v.findViewById(R.id.txtEmpresa);

        txtEmpresa.setText("Empresa: " + myItemEmpresas.getNombre());

        return v;
    }
}
