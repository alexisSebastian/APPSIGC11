package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DAOEmpresasTotales;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.AdapterEmpresasTotales;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresastotales;
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

public class Empresas extends AppCompatActivity {

    public ListView lstEmpresas;
    public ArrayList<ItemEmpresastotales> myItemEmpresa1s;
    public AdapterEmpresasTotales myAdapterE;

    public DAOEmpresasTotales myDAO;

    //conexion base de datos
    private String ip = "192.168.42.49";
    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;


    private PropertyInfo total = null;

    ProgressDialog dialogAsynk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

        lstEmpresas = (ListView)findViewById(R.id.lstEmpresas);
        myItemEmpresa1s = new ArrayList<ItemEmpresastotales>();


        myDAO = new DAOEmpresasTotales(getApplication());
        myItemEmpresa1s = myDAO.getAllEmpresas();

        myAdapterE = new AdapterEmpresasTotales(this, myItemEmpresa1s);
        lstEmpresas.setAdapter(myAdapterE);
        registerForContextMenu(lstEmpresas);

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu){
        menu.add(0,0,0, "Actualizar");
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case 0:
                new SolicitudSegundoplano().execute("");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inf = (MenuInflater)getMenuInflater();
        menu.setHeaderTitle("Opciones");

        inf.inflate(R.menu.menu_grafica, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo inf = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        switch (item.getItemId()){
            case R.id.ContextMenuGrafica:
                Intent i = new Intent(getApplicationContext(),Graficos.class);
                startActivity(i);
                break;
        }
        return super.onContextItemSelected(item);
    }

    public void reload(){
        myDAO = new DAOEmpresasTotales(getApplicationContext());
        myItemEmpresa1s = myDAO.getAllEmpresas();
        myAdapterE = new AdapterEmpresasTotales(this, myItemEmpresa1s);
        lstEmpresas.setAdapter(myAdapterE);
    }


    public ArrayList<ItemEmpresastotales> lanzarEmpresa(String param){
        METHOD_NAME = "getTotalSolicitudes";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;

        ArrayList<ItemEmpresastotales>myArrayWS = new ArrayList<ItemEmpresastotales>();

        try {
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            total = new PropertyInfo();
            total.setName("total");
            total.setValue(param);
            total.setType(String.class);

            request.addProperty(total);

            envelo = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelo.dotNet = false;
            envelo.setOutputSoapObject(request);

            transport = new HttpTransportSE(URL_SERVICE);
            transport.call(SOAP_ACTION, envelo);

            String resultadoWS = (String)envelo.getResponse();

            Type tipo = new TypeToken<ArrayList<ItemEmpresastotales>>(){
            }.getType();

            Gson convertedJson = new Gson();
            myArrayWS = convertedJson.fromJson(resultadoWS, tipo);

        }catch (Exception e){
            e.printStackTrace();
        }
        return myArrayWS;
    }

    class SolicitudSegundoplano extends AsyncTask<String, Integer, ArrayList<ItemEmpresastotales>> {

        @Override
        protected ArrayList<ItemEmpresastotales>doInBackground(String...params){
            return lanzarEmpresa(params[0]);
        }

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialogAsynk = ProgressDialog.show(Empresas.this, "","espera", true);

        }

        @Override
        protected void onPostExecute(ArrayList<ItemEmpresastotales> itemEmpresase1s){
            super.onPostExecute(itemEmpresase1s);

            dialogAsynk.dismiss();

            Log.d(getClass().getSimpleName(), "Tamaño del arreglo: " + itemEmpresase1s.size());
            DAOEmpresasTotales myDao = new DAOEmpresasTotales(getApplicationContext());

            for (ItemEmpresastotales empresas : itemEmpresase1s){
                if (myDao.insertEmpresas(empresas) == true){
                    //Crouton.makeText(Empresas.this,"Actualizado", Style.INFO).show();
                    reload();
                }else{
                    Crouton.makeText(Empresas.this, "Sin Novedades", Style.INFO).show();
                }
            }
        }

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
