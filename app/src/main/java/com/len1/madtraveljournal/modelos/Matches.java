package com.len1.madtraveljournal.modelos;

import com.google.firebase.firestore.ServerTimestamp;

import java.io.Serializable;
import java.util.Date;

public class Matches implements Serializable {

    private long fecha;
    private String envia;
    private String fotoEnvia;
    private String nombreEnvia;
    private String fotoRecibe;
    private String nombreRecibe;
    private String recibe;
    private String bar;
    private boolean match;

    public Matches(String envia, String recibe, String bar) {
        this.envia = envia;
        this.recibe = recibe;
        this.bar = bar;
        match = false;
    }

    public long getFecha() {
        return fecha;
    }

    public void setFecha(long fecha) {
        this.fecha = fecha;
    }

    public String getNombreEnvia() {
        return nombreEnvia;
    }

    public void setNombreEnvia(String nombreEnvia) {
        this.nombreEnvia = nombreEnvia;
    }

    public String getNombreRecibe() {
        return nombreRecibe;
    }

    public void setNombreRecibe(String nombreRecibe) {
        this.nombreRecibe = nombreRecibe;
    }

    public String getFotoEnvia() {
        return fotoEnvia;
    }

    public void setFotoEnvia(String fotoEnvia) {
        this.fotoEnvia = fotoEnvia;
    }

    public String getFotoRecibe() {
        return fotoRecibe;
    }

    public void setFotoRecibe(String fotoRecibe) {
        this.fotoRecibe = fotoRecibe;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public Matches(){

    }

    public String getEnvia() {
        return envia;
    }

    public void setEnvia(String envia) {
        this.envia = envia;
    }

    public String getRecibe() {
        return recibe;
    }

    public void setRecibe(String recibe) {
        this.recibe = recibe;
    }
}
