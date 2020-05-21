package com.len1.madtraveljournal.modelos;

public class Post {

    private ClaseUsuario usuario;
    private String comentario;
    private String bar;

    public Post(ClaseUsuario usuario, String comentario, String bar) {
        this.usuario = usuario;
        this.comentario = comentario;
        this.bar = bar;
    }

    public ClaseUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(ClaseUsuario usuario) {
        this.usuario = usuario;
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
