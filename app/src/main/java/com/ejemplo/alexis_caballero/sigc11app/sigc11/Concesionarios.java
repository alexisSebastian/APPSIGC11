package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ListView;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DaoEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.AdapterEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;
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

public class Concesionarios extends AppCompatActivity {

    public ListView lstEmp;
    public ArrayList<ItemEmpresas> myItemEmp;
    public AdapterEmpresas myAdapterEmp;

    //DAO EMPRESAS;
    public DaoEmpresas myDaoEmp;

    //conexion base de datos
    private String ip = "192.168.42.49";
    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;


    private PropertyInfo empresa = null;

    ProgressDialog dialogAsynk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_concesionarios);

        lstEmp = (ListView)findViewById(R.id.lstConcesionarios);
        myItemEmp = new ArrayList<ItemEmpresas>();

        myDaoEmp = new DaoEmpresas(getApplication());
        myItemEmp = myDaoEmp.getAllEmpresas();


        myAdapterEmp = new AdapterEmpresas(this, myItemEmp);
        lstEmp.setAdapter(myAdapterEmp);
        registerForContextMenu(lstEmp);
    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        menu.add(0,0,0, "Actualizar");
        menu.add(0,1,1, "Ver Solicitudes");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 0:
                new EmpresaEnSegundoPlano().execute("");
                break;
            case 1:
                Intent i = new Intent(Concesionarios.this, Solicitudes.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    public void reload(){
        myDaoEmp = new DaoEmpresas(getApplicationContext());
        myItemEmp = myDaoEmp.getAllEmpresas();
        myAdapterEmp = new AdapterEmpresas(this, myItemEmp);
        lstEmp.setAdapter(myAdapterEmp);
    }

    public ArrayList<ItemEmpresas> LanzarEmpresa(String params){
        METHOD_NAME = "getEmpresa";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;

        ArrayList<ItemEmpresas> myArrayWs = new ArrayList<ItemEmpresas>();

        try {
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            empresa = new PropertyInfo();
            empresa.setName("empresa");
            empresa.setValue(params);
            empresa.setType(String.class);

            request.addProperty(empresa);


            envelo = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelo.dotNet = false;
            envelo.setOutputSoapObject(request);

            transport = new HttpTransportSE(URL_SERVICE);
            transport.call(SOAP_ACTION, envelo);

            String resultado = (String) envelo.getResponse();

            Type tipo = new TypeToken<ArrayList<ItemEmpresas>>() {}.getType();

            Gson convertidoJson = new Gson();

            myArrayWs = convertidoJson.fromJson(resultado, tipo);
        }catch (Exception e){
            e.printStackTrace();
        }

        return myArrayWs;
    }

    //**********************Tarea asyncrona***********************//
    class EmpresaEnSegundoPlano extends AsyncTask<String,Integer, ArrayList<ItemEmpresas>> {

        @Override
        protected ArrayList<ItemEmpresas> doInBackground(String... params) {
            return LanzarEmpresa(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAsynk = ProgressDialog.show(Concesionarios.this, "", "espera", true);
        }


        @Override
        protected void onPostExecute(ArrayList<ItemEmpresas> itemEmpresases) {
            super.onPostExecute(itemEmpresases);
            dialogAsynk.dismiss();

            Log.d(getClass().getSimpleName(), "Tamaño del arrego: " + itemEmpresases.size());


            DaoEmpresas myDao = new DaoEmpresas(getApplicationContext());
            //ItemEmpresas myEmpresa = new ItemEmpresas();

            //myEmpresa.setNombre(itemEmpresases.get(0).getNombre());

            for (ItemEmpresas empresas : itemEmpresases) {
                if (myDao.insertEmpresa(empresas) == true){
                    Crouton.makeText(Concesionarios.this, "Actualizado", Style.INFO).show();
                    reload();
                }else{
                    Crouton.makeText(Concesionarios.this, "Sin Novedades", Style.INFO).show();
                }
            }


            //DaoEmpresas myDao = new DaoEmpresas(getApplicationContext());
            /*for (int i = 0; i < itemEmpresases.size(); i++){
                ItemEmpresas myEmp = new ItemEmpresas();
                myEmp.setNombre(itemEmpresases.get(i).getNombre());
                if (itemEmpresases.size() >= i){
                    if (myDao.insertEmpresa(myEmp) == true){
                        Crouton.makeText(Concesionarios.this, "Actualizado", Style.INFO).show();
                        reload();
                    }
                }else {
                    Crouton.makeText(Concesionarios.this, "Sin Novedades", Style.INFO).show();
                }
            }*/
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
