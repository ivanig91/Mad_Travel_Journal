package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.adapters.TurismoAdapter;
import com.len1.madtraveljournal.lugares.LugarTurismo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaTurismo extends AsyncTask<String,Void,Void> {
    private boolean error = false;
    private ProgressDialog dialog;
    private ArrayList<LugarTurismo> puntosTuristicos;
    JSONArray jsonArray = null;
    TurismoAdapter adapter;

    public DescargaTurismo(ArrayList<LugarTurismo> puntosTuristicos, TurismoAdapter adapter) {
        this.puntosTuristicos = puntosTuristicos;
        this.adapter = adapter;
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

                    if(flip && !lugarRepetido(jsonArray.getJSONObject(i).getString("title"))){
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

            flip= false;
        }
        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        puntosTuristicos = new ArrayList<>();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        adapter.notifyDataSetChanged();
    }
    private boolean lugarRepetido(String nombre){
        boolean flip = false;
        for(LugarTurismo lugar : puntosTuristicos){
            if(lugar.getNombre().equals(nombre)){
                flip = true;
            }
        }
        return flip;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

    }
}
