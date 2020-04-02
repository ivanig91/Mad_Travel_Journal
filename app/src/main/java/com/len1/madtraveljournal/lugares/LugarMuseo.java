package com.len1.madtraveljournal.lugares;

import com.len1.madtraveljournal.R;

public class LugarMuseo extends  Lugar {
    private String horario;

    public LugarMuseo(String id, String nombre, String descripcion, String direccion, String latitud, String longitud, String horario) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.horario = horario;
        this.setIcono(R.drawable.mercado);
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }
}
