package com.ejemplo.alexis_caballero.sigc11app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ejemplo.alexis_caballero.sigc11app.dataBase.DataBase;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemSolicitud;

import java.util.ArrayList;

/**
 * Created by sebastian on 10/03/17.
 */

public class DaoSolicitudes {
    DataBase DB;
    SQLiteDatabase AD;

    public DaoSolicitudes(Context cnt){
        super();
        DB = new DataBase(cnt);
    }




    //realzar CRUD
    //Consultar todos los registros de la base de datos
    public boolean insertSolicitud(ItemSolicitud itemSolicitud){
        AD = DB.getWritableDatabase();
        boolean result = false;

        ContentValues CV = new ContentValues();
        CV.put("concesionario", itemSolicitud.getConcesionario());
        CV.put("Solicitud_NIS", itemSolicitud.getSolicitud_NIS());
        CV.put("cable_instalar", itemSolicitud.getCable_instalar());
        CV.put("Tipo_Red", itemSolicitud.getTipo_Red());

        try {
            result = AD.insert(DataBase.TABLE_2,null,CV) > 0;
        }catch (Exception e){
            e.printStackTrace();
        }

        AD.close();
        return result;
    }

    public ArrayList<ItemSolicitud> getAllSolicitudes(){

        ArrayList<ItemSolicitud> lstSlicitud = new ArrayList<ItemSolicitud>();
        AD = DB.getWritableDatabase();

        Cursor myCursor = AD.query(
                DataBase.TABLE_2,
                new String[]{"id","concesionario","Solicitud_NIS","cable_instalar","Tipo_Red"},
                null,
                null,
                null,
                null,
                null
        );
        if (myCursor.moveToFirst()){
            do {
                lstSlicitud.add(new ItemSolicitud(myCursor.getInt(0),myCursor.getString(1),myCursor.getString(2),myCursor.getString(3),myCursor.getString(4)));
            }while (myCursor.moveToNext());
        }

        AD.close();
        return lstSlicitud;
    }
}
