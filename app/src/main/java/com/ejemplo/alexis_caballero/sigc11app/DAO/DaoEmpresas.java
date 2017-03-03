package com.ejemplo.alexis_caballero.sigc11app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ejemplo.alexis_caballero.sigc11app.dataBase.DataBase;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;

import java.util.ArrayList;


/**
 * Created by alexis-caballero on 8/02/17.
 */

public class DaoEmpresas {

    DataBase db;

    SQLiteDatabase ad;

    public DaoEmpresas(Context cnt){
        super();
        db = new DataBase(cnt);
    }

    //realzar CRUD
    //Consultar todos los registros de la base de datos

    public boolean insertEmpresa(ItemEmpresas empresas){
        ad = db.getWritableDatabase();

        boolean result = false;

        ContentValues cv = new ContentValues();

        cv.put("nombre",empresas.getNombre());

        try {
            result = ad.insert(DataBase.TABLE_1,null,cv) > 0;
        }catch (Exception e){
            throw e;
        }

        ad.close();
        return result;
    }



    public ArrayList<ItemEmpresas> getAllEmpresas(){

        ArrayList<ItemEmpresas> lstEmpresas = new ArrayList<ItemEmpresas>();
        ad = db.getWritableDatabase();

        Cursor myCursor = ad.query(
                DataBase.TABLE_1,
                new String[]{
                        "id","nombre"},
                        null,
                        null,
                        null,
                        null,
                        null
        );
        if (myCursor.moveToFirst()) {
            do {
                lstEmpresas.add(new ItemEmpresas(myCursor.getInt(0),
                        myCursor.getString(1)));
            }while (myCursor.moveToNext());
        }

        ad.close();
        return lstEmpresas;
    }

    //actualizar las empresas

    public  boolean upDateEmpresa (ItemEmpresas empresas){
        ad = db.getWritableDatabase();

        //variable del resultado
        boolean result = false;

        ContentValues cv = new ContentValues();

        cv.put("nombre", empresas.getNombre());

        try {
            result = ad.update(DataBase.TABLE_1,cv,"id_empresa="+empresas.getId(),null)>0;
        }catch (Exception e){
            e.printStackTrace();
        }

        ad.close();

        return result;
    }
}
