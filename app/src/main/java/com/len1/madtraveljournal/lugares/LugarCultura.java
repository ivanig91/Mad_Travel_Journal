package com.len1.madtraveljournal.lugares;

public class LugarCultura extends Lugar {
    String precio;
    String horaComienzo;
    String horaFin;
    String eventLocation;
    public LugarCultura(String id, String nombre, String descripcion, String direccion, String latitud, String longitud,String precio,String horaCominezo,String horaFin,String eventLocation) {
        super(id, nombre, descripcion, direccion, latitud, longitud);
        this.precio = precio;
        this.horaComienzo = horaCominezo;
        this.horaFin = horaFin;
        this.eventLocation = eventLocation;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getHoraComienzo() {
        return horaComienzo;
    }

    public void setHoraComienzo(String horaComienzo) {
        this.horaComienzo = horaComienzo;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }
}
