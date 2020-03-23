package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.lugares.LugarTurismo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaTurismo extends AsyncTask<String,Void,Void> {
    private boolean error = false;
    private ProgressDialog dialog;
    private ArrayList<LugarTurismo> puntosTuristicos;
    JSONArray jsonArray = null;

    public DescargaTurismo(ArrayList<LugarTurismo> puntosTuristicos) {
        this.puntosTuristicos = puntosTuristicos;
    }

    @Override
    protected Void doInBackground(String... strings) {
        CrearJSArray crea = new CrearJSArray(Constantes.urlTurismo,Constantes.NODO_MONUMENTOS);
        jsonArray = crea.creaJsonArray();

        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        String servicios;
        LugarTurismo puntoTurimo = null;
        int i=0;
        int cp=0;
        String cpString;
        boolean flip = false;
        while(i<jsonArray.length()){
            try {
                cpString = jsonArray.getJSONObject(i).getJSONObject("address").getString("postal-code");
                if(!cpString.equals("")){
                    cp = Integer.parseInt(cpString);
                    if(cp<=Constantes.CP_MAX && cp>=Constantes.CP_MIN){
                        flip=true;
                    }

                    if(flip){
                        id = jsonArray.getJSONObject(i).getString("id");
                        nombre = jsonArray.getJSONObject(i).getString("title");
                        descripcion = jsonArray.getJSONObject(i).getJSONObject("organization").getString("organization-desc");
                        direccion = jsonArray.getJSONObject(i).getJSONObject("address").getString("street-address");
                        latitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                        longitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");
                        servicios = jsonArray.getJSONObject(i).getJSONObject("organization").getString("services");
                        puntoTurimo = new LugarTurismo(id,nombre,descripcion,direccion,latitud,longitud,servicios);
                        puntosTuristicos.add(puntoTurimo);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
            Log.i("turismo",puntoTurimo.getNombre());
            flip= false;
        }
        return null;
    }

}
