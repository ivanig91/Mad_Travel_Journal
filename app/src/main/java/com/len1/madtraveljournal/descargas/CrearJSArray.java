package com.len1.madtraveljournal.descargas;

import android.util.Log;

import com.len1.madtraveljournal.Constantes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class CrearJSArray {

    private String resultado = null;
    private JSONObject json = null;
    private JSONArray jsonArray;
    private String urlWeb;
    private String principal;

    public CrearJSArray(String url, String principal) {
        this.urlWeb = url;
        this.principal = principal;
    }

    public JSONArray creaJsonArray() {

         jsonArray = new JSONArray();
        try {
            URL url = new URL(urlWeb);
            HttpsURLConnection conexion = (HttpsURLConnection) url.openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                sb.append(linea + "\n");
            }
            conexion.disconnect();
            br.close();
            resultado = sb.toString();
            json = new JSONObject(resultado);
            jsonArray = json.getJSONArray(principal);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }
}

