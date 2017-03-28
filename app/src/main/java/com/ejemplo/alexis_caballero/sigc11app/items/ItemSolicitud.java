package com.ejemplo.alexis_caballero.sigc11app.items;

import java.io.Serializable;

/**
 * Created by sebastian on 10/03/17.
 */

public class ItemSolicitud implements Serializable {
    int id;
    String concesionario, Solicitud_NIS, cable_instalar, Tipo_Red;

    public ItemSolicitud() {
    }

    public ItemSolicitud(int id, String concesionario) {
        this.id = id;
        this.concesionario = concesionario;
    }

    public ItemSolicitud(int id, String concesionario, String solicitud_NIS) {
        this.id = id;
        this.concesionario = concesionario;
        Solicitud_NIS = solicitud_NIS;
    }

    public ItemSolicitud(int id, String concesionario, String solicitud_NIS, String cable_instalar) {
        this.id = id;
        this.concesionario = concesionario;
        Solicitud_NIS = solicitud_NIS;
        this.cable_instalar = cable_instalar;
    }

    public ItemSolicitud(int id, String concesionario, String solicitud_NIS, String cable_instalar, String tipo_Red) {
        this.id = id;
        this.concesionario = concesionario;
        Solicitud_NIS = solicitud_NIS;
        this.cable_instalar = cable_instalar;
        Tipo_Red = tipo_Red;
    }

    public ItemSolicitud(String concesionario, String solicitud_NIS, String cable_instalar, String tipo_Red) {
        this.concesionario = concesionario;
        Solicitud_NIS = solicitud_NIS;
        this.cable_instalar = cable_instalar;
        Tipo_Red = tipo_Red;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcesionario() {
        return concesionario;
    }

    public void setConcesionario(String concesionario) {
        this.concesionario = concesionario;
    }

    public String getSolicitud_NIS() {
        return Solicitud_NIS;
    }

    public void setSolicitud_NIS(String solicitud_NIS) {
        Solicitud_NIS = solicitud_NIS;
    }

    public String getCable_instalar() {
        return cable_instalar;
    }

    public void setCable_instalar(String cable_instalar) {
        this.cable_instalar = cable_instalar;
    }

    public String getTipo_Red() {
        return Tipo_Red;
    }

    public void setTipo_Red(String tipo_Red) {
        Tipo_Red = tipo_Red;
    }
}