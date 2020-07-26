package com.len1.madtraveljournal.modelos;

public class FotoDelUsuario {
    private String nombreUsuario;
    private String urlFotoUsuario;
    private String urlFotoBar;
    private String nombreBar;
    private String comentarioBar;
    //poner atributo de comentario
    // por lo tanto la coleccion de comentarios ya no me servira


    public FotoDelUsuario(String nombreUsuario, String urlFotoUsuario, String urlFotoBar,
                          String nombreBar, String comentarioBar) {
        this.nombreUsuario = nombreUsuario;
        this.urlFotoUsuario = urlFotoUsuario;
        this.urlFotoBar = urlFotoBar;
        this.nombreBar = nombreBar;
        this.comentarioBar = comentarioBar;
    }

    public String getComentarioBar() {
        return comentarioBar;
    }

    public void setComentarioBar(String comentarioBar) {
        this.comentarioBar = comentarioBar;
    }

    public FotoDelUsuario(){

    }



    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUrlFotoUsuario() {
        return urlFotoUsuario;
    }

    public void setUrlFotoUsuario(String urlFotoUsuario) {
        this.urlFotoUsuario = urlFotoUsuario;
    }

    public String getUrlFotoBar() {
        return urlFotoBar;
    }

    public void setUrlFotoBar(String urlFotoBar) {
        this.urlFotoBar = urlFotoBar;
    }

    public String getNombreBar() {
        return nombreBar;
    }

    public void setNombreBar(String nombreBar) {
        this.nombreBar = nombreBar;
    }
}
