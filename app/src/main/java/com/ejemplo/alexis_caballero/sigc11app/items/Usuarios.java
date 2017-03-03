package com.ejemplo.alexis_caballero.sigc11app.items;

import java.io.Serializable;

/**
 * Created by alexis-caballero on 26/01/17.
 */

public class Usuarios implements Serializable {

    int id;
    String name, pass,ncontrol;

    public Usuarios() {
    }

    public Usuarios(int id, String ncontrol) {
        this.id = id;
        this.ncontrol = ncontrol;
    }

    public Usuarios(String name, String pass, int id) {
        this.name = name;
        this.pass = pass;
        this.id = id;
    }

    public Usuarios(String ncontrol, String name, String pass) {
        this.ncontrol = ncontrol;
        this.name = name;
        this.pass = pass;
    }

    public Usuarios(int id, String ncontrol, String name, String pass) {
        this.id = id;
        this.ncontrol = ncontrol;
        this.name = name;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNcontrol(String trim) {
        return ncontrol;
    }

    public void setNcontrol(String ncontrol) {
        this.ncontrol = ncontrol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
