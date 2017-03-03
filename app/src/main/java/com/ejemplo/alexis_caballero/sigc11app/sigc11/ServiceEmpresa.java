package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.ejemplo.alexis_caballero.sigc11app.DAO.DaoEmpresas;
import com.ejemplo.alexis_caballero.sigc11app.R;
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

import static java.lang.Thread.sleep;

/**
 * Created by sebastian on 1/03/17.
 */

public class ServiceEmpresa extends Service implements Runnable {

    int lastID = 0;

    //Conexion ws
    private String ip = "10.169.6.189";
    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;

    int notificacionID;


    private PropertyInfo empresa = null;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(getClass().getSimpleName(),"on Create service");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(getClass().getSimpleName(),"on Star service");

        Thread hilo = new Thread(ServiceEmpresa.this);
        hilo.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(getClass().getSimpleName(), "handler aquí vamos a notificar");
        super.onDestroy();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.d(getClass().getSimpleName(), "handler aquí vamos a notificar");
            //
        }
    };

    @Override
    public void run() {

        try {

            while (true){
                sleep(20000);
                Log.d(getClass().getSimpleName(), "Hilo método run");
                handler.sendEmptyMessage(0);
            }

        }catch (InterruptedException e){
            e.printStackTrace();
        }

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

    class NotificaSegundoPlano extends AsyncTask<String, Integer, ArrayList<ItemEmpresas>>{

        @Override
        protected ArrayList<ItemEmpresas> doInBackground(String... params) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<ItemEmpresas> itemEmpresases) {
            super.onPostExecute(itemEmpresases);

            Log.d(getClass().getSimpleName(),"tamaño del arreglo: " + itemEmpresases.size());

            if (lastID != itemEmpresases.get(0).getId()){
                DaoEmpresas myDao = new DaoEmpresas(getApplicationContext());

                ItemEmpresas newItem = new ItemEmpresas();
                newItem.setNombre(itemEmpresases.get(0).getNombre());

                if (myDao.insertEmpresa(newItem) == true){

                }
            }
        }

        protected void displayNotification(String nombre,String titulo, String subtitulo, String info){
            Intent i = new Intent(ServiceEmpresa.this, Datos.class);
            i.putExtra("notificacionID", notificacionID);

            PendingIntent pendingIntent = PendingIntent.getActivity(ServiceEmpresa.this, 0, i, 0);
            NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);


            CharSequence ticker =subtitulo;
            CharSequence contentTitle = titulo;
            CharSequence contentText = info;
            Notification noti = new NotificationCompat.Builder(ServiceEmpresa.this)
                    .setContentIntent(pendingIntent)
                    .setTicker(ticker)
                    .setContentTitle(contentTitle)
                    .setContentText(contentText)
                    .setSmallIcon(R.drawable.notificaciones)
                    .addAction(R.drawable.notificaciones, ticker, pendingIntent)
                    .setVibrate(new long[] {100, 250, 100, 500})
                    .build();
            nm.notify(notificacionID, noti);
        }
    }
}
