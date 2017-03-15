package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;


import com.ejemplo.alexis_caballero.sigc11app.DAO.DaoSolicitudes;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.AdapterSolicitud;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemSolicitud;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Solicitudes extends AppCompatActivity {

    public ListView lstSolicitud;
    public ArrayList<ItemSolicitud> myItemSolicitud;
    public AdapterSolicitud myAdapterSolicitud;

    public DaoSolicitudes myDaoSolicitud;

    //conexion base de datos
    private String ip = "192.168.42.49";
    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;


    private PropertyInfo concesionario = null;

    ProgressDialog dialogAsynk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        lstSolicitud = (ListView)findViewById(R.id.lstSolicitudes);
        myItemSolicitud = new ArrayList<ItemSolicitud>();

        myDaoSolicitud = new DaoSolicitudes(getApplication());
        lstSolicitud.setAdapter(myAdapterSolicitud);
        registerForContextMenu(lstSolicitud);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(0,0,0, "Actualizar");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                new SolicitudSegundoplano().execute("");
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void reload(){
        myDaoSolicitud = new DaoSolicitudes(getApplicationContext());
        myItemSolicitud = myDaoSolicitud.getAllSolicitudes();
        myAdapterSolicitud = new AdapterSolicitud(this, myItemSolicitud);
        lstSolicitud.setAdapter(myAdapterSolicitud);
    }

    public ArrayList<ItemSolicitud> LanzarSolicitud(String params){
        METHOD_NAME = "getConDetail";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;

        ArrayList<ItemSolicitud> myArrayWS = new ArrayList<ItemSolicitud>();

        try {

            request = new SoapObject(NAMESPACE, METHOD_NAME);

            concesionario = new PropertyInfo();
            concesionario.setName("solicitud");
            concesionario.setValue(params);
            concesionario.setType(String.class);

            request.addProperty(concesionario);

            envelo = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelo.dotNet = false;
            envelo.setOutputSoapObject(request);

            transport = new HttpTransportSE(URL_SERVICE);
            transport.call(SOAP_ACTION, envelo);

            String resultado = (String) envelo.getResponse();

            Type tipo = new TypeToken<ArrayList<ItemSolicitud>>() {}.getType();

            Gson convertidoJson = new Gson();

            myArrayWS = convertidoJson.fromJson(resultado, tipo);
        }catch (Exception e){
            e.printStackTrace();
        }

        return myArrayWS;
    }

    class SolicitudSegundoplano extends AsyncTask<String,Integer, ArrayList<ItemSolicitud>>{

        @Override
        protected ArrayList<ItemSolicitud> doInBackground(String... params) {
            return LanzarSolicitud(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAsynk = ProgressDialog.show(Solicitudes.this, "", "Cargando...", true);
        }

        @Override
        protected void onPostExecute(ArrayList<ItemSolicitud> itemSolicituds) {
            super.onPostExecute(itemSolicituds);

            dialogAsynk.dismiss();

            Log.d(getClass().getSimpleName(), "Tamaño del arrego: " + itemSolicituds.size());

            DaoSolicitudes myDao = new DaoSolicitudes(getApplicationContext());

            for (ItemSolicitud solicitud : itemSolicituds) {
                if (myDao.insertSolicitud(solicitud) == true){
                    Crouton.makeText(Solicitudes.this, "Actualizado", Style.INFO).show();
                    reload();
                }else {
                    Crouton.makeText(Solicitudes.this, "Sin Novedades", Style.INFO).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
