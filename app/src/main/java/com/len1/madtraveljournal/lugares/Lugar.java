package com.len1.madtraveljournal.lugares;

import android.media.Image;

public class Lugar {
    private String id;
    private String nombre;
    private String descripcion;
    private String direccion;
    private String latitud;
    private String longitud;
    private int icono;

    public Lugar(String id, String nombre, String descripcion, String direccion, String latitud, String longitud) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getIcono() {
        return icono;
    }

    public void setIcono(int icono) {
        this.icono = icono;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }
}
