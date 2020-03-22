package com.len1.madtraveljournal.lugares;

public class LugarMercado extends Lugar {
    private String horario;
    private String servicios;
    public LugarMercado(String id, String nombre, String descripcion, String direccion, String latitud, String longitud, String horario, String servicios) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.horario= horario;
        this.servicios = servicios;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getServicios() {
        return servicios;
    }

    public void setServicios(String servicios) {
        this.servicios = servicios;
    }
}
