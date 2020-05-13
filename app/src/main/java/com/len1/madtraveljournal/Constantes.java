package com.len1.madtraveljournal;

import android.graphics.Bitmap;

public class Constantes {

    public final static String urlBares = "https://www.esmadrid.com/opendata/noche_v1_es.xml";
    public final static String CAT_MUSICA_DIRECTO = "Musica directo";
    public final static String CAT_FLAMENCO = "Flamenco";
    public final static String CAT_TERRAZAS = "Terrazas";
    public final static String CAT_DISCOTECA = "Discoteca";
    public final static String CAT_BAR_DE_COPAS= "Bar de copas";
    public final static String CAT_BARES="Bares";
    public final static String CAT_OTROS ="Otros";
    public final static String CAT_CAFES= "Cafés";
    public final static String CAT_KARAOKE="Karaokes";
    public final static String CAT_COCTELES="Coctelerías";
    public final static String CAT_COHOCO = "Chocolaterías";
    public final static String NODO_MONUMENTOS = "@graph";
    public final static String TABLA_USUARIOS = "Usuarios";
    public final static String MENSAJE_DATOS_VACIOS="Debe rellenar todos los campos para continuar";
    public final static String GENERO_HOM="H";
    public final static String GENERO_LGBT="LGBT";
    public final static String GENERO_MUJ="M";
    public final static int CP_MIN =28000;
    public final static int CP_MAX = 28055;

    public static String arreglaStrings(String string){
        final String LA_P="<p>",LOCURA="<!-- x-tinymce/html -->", LA_PBARRA ="</p>", STRONG ="<strong>",
                STRONG_BARRA = "</strong>", LA_A ="<a>", LA_ABARRA="</a>", LA_B = "<b>", LA_BBARRA = "</b>",
                NBSP = "nbsp;", SUP = "&sup", LA_H2 ="<h2>", LA_H2B = "</h2>", SPAN = "<span>",
                SPANBARRA="</span>", NUM39 = "&#39;", BR = "<br>", BRB = "</br>", EM = "<em>",
                EMB = "</em>", RSQUO = "&rsquo;", LSQUO = "&lsquo;", QUOT = "&quot;", BULL = "&bull;",
                H4 = "<h4>", H4B = "</h4>", BR_ES= "<br />", ACUTES = "acute;", HR = "<hr>", HRB = "</hr>",
                HRBE = "<hr />", AMP = "amp;", TILDE = "tilde;", LDQUO = "&ldquo", RDQUO = "rdquo;",
                H3 = "<h3>", H3B = "</h3>", HELIP = "&hellip;", UML = "uml;", CIRC = "circ;",
                LOCURA_2="<p class=\"heading-2\">", DASH = "dash;",SUP2 ="<sup>",LOCURA_3="<p class=\"heading-3\">";

        if(string.contains(LOCURA_3)){
            string = string.replaceAll(LOCURA_3,"");
        }
        if(string.contains(SUP2)){
            string = string.replaceAll(SUP2,"");
            string = string.replaceAll("</sup>","");
        }
        if(string.contains(LOCURA_2)){
            string = string.replaceAll(LOCURA_2,"");
        }
        if(string.contains(DASH)){
            string = string.replaceAll(DASH,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(CIRC)){
            string = string.replaceAll(CIRC,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(UML)){
            string = string.replaceAll(UML,"");
            string = string.replaceAll("&","");
        }

        if(string.contains(HELIP)){
            string = string.replaceAll(HELIP,"");
        }
        if(string.contains(H3B)){
            string = string.replaceAll(H3B,"");
        }
        if(string.contains(H3)){
            string = string.replaceAll(H3,"");
        }
        if(string.contains(RDQUO)){
            string = string.replaceAll(RDQUO,"");
        }
        if(string.contains(LDQUO)){
            string = string.replaceAll(LDQUO,"");
        }

        if(string.contains(TILDE)){
            string = string.replaceAll(TILDE,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(AMP)){
            string = string.replaceAll(AMP,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(HRBE)){
            string = string.replaceAll(HRBE,"");
        }
        if(string.contains(HRB)){
            string = string.replaceAll(HRB,"");
        }
        if(string.contains(HR)){
            string = string.replaceAll(HR,"");
        }

        if(string.contains(ACUTES)){
            string = string.replaceAll(ACUTES,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(BR_ES)){
            string = string.replaceAll(BR_ES,"");
        }
        if(string.contains(H4B)){
            string = string.replaceAll(H4B,"");
        }
        if(string.contains(H4)){
            string = string.replaceAll(H4,"");
        }
        if(string.contains(BULL)){
            string = string.replaceAll(BULL,"");
        }
        if(string.contains(QUOT)){
            string = string.replaceAll(QUOT,"");
        }

        if(string.contains(LSQUO)){
            string= string.replaceAll(LSQUO,"");
        }
        if(string.contains(RSQUO)){
            string = string.replaceAll(RSQUO,"");
        }
        if(string.contains(LA_P)){
            string = string.replaceAll(LA_P,"");
        }
        if (string.contains(LOCURA)){
            string = string.replaceAll(LOCURA,"");
        }
        if(string.contains(LA_PBARRA)){
            string = string.replaceAll(LA_PBARRA,"");
        }
        if(string.contains(STRONG)){
            string = string.replaceAll(STRONG,"");
        }
        if(string.contains(STRONG_BARRA)){
            string = string.replaceAll(STRONG_BARRA,"");
        }
        if(string.contains(LA_A)){
            string = string.replaceAll(LA_A,"");
        }
        if(string.contains(LA_ABARRA)){
            string = string.replaceAll(LA_ABARRA,"");
        }
        if(string.contains(LA_B)){
            string = string.replaceAll(LA_B,"");
        }
        if(string.contains(LA_BBARRA)){
            string = string.replaceAll(LA_BBARRA,"");
        }
        if(string.contains(NBSP)){
            string = string.replaceAll(NBSP,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(SUP)){
            string = string.replaceAll(SUP,"");
        }
        if(string.contains(LA_H2)){
            string = string.replaceAll(LA_H2,"");
        }
        if(string.contains(LA_H2B)){
            string =string.replaceAll(LA_H2B,"");
        }
        if(string.contains(NUM39)){
            string = string.replaceAll(NUM39,"");
        }
        if(string.contains(SPAN)){
            string = string.replaceAll(SPAN,"");
        }
        if(string.contains(SPANBARRA)){
            string = string.replaceAll(SPANBARRA,"");
        }
        if(string.contains(BR)){
            string = string.replaceAll(BR,"");
        }
        if(string.contains(BRB)){
            string = string.replaceAll(BRB,"");
        }
        if(string.contains(EM)){
            string = string.replaceAll(EM,"");
        }
        if(string.contains(EMB)){
            string = string.replaceAll(EMB,"");
        }


        return string;
    }


}
