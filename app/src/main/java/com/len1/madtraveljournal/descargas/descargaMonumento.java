package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.lugares.LugarMonumento;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class descargaMonumento extends AsyncTask<String,Void,Void> {

    private boolean error = false;  // tengo que terminar de hacer los metodos override
    private ProgressDialog dialog;  // tengo que terminar de hacer los metodos override
    private ArrayList<LugarMonumento> monumentos;
    private JSONArray jsonArray;

    public descargaMonumento(ArrayList<LugarMonumento> monumentos) {
        this.monumentos = monumentos;
    }

    @Override
    protected Void doInBackground(String... strings) {

        CrearJSArray cone = new CrearJSArray(Constantes.urlMonumentos,Constantes.NODO_MONUMENTOS);

        jsonArray = cone.creaJsonArray();

        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        LugarMonumento objeto = null;

        int i=0;
        int cp;
        String cpString;
        boolean flip = false;

        while(i<jsonArray.length()){
            try {
                cpString= jsonArray.getJSONObject(i).getJSONObject("address").getString("postal-code");
                if(!cpString.equals("")){
                    cp = Integer.parseInt(cpString);
                    if(cp>=Constantes.CP_MIN && cp <= Constantes.CP_MAX){
                        flip = true;
                    }

                    if(jsonArray.getJSONObject(i).has("location") && flip){

                        id = jsonArray.getJSONObject(i).getString("id");
                        nombre = jsonArray.getJSONObject(i).getString("title");
                        descripcion = jsonArray.getJSONObject(i).getJSONObject("organization").getString("organization-desc");
                        direccion = jsonArray.getJSONObject(i).getJSONObject("address").getString("street-address");
                        latitud =jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                        longitud =jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");
                        objeto = new LugarMonumento(id,nombre,descripcion,direccion,latitud,longitud);

                        monumentos.add(objeto);
                    }
                }
            } catch (JSONException ex) {
                ex.printStackTrace();
            }


            i++;
            flip=false;

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

    public ArrayList<LugarMonumento> getMonumentos() {
        return monumentos;
    }

    public void setMonumentos(ArrayList<LugarMonumento> monumentos) {
        this.monumentos = monumentos;
    }
}
