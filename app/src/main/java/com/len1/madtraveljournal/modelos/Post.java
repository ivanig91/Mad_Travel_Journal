package com.len1.madtraveljournal.modelos;

public class Post {
    private String nombre;
    private String urlFoto;
    private String comentario;
    private String bar;

    public Post(String nombre, String urlFoto, String comentario, String bar) {
        this.nombre = nombre;
        this.urlFoto = urlFoto;
        this.comentario = comentario;
        this.bar = bar;
    }
    public Post(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
