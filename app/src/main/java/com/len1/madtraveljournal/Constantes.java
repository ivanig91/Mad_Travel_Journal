package com.len1.madtraveljournal;

import android.graphics.Bitmap;

public class Constantes {
    public final static String urlMonumentos ="https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=300356-0-monumentos-ciudad-madrid&mgmtid=eb8e993ae322b610VgnVCM1000001d4a900aRCRD&preview=full";
    public final static String urlMercados = "https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=200967-0-mercados&mgmtid=b9f7530479243410VgnVCM1000000b205a0aRCRD&preview=full";
    public final static String urlCultura = "https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=206974-0-agenda-eventos-culturales-100&mgmtid=6c0b6d01df986410VgnVCM2000000c205a0aRCRD&preview=full";
    public final static String urlMuseos = "https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=201132-0-museos&mgmtid=118f2fdbecc63410VgnVCM1000000b205a0aRCRD&preview=full";
    public final static String urlPiscinas = "https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=210227-0-piscinas-publicas&mgmtid=3d481f900c6e9410VgnVCM1000000b205a0aRCRD&preview=full";
    public final static String urlTurismo = "https://datos.madrid.es/portal/site/egob/menuitem.ac61933d6ee3c31cae77ae7784f1a5a0/?vgnextoid=00149033f2201410VgnVCM100000171f5a0aRCRD&format=json&file=0&filename=201105-0-informacion-turismo&mgmtid=ee5e692e74c63410VgnVCM2000000c205a0aRCRD&preview=full";
    public final static String urlBares = "https://www.esmadrid.com/opendata/noche_v1_es.xml";
    public final static String NODO_MONUMENTOS = "@graph";
    public final static int CP_MIN =28000;
    public final static int CP_MAX = 28055;

    public static String arreglaStrings(String string){
        final String LA_P="<p>";
        final String LOCURA="<!-- x-tinymce/html -->";
        final String LA_PBARRA ="</p>";
        final String STRONG ="<strong>";
        final String STRONG_BARRA = "</strong>";
        final String LA_A ="<a>";
        final String LA_ABARRA="</a>";
        final String LA_B = "<b>";
        final String LA_BBARRA = "</b>";
        final String NBSP = "&nbsp;";
        final String SUP = "&sup";
        final String LA_H2 ="<h2>";
        final String LA_H2B = "</h2>";
        final String SPAN = "<span>";
        final String SPANBARRA="</span>";
        final String NUM39 = "&#39;";
        final String BR = "<br>";
        final String BRB = "</br>";
        final String EM = "<em>";
        final String EMB = "</em>";
        final String RSQUO = "&rsquo;";
        final String LSQUO = "&lsquo;";
        final String QUOT = "&quot;";
        final String BULL = "&bull;";
        final String H4 = "<h4>";
        final String H4B = "</h4>";
        final String BR_ES= "<br />";
        final String ACUTES = "acute;";
        final String HR = "<hr>";
        final String HRB = "</hr>";
        final String HRBE = "<hr />";
        final String AMP = "amp;";
        final String TILDE = "tilde;";
        final String LDQUO = "&ldquo";
        final String RDQUO = "rdquo;";
        final String H3 = "<h3>";
        final String H3B = "</h3>";
        final String HELIP = "&hellip;";
        final String UML = "uml;";
        final String CIRC = "circ;";
        final String DASH = "dash;";

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
