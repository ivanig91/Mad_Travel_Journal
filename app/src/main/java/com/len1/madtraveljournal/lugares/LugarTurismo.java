package com.len1.madtraveljournal.lugares;

import com.len1.madtraveljournal.R;

public class LugarTurismo extends Lugar {
    private String servicios;
    public LugarTurismo(String id, String nombre, String descripcion, String direccion, String latitud, String longitud, String servicios) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.servicios = servicios;
        this.setIcono(R.drawable.mercado);
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
}
