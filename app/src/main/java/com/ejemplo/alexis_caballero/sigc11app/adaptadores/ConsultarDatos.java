package com.ejemplo.alexis_caballero.sigc11app.adaptadores;

import android.content.Context;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by alexis-caballero on 7/02/17.
 */

public class ConsultarDatos {

    private final String URL_SERVICE = "http://192.168.42.49sigc11appws/server.php#";
    private final String NAMESPACE = "http://192.168.42.49/sigc11appws/";
    private String METHOD_NAME = "";
    private String SOAP_ACTION = NAMESPACE + METHOD_NAME;


    private SoapObject request;
    private SoapSerializationEnvelope envelo;
    private HttpTransportSE transport;

    public ConsultarDatos(Context ctx) {
        //El super utiliza el contructor de la clase padre
        super();
    }


}
