package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;


import com.len1.madtraveljournal.Constantes;

import com.len1.madtraveljournal.lugares.LugarPiscina;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaPiscina  extends AsyncTask<String,Void,Void> {
    private boolean error = false;  // tengo que terminar de hacer los metodos override
    private ProgressDialog dialog;  // tengo que terminar de hacer los metodos override
    private ArrayList<LugarPiscina> piscinas;
    private JSONArray jsonArray;

    public DescargaPiscina(ArrayList<LugarPiscina> piscinas) {
        this.piscinas = piscinas;
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
                    if(flip){
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
            Log.i("piscina",piscina.getNombre());
        }

        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    public ArrayList<LugarPiscina> getPiscinas() {
        return piscinas;
    }

    public void setPiscinas(ArrayList<LugarPiscina> piscinas) {
        this.piscinas = piscinas;
    }
}
