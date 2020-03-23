package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.lugares.LugarCultura;


import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaCultura extends AsyncTask<String,Void,Void> {

    private boolean error = false;  // tengo que terminar de hacer los metodos override
    private ProgressDialog dialog;  // tengo que terminar de hacer los metodos override
    private ArrayList<LugarCultura> actividadesCultura;
    private JSONArray jsonArray;

    public DescargaCultura(ArrayList<LugarCultura> actividadesCultura) {
        this.actividadesCultura = actividadesCultura;
    }


    @Override
    protected Void doInBackground(String... strings) {
        CrearJSArray crea = new CrearJSArray(Constantes.urlCultura,Constantes.NODO_MONUMENTOS);

        jsonArray = crea.creaJsonArray();

        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        String precio;
        String horaComienzo;
        String horaFin;
        String eventLocation;
        LugarCultura actCultura =null;

        int i=0;
        int cp=0;
        String cpString;
        boolean flip = false;

        while(i< jsonArray.length()){
            try {
                cpString = jsonArray.getJSONObject(i).getJSONObject("address").getJSONObject("area").getString("postal-code");
                if(!cpString.equals("")) {
                    cp = Integer.parseInt(cpString);
                    if (cp >= Constantes.CP_MIN && cp <= Constantes.CP_MAX) {
                        flip = true;
                    }
                    if(jsonArray.getJSONObject(i).has("location") && flip && jsonArray.getJSONObject(i).has("address")){
                        id = jsonArray.getJSONObject(i).getString("id");
                        nombre = jsonArray.getJSONObject(i).getString("title");
                        descripcion = jsonArray.getJSONObject(i).getString("description");
                        direccion = jsonArray.getJSONObject(i).getJSONObject("address").getJSONObject("area").getString("street-address");
                        latitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                        longitud = jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");
                        precio = jsonArray.getJSONObject(i).getString("free");
                        horaComienzo = jsonArray.getJSONObject(i).getString("dtstart");
                        horaFin = jsonArray.getJSONObject(i).getString("dtend");
                        eventLocation = jsonArray.getJSONObject(i).getString("event-location");

                        actCultura = new LugarCultura(id,nombre,descripcion,direccion,latitud,longitud,precio,horaComienzo,horaFin,eventLocation);
                        actividadesCultura.add(actCultura);
                    }

                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
            flip=false;
            Log.i("cultura",actCultura.getNombre());
        }

        return null;
    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
