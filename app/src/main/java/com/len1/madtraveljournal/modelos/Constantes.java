package com.len1.madtraveljournal.modelos;

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
    public final static String SUBCOLECION_MATCH ="MatchSubColection";
    public final static String TABLA_COMENTARIOS = "Comentarios";
    public final static String TABLA_MATCH ="Match";
    public final static String MENSAJE_DATOS_VACIOS="Debe rellenar todos los campos para continuar";
    public final static String GENERO_HOM="H";
    public final static String GENERO_LGBT="LGBT";
    public final static String GENERO_MUJ="M";
    public final static String STRONG ="<strong>", STRONG_BARRA = "</strong>";
    public final static int CP_MIN =28000;
    public final static int CP_MAX = 28055;

    public static String arreglaStrings(String string){
        final String LA_P="<p>",LOCURA="<!-- x-tinymce/html -->", LA_PBARRA ="</p>", LA_A ="<a>",
                LA_ABARRA="</a>", LA_B = "<b>", LA_BBARRA = "</b>", NBSP = "nbsp;", SUP = "&sup",
                LA_H2 ="<h2>", LA_H2B = "</h2>", SPAN = "<span>", SPANBARRA="</span>", NUM39 = "#39;",
                BR = "<br>", BRB = "</br>", EM = "<em>", EMB = "</em>", RSQUO = "&rsquo;",
                LSQUO = "&lsquo;", QUOT = "&quot;", BULL = "&bull;", H4 = "<h4>", H4B = "</h4>",
                BR_ES= "<br />", ACUTES = "acute;", HR = "<hr>", HRB = "</hr>", HRBE = "<hr />",
                AMP = "amp;", TILDE = "tilde;", LDQUO = "&ldquo", RDQUO = "rdquo;", H3 = "<h3>",
                H3B = "</h3>", HELIP = "&hellip;", UML = "uml;", CIRC = "circ;", LOCURA_2="<p class=\"heading-2\">",
                DASH = "dash;",SUP2 ="<sup>",LOCURA_3="<p class=\"heading-3\">", ORDM="ordm;",
                HOTEL_EMPERADOR ="<a href=\"https://www.esmadrid.com/alojamientos/hotel-emperador\">",
        CUPULA_RETIRO="<a href=\"https://www.esmadrid.com/restaurantes/florida-retiro\">",
        GINKGO ="<a href=\"https://www.esmadrid.com/alojamientos/vp-plaza-espana-design\">",
        CIBELES ="<a href=\"https://www.esmadrid.com/informacion-turistica/centrocentro-palacio-de-cibeles\">",
        PROBETA = "<a href=\"https://www.esmadrid.com/restaurantes/la-tita-rivera\">",
        SKY_BAR = "<a href=\"https://www.esmadrid.com/alojamientos/axel-hotel-madrid\">",
        GARRA ="<a href=\"http://www.esmadrid.com/alojamientos/barcelo.torre.madrid\" target=\"_self\">",
        BALA ="<a href=\"https://www.esmadrid.com/restaurantes/chicas-chicos-maniquis\">",
        SECRETO_VEL="<a href=\"https://www.esmadrid.com/restuarantes/la-clave\">",
        DRY_MART ="<a href=\"https://www.esmadrid.com/alojamientos/gran-melia-fenix\">",
        ATICO = "<a href=\"htttps://wwww.esmadrid.com/alojamientos/de-las-letras\">",
        PECERA ="<a href=\"https://wwww.esmadrid.com/informacion-turistica/circulo-de-bellas-artes\">",
        TERR_URBAN="<a href=\"https://wwww.esmadrid.com/alojamientos/hotel-urban\">";

        string = string.replaceAll(TERR_URBAN,"");
        string = string.replaceAll(SECRETO_VEL,"");
        string = string.replaceAll(DRY_MART,"");
        string = string.replaceAll(ATICO,"");
        string = string.replaceAll(PECERA,"");
        string = string.replaceAll(BALA,"");
        string = string.replaceAll(PROBETA,"");
        string = string.replaceAll(SKY_BAR,"");
        string = string.replaceAll(GARRA,"");
        string = string.replaceAll(GINKGO,"");
        string = string.replaceAll(CIBELES,"");
        string = string.replaceAll(CUPULA_RETIRO,"");
        string = string.replaceAll(HOTEL_EMPERADOR,"");
        string = string.replaceAll(LOCURA_3,"");

        if(string.contains(SUP2)){
            string = string.replaceAll(SUP2,"");
            string = string.replaceAll("</sup>","");
        }

        string = string.replaceAll(LOCURA_2,"");

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

        string = string.replaceAll(ORDM,"");
        string = string.replaceAll(HELIP,"");
        string = string.replaceAll(H3B,"");
        string = string.replaceAll(H3,"");
        string = string.replaceAll(RDQUO,"");

        if(string.contains(LDQUO)) {
            string = string.replaceAll(LDQUO, "");
        }

        if(string.contains(TILDE)){
            string = string.replaceAll(TILDE,"");
            string = string.replaceAll("&","");
        }
        if(string.contains(AMP)){
            string = string.replaceAll(AMP,"");
            string = string.replaceAll("&","");
        }
        string = string.replaceAll(HRBE,"");
        string = string.replaceAll(HRB,"");
        string = string.replaceAll(HR,"");
        if(string.contains(ACUTES)){
            string = string.replaceAll(ACUTES,"");
            string = string.replaceAll("&","");
        }

        string = string.replaceAll(BR_ES,"");
        string = string.replaceAll(H4B,"");
        string = string.replaceAll(H4,"");
        string = string.replaceAll(BULL,"");
        string = string.replaceAll(QUOT,"");
        string= string.replaceAll(LSQUO,"");
        string = string.replaceAll(RSQUO,"");
        string = string.replaceAll(LA_P,"");
        string = string.replaceAll(LOCURA,"");
        string = string.replaceAll(LA_PBARRA,"");
        string = string.replaceAll(STRONG,"");
        string = string.replaceAll(STRONG_BARRA,"");
        string = string.replaceAll(LA_A,"");
        string = string.replaceAll(LA_ABARRA,"");
        string = string.replaceAll(LA_B,"");
        string = string.replaceAll(LA_BBARRA,"");

        if(string.contains(NBSP)){
            string = string.replaceAll(NBSP,"");
            string = string.replaceAll("&","");
        }
        string = string.replaceAll(SUP,"");
        string = string.replaceAll(LA_H2,"");
        string =string.replaceAll(LA_H2B,"");
        if(string.contains(NUM39)){
            string = string.replaceAll(NUM39,"");
            string = string.replaceAll("&","");
        }

        string = string.replaceAll(SPAN,"");
        string = string.replaceAll(SPANBARRA,"");
        string = string.replaceAll(BR,"");
        string = string.replaceAll(BRB,"");
        string = string.replaceAll(EM,"");
        string = string.replaceAll(EMB,"");

        return string;
    }



}
