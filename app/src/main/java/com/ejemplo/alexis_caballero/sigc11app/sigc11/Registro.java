package com.ejemplo.alexis_caballero.sigc11app.sigc11;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ejemplo.alexis_caballero.sigc11app.R;
import com.ejemplo.alexis_caballero.sigc11app.items.Usuarios;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class Registro extends AppCompatActivity {
    EditText edtRnumCont, edtRUser, edtRContra;
    Button btnReg, btnCan;


    private String ip = "192.168.0.20";


    private final String URL_SERVICE = "http://"+ip+"/sigc11appws/servidor.php#";
    private final String NAMESPACE = "http://"+ip+"/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;


    private PropertyInfo numCont = null ,user = null, clave = null;

    ProgressDialog dialogAsynk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        edtRnumCont = (EditText)findViewById(R.id.edtRegNumCont);
        edtRUser = (EditText)findViewById(R.id.edtRegNewUser);
        edtRContra = (EditText)findViewById(R.id.edtRegContra);

        btnCan = (Button)findViewById(R.id.btnCancelar);
        btnCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnReg = (Button)findViewById(R.id.btnRegistrar);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new backgroun().execute(edtRnumCont.getText().toString().trim(),
                        edtRUser.getText().toString().trim(),edtRContra.getText().toString().trim());
            }
        });

    }

    public int newtUser(String numCont,String user, String pass){
        METHOD_NAME = "newUser";
        SOAP_ACTION = NAMESPACE + METHOD_NAME;
        int resultado = 0;
        try {
            request = new SoapObject(NAMESPACE, METHOD_NAME);

            this.user = new PropertyInfo();
            this.user.setName("usuario");
            this.user.setValue(user);
            this.user.setType(String.class);

            clave = new PropertyInfo();
            clave.setName("clave");
            clave.setValue(pass);
            clave.setType(String.class);

            this.numCont = new PropertyInfo();
            this.numCont.setName("nCont");
            this.numCont.setValue(numCont);
            this.numCont.setType(Integer.class);

            request.addProperty(this.numCont);
            request.addProperty(this.user);
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

    class backgroun extends AsyncTask<String, Integer, Integer>{

        @Override
        protected Integer doInBackground(String... strings) {
            return newtUser(strings[0],strings[1],strings[2]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialogAsynk = ProgressDialog.show(Registro.this, "", "Procesando", true);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if (integer == 1){

            }else {

            }

            dialogAsynk.dismiss();

            if (integer < 1){
                Intent i = new Intent(getApplicationContext(), Login.class);

                Usuarios newUser = new  Usuarios();
                newUser.setNcontrol(edtRnumCont.getText().toString().trim());
                newUser.setName(edtRUser.getText().toString().trim());
                newUser.setPass(edtRContra.getText().toString().trim());

                i.putExtra("myObj", newUser);
                //Crouton.makeText(Registro.this,"Datos ingresados", Style.INFO).show();

                startActivity(i);
            }else {
                Crouton.makeText(Registro.this,"Datos incorrectos", Style.ALERT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Crouton.cancelAllCroutons();
    }
}
