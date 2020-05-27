package com.len1.madtraveljournal.modelos;

public class Matches {
    private String envia;
    private String recibe;
    private String bar;
    private boolean match;

    public Matches(String envia, String recibe, String bar) {
        this.envia = envia;
        this.recibe = recibe;
        this.bar = bar;
        match = false;
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
