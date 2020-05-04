package com.len1.madtraveljournal;

import java.io.Serializable;

public class ClaseUsuario implements Serializable {

    private String email;
    private String password;
   // private String nombreUduario;
   // private String genero;
    public ClaseUsuario(String email, String password) {
        this.email = email;
        this.password = password;
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
}
