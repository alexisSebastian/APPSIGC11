package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DaoEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.AdapterEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.AdapterSolicitudNis;
import com.ejemplo.alexis_caballero.sigc11app.adaptadores.ConsultarDatos;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.items.ItemSolicitudNis;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.kobjects.util.Strings;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import static com.ejemplo.alexis_caballero.sigc11app.R.id.lstEmpresas;
import static com.ejemplo.alexis_caballero.sigc11app.R.id.start;

public class Datos extends AppCompatActivity {

    public ListView lstEmpresas;
    public ArrayList<ItemEmpresas> myItemEp;
    public AdapterEmpresas myAdapterE;


    //Conexion ws
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
        setContentView(R.layout.activity_datos);

        lstEmpresas = (ListView)findViewById(R.id.lstEmpresas);
        ArrayList<ItemEmpresas> myItemEp = new ArrayList<ItemEmpresas>();

        try {
            new AsyncronoEmp().execute();


        }catch (Exception e){
            e.printStackTrace();
        }

        myAdapterE = new AdapterEmpresas(this,myItemEp);
        //lstEmpresas.setAdapter(myAdapterE);
        //registerForContextMenu(lstEmpresas);


    }


    //***********Metodo para obtener las EMPRESAS*********//
    public ArrayList<ItemEmpresas> getAllEmpresas(String params){
        METHOD_NAME = "getEmpresas";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;

        ArrayList<ItemEmpresas> myArrayWSE = new ArrayList<ItemEmpresas>();
        try {
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            this.empresa = new PropertyInfo();
            this.empresa.setName("empresa");
            this.empresa.setValue(params);
            this.empresa.setType(String.class);

            request.addProperty(this.empresa);

            envelo = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelo.dotNet=false;
            envelo.setOutputSoapObject(request);

            transport = new HttpTransportSE(URL_SERVICE);
            transport.call(SOAP_ACTION, envelo);

            String resultadoWS = (String) envelo.getResponse();

            Type tipo = new TypeToken<ArrayList<ItemEmpresas>>(){}.getType();

            Gson convertidoJson = new Gson();

            myArrayWSE = convertidoJson.fromJson(resultadoWS, tipo);

        }catch (Exception e){
            e.printStackTrace();
        }

        return myArrayWSE;
    }

    //****************Tarea Asyncrona para trabajar segundo plano obtencion de empresas**********//
    class AsyncronoEmp extends AsyncTask<String, Integer, ArrayList<ItemEmpresas>>{

        @Override
        protected ArrayList<ItemEmpresas> doInBackground(String... params) {
            return getAllEmpresas(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(ArrayList<ItemEmpresas> itemEmpresases) {
            super.onPostExecute(itemEmpresases);
            dialogAsynk.dismiss();

            if(itemEmpresases.size()> 1){
                ItemEmpresas myEmpresa = new ItemEmpresas();
                myEmpresa.setNombre(itemEmpresases.get(0).getNombre());
            }else {
                Crouton.makeText(Datos.this, "Sin Novedades", Style.INFO).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}


