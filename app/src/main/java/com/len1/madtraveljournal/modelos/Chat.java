package com.len1.madtraveljournal.modelos;

public class Chat {

    private String nombre;
    private String foto;
    private String email;
    private String mensaje;
    private String fecha;


    public Chat(String nombre, String foto, String email, String mensaje) {
        this.nombre = nombre;
        this.foto = foto;
        this.email = email;
        this.mensaje = mensaje;
    }
    public Chat(){

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
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
