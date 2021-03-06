package com.ejemplo.alexis_caballero.sigc11app.dataBase;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexis-caballero on 8/02/17.
 */

public class DataBase extends SQLiteOpenHelper {

    public static final String DB_NAME = "DbConcecionarios";
    public static final int DB_VERSION = 2;
    public static final String TABLE_1 = "EMPRESAS";
    public static final String TABLE_2 = "solicitudes";
    public static final String TABLE_3 =  "EmpresaTotal";


    //Script de las tablas
    public static final String SCRIPT_TABLE_1 = " " +
            "create table "+TABLE_1+" " +
            "(" +
            "id integer primary key autoincrement, " +
            "nombre varchar(40) not null " +
            "); ";

    public static final String SCRIPT_TABLE_2 = " " +
            "create table "+TABLE_2+" " +
            "(" +
            "id integer primary key autoincrement, " +
            "concesionario varchar(75) NULL DEFAULT 'sin datos', " +
            "Solicitud_NIS varchar(45) NOT NULL, " +
            "cable_instalar varchar(120) NULL DEFAULT 'sin datos', " +
            "Tipo_Red varchar(45) NOT NULL " +
            "); ";
    public static final String SCRIPT_TABLE_3 = " " +
            "create table " +TABLE_3+" "+
            "("+
            "id integer primary key autoincrement, " +
            "empresa varchar(75) not null, " +
            "solicitudes int(21) not null"+
            ");";

    public DataBase(Context ctx){
        super(ctx,DB_NAME,null,DB_VERSION);

    }

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SCRIPT_TABLE_1);
        db.execSQL(SCRIPT_TABLE_2);
        db.execSQL(SCRIPT_TABLE_3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
