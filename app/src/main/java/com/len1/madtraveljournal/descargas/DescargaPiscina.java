package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


import com.len1.madtraveljournal.Constantes;

import com.len1.madtraveljournal.adapters.PiscinaAdapter;
import com.len1.madtraveljournal.lugares.LugarPiscina;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaPiscina  extends AsyncTask<String,Void,Void> {
    private boolean error = false;  // tengo que terminar de hacer los metodos override
    private ProgressDialog dialog;  // tengo que terminar de hacer los metodos override
    private ArrayList<LugarPiscina> piscinas;
    private JSONArray jsonArray;
    private PiscinaAdapter adapter;

    public DescargaPiscina(ArrayList<LugarPiscina> piscinas, PiscinaAdapter adapter) {
        this.piscinas = piscinas;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... strings) {
        CrearJSArray crea = new CrearJSArray(Constantes.urlPiscinas,Constantes.NODO_MONUMENTOS);
        jsonArray = crea.creaJsonArray();

        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        LugarPiscina piscina = null;

        int i=0;
        int cp=0;
        String cpString;
        boolean flip = false;

        while(i<jsonArray.length()){
            try {
                cpString = jsonArray.getJSONObject(i).getJSONObject("address").getString("postal-code");
                if(!cpString.equals("")){
                    cp = Integer.parseInt(cpString);
                    if(cp>= Constantes.CP_MIN && cp<= Constantes.CP_MAX){
                        flip = true;
                    }
                    if(flip && !lugarRepetido(jsonArray.getJSONObject(i).getString("title"))){
                        id = jsonArray.getJSONObject(i).getString("id");
                        nombre = jsonArray.getJSONObject(i).getString("title");
                        descripcion = jsonArray.getJSONObject(i).getJSONObject("organization").getString("organization-desc");
                        direccion = jsonArray.getJSONObject(i).getJSONObject("address").getString("street-address");
                        latitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                        longitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");

                        piscina = new LugarPiscina(id,nombre,descripcion,direccion,latitud,longitud);

                        piscinas.add(piscina);
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
            flip=false;

        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        piscinas = new ArrayList<>();

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
        for(LugarPiscina lugar : piscinas){
            if(lugar.getNombre().equals(nombre)){
                flip = true;
            }
        }
        return flip;
    }
    public ArrayList<LugarPiscina> getPiscinas() {
        return piscinas;
    }

    public void setPiscinas(ArrayList<LugarPiscina> piscinas) {
        this.piscinas = piscinas;
    }
}
