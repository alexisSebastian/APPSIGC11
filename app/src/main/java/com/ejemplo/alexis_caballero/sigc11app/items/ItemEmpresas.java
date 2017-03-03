package com.ejemplo.alexis_caballero.sigc11app.items;

import java.io.Serializable;

/**
 * Created by alexis-caballero on 7/02/17.
 */

public class ItemEmpresas implements Serializable {

    int id;
    String nombre;



    public ItemEmpresas(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ItemEmpresas(int id) {
        this.id = id;
    }

    public ItemEmpresas(String nombre) {
        this.nombre = nombre;

    }

    public ItemEmpresas() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
