package com.len1.madtraveljournal.modelos;

import com.len1.madtraveljournal.lugares.Lugar;
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
    public ClaseUsuario(String email, String password) {
        this.email = email;
        this.password = password;
        baresFavoritos = new ArrayList<>();
    }
    public ClaseUsuario(String email,String password,String nombreUsuario,String genero){
        this.email = email;
        this.password = password;
        this.nombreUsuario = nombreUsuario;
        this.genero = genero;
    }
    public ClaseUsuario(){

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
