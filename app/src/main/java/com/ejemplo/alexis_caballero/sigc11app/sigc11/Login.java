package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.Usuarios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Login extends AppCompatActivity {

    EditText edtU,edtP;
    Button btnL, btnR;

    SharedPreferences.Editor myEditor;

    SharedPreferences MyDatosLogin;

    private String ip = "192.168.42.49";
    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;


    private PropertyInfo usuario = null, clave = null;

    ProgressDialog dialogAsynk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyDatosLogin = getSharedPreferences("DatosDelLogin", Context.MODE_PRIVATE);

        myEditor = MyDatosLogin.edit();

        edtU = (EditText)findViewById(R.id.edtUserLogin);
        edtP = (EditText)findViewById(R.id.edtPassLogin);

        btnL = (Button)findViewById(R.id.btnLogin);
        btnL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SegundoPlano().execute(
                        edtU.getText().toString().trim(),
                        edtP.getText().toString().trim());
            }
        });

        btnR = (Button)findViewById(R.id.btnSignUp);
        btnR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Registro.class);
                startActivity(i);
            }
        });
    }

    public int LoginWebService(String user, String pass){


        METHOD_NAME = "loginUser";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        int resultado = 0;
        try {
            request = new SoapObject(NAMESPACE, METHOD_NAME);
            this.usuario = new PropertyInfo();
            this.usuario.setName("usuario");
            this.usuario.setValue(user);
            this.usuario.setType(String.class);

            clave = new PropertyInfo();
            clave.setName("clave");
            clave.setValue(pass);
            clave.setType(String.class);

            request.addProperty(this.usuario);
            request.addProperty(clave);

            envelo = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelo.dotNet = false;
            envelo.setOutputSoapObject(request);
            transport = new HttpTransportSE(URL_SERVICE);
            transport.call(SOAP_ACTION, envelo);
            resultado = (Integer)envelo.getResponse();
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    class SegundoPlano extends AsyncTask<String, Integer, Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            return LoginWebService(strings[0], strings[1]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAsynk = ProgressDialog.show(Login.this, "", "Cargando", true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == 1){

            }else {

            }

            dialogAsynk.dismiss();
            //Toast.makeText(Login.this, "ya entró: " + integer, Toast.LENGTH_LONG).show();

            if (integer > 0){
                Intent i = new Intent(getApplicationContext(), Menu.class);

                Usuarios myUser = new Usuarios();
                myUser.setName(edtU.getText().toString().trim());
                myUser.setPass(edtP.getText().toString().trim());

                i.putExtra("myObj", myUser);

                myEditor.putString("USUARIO",myUser.getName());
                myEditor.putString("PASSWORD",myUser.getPass());
                myEditor.commit();

                startActivity(i);
            }else {
                Crouton.makeText(Login.this,"Datos incorrectos", Style.ALERT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
