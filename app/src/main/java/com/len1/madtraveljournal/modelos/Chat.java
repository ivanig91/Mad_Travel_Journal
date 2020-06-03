package com.len1.madtraveljournal.modelos;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Chat {

    @ServerTimestamp
    private Date fecha;
    private String nombre;
    private String foto;
    private String email;
    private String mensaje;




    public Chat(String nombre, String foto, String email, String mensaje) {
        this.nombre = nombre;
        this.foto = foto;
        this.email = email;
        this.mensaje = mensaje;
    }
    public Chat(){

    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
