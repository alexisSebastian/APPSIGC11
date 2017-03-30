    package com.ejemplo.alexis_caballero.sigc11app.items;

    import java.io.Serializable;

    /**
     * Created by Alejandro on 15/03/2017.
     */

            public class ItemEmpresastotales implements Serializable {
            int id, Solicitudes;
            String Empresa;

        public ItemEmpresastotales(String empresa, int solicitudes) {
            Solicitudes = solicitudes;
            Empresa = empresa;
        }

        public ItemEmpresastotales(int id, String empresa, int solicitudes) {
            this.id = id;
            Solicitudes = solicitudes;
            Empresa = empresa;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {

            this.id = id;
        }

        public int getSolicitudes() {

            return Solicitudes;
        }

        public void setSolicitudes(int solicitudes) {

            Solicitudes = solicitudes;
        }

        public String getEmpresa() {

            return Empresa;
        }

        public void setEmpresa(String empresa) {
            Empresa = empresa;
        }
    }
