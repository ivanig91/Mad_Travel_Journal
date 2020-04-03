package com.len1.madtraveljournal.lugares;

import com.len1.madtraveljournal.R;

public class LugarBar extends Lugar {
    private String fotoUrl;
    private String categoria;
    public LugarBar(String id, String nombre, String descripcion, String direccion, String latitud, String longitud,String fotoUrl,String categoria) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.fotoUrl = fotoUrl;
        this.categoria = categoria;
        this.setIcono(R.drawable.mercado);
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
