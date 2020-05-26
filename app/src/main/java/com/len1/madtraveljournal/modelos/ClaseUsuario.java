package com.len1.madtraveljournal.modelos;
import com.len1.madtraveljournal.lugares.LugarBar;

import java.io.Serializable;
import java.util.ArrayList;

public class ClaseUsuario implements Serializable {

    private String email;
    private String password;
    private String nombreUsuario;
    private String genero;
    private String urlFoto;
    private ArrayList<LugarBar> baresFavoritos;
    private boolean estoyAqui;
    private String barActual;
    public ClaseUsuario(String email, String password) {
        this.email = email;
        this.password = password;
        baresFavoritos = new ArrayList<>();
        estoyAqui=false;
    }
    public ClaseUsuario(String email,String password,String nombreUsuario,String genero){
        this.email = email;
        this.password = password;
        this.nombreUsuario = nombreUsuario;
        this.genero = genero;
        estoyAqui = false;
    }

    public String getBarActual() {
        return barActual;
    }

    public void setBarActual(String barActual) {
        this.barActual = barActual;
    }

    public ClaseUsuario(){

    }

    public boolean isEstoyAqui() {
        return estoyAqui;
    }

    public void setEstoyAqui(boolean estoyAqui) {
        this.estoyAqui = estoyAqui;
    }

    public ArrayList<LugarBar> getBaresFavoritos() {
        return baresFavoritos;
    }

    public void setBaresFavoritos(ArrayList<LugarBar> baresFavoritos) {
        this.baresFavoritos = baresFavoritos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUduario) {
        this.nombreUsuario = nombreUduario;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
