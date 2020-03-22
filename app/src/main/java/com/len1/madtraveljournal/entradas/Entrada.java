package com.len1.madtraveljournal.entradas;

public class Entrada {
    private String id;
    private String comentario;
    private  String foto;
    private  String usuario;
    private String rating;

    public Entrada(String id, String comentario, String foto, String usuario, String rating) {
        this.id = id;
        this.comentario = comentario;
        this.foto = foto;
        this.usuario = usuario;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
