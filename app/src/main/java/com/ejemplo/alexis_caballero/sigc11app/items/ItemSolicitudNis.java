package com.ejemplo.alexis_caballero.sigc11app.items;

import java.io.Serializable;

/**
 * Created by alexis-caballero on 14/02/17.
 */

public class ItemSolicitudNis implements Serializable {
    int id, nis, icon;

    public ItemSolicitudNis(int id, int nis, int icon) {
        this.id = id;
        this.nis = nis;
        this.icon = icon;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ItemSolicitudNis() {
    }

    public ItemSolicitudNis(int id) {
        this.id = id;
    }

    public ItemSolicitudNis(int id, int nis) {
        this.id = id;
        this.nis = nis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNis() {
        return nis;
    }

    public void setNis(int nis) {
        this.nis = nis;
    }
}
