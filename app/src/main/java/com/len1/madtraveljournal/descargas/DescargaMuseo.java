package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.adapters.MuseoAdapter;
import com.len1.madtraveljournal.lugares.LugarMercado;
import com.len1.madtraveljournal.lugares.LugarMuseo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaMuseo extends AsyncTask<String,Void,Void> {
    private ArrayList<LugarMuseo> museos;
    private boolean error = false;  // tengo que terminar de hacer los metodos override
    private ProgressDialog dialog;  // tengo que terminar de hacer los metodos override
    private JSONArray jsonArray;
    private MuseoAdapter adapter;

    public DescargaMuseo(ArrayList<LugarMuseo> museos, MuseoAdapter adapter) {
        this.museos = museos;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... strings) {
        CrearJSArray crea = new CrearJSArray(Constantes.urlMuseos,Constantes.NODO_MONUMENTOS);
        jsonArray = crea.creaJsonArray();

        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        String horario;
        LugarMuseo museo = null;

        int i=0;
        int cp;
        String cpString;
        boolean flip = false;
        while(i<jsonArray.length()){
            try {
                cpString= jsonArray.getJSONObject(i).getJSONObject("address").getString("postal-code");
                if(!cpString.equals("")){
                    cp = Integer.parseInt(cpString);
                    if(cp>=Constantes.CP_MIN && cp<=Constantes.CP_MAX){
                        flip =  true;
                    }
                }
                if (jsonArray.getJSONObject(i).has("location") && flip && jsonArray.getJSONObject(i).has("address") && !lugarRepetido(jsonArray.getJSONObject(i).getString("title"))){
                    id = jsonArray.getJSONObject(i).getString("id");
                    nombre = jsonArray.getJSONObject(i).getString("title");
                    descripcion = jsonArray.getJSONObject(i).getJSONObject("organization").getString("organization-desc");
                    direccion = jsonArray.getJSONObject(i).getJSONObject("address").getString("street-address");
                    latitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                    longitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");
                    horario = jsonArray.getJSONObject(i).getJSONObject("organization").getString("schedule");
                    museo = new LugarMuseo(id,nombre,descripcion,direccion,latitud,longitud,horario);
                    museos.add(museo);
                }

                i++;
                flip=false;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        museos = new ArrayList<>();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        adapter.notifyDataSetChanged();
    }
    private boolean lugarRepetido(String nombre){
        boolean flip = false;
        for(LugarMuseo lugar : museos){
            if(lugar.getNombre().equals(nombre)){
                flip = true;
            }
        }
        return flip;
    }
}
