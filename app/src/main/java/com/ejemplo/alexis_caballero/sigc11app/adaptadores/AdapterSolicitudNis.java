package com.ejemplo.alexis_caballero.sigc11app.adaptadores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemSolicitudNis;

import java.util.ArrayList;

/**
 * Created by alexis-caballero on 14/02/17.
 */

public class AdapterSolicitudNis extends BaseAdapter {
    protected AppCompatActivity solicitudActivity;
    protected ArrayList<ItemSolicitudNis> itemSolicitudNises;

    public AdapterSolicitudNis(AppCompatActivity solicitudActivity, ArrayList<ItemSolicitudNis> itemSolicitudNises){
        this.solicitudActivity = solicitudActivity;
        this.itemSolicitudNises = itemSolicitudNises;
    }


    @Override
    public int getCount() {
        return itemSolicitudNises.size();
    }

    @Override
    public Object getItem(int position) {
        return itemSolicitudNises.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        if (convertView == null){
            LayoutInflater inf = (LayoutInflater)solicitudActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_solicitud_nis, null);
        }

        ItemSolicitudNis myitemNis = itemSolicitudNises.get(position);

        TextView txtSn;
        ImageView imgSn;

        txtSn = (TextView)v.findViewById(R.id.txtSolicitudNis);
        imgSn = (ImageView)v.findViewById(R.id.imgSoliNis);

        txtSn.setText("Solicitud: " + myitemNis.getNis());

        return v;
    }
}
