package com.ejemplo.alexis_caballero.sigc11app.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.ejemplo.alexis_caballero.sigc11app.dataBase.DataBase;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresastotales;

import java.util.ArrayList;

public class DAOEmpresasTotales {

    DataBase DbEmpresas;
    SQLiteDatabase AD;
    //boolean result = false ;

    public DAOEmpresasTotales(Context ctx){
        super ();
        DbEmpresas = new DataBase(ctx);
    }

     //Abre conexion a base de datos
    public void openConnection(){
    AD = DbEmpresas.getWritableDatabase();
    }

    //Cierra conexion a base de datos
    public void closeConnection(){
        DbEmpresas.close();
    }


    public boolean insertEmpresas (ItemEmpresastotales itemEmpresastotales){
        boolean result = false ;
        AD = DbEmpresas.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("empresa", itemEmpresastotales.getEmpresa());
        cv.put("solicitudes", itemEmpresastotales.getSolicitudes());


        try{
            result = AD.insert(DataBase.TABLE_3, null, cv)>0;

        }catch (SQLException e){
            throw e;
        }
        AD.close();
        return result;

    }

   public ArrayList<ItemEmpresastotales>getAllEmpresas() {

       ArrayList<ItemEmpresastotales> arrayList = new ArrayList<ItemEmpresastotales>();
       AD = DbEmpresas.getWritableDatabase();

       Cursor cursor = AD.query(
               DataBase.TABLE_3,
               new String[]{"id", "empresa", "solicitudes"},
                    null,
                    null,
                    null,
                    null,
                    null
       );
       if (cursor.moveToFirst()) {
           do {
               arrayList.add(new ItemEmpresastotales(cursor.getInt(0),
                                    cursor.getString(1),
                                    cursor.getInt(2)));
           } while (cursor.moveToNext());
       }
       AD.close();
       return arrayList;
   }



    public int getTotalSolicitudes(){

        String sql = "SELECT SUM(solicitudes)AS total FROM EmpresaTotal";
        Cursor cursor = AD.rawQuery(sql, null);
        cursor.moveToFirst();
        int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        cursor.close();
        return total;
    }



}
