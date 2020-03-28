package com.len1.madtraveljournal.lugares;

import com.len1.madtraveljournal.R;

public class LugarMonumento extends Lugar {
    public LugarMonumento(String id, String nombre, String descripcion, String direccion, String latitud, String longitud) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.setIcono(R.drawable.mercado);
    }
}
