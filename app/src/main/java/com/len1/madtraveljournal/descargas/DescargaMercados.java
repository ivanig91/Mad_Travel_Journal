package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;



import com.len1.madtraveljournal.Constantes;


import com.len1.madtraveljournal.adapters.LugarAdapter;
import com.len1.madtraveljournal.lugares.LugarMercado;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DescargaMercados extends AsyncTask<String,Void,Void> {
    private boolean error = false;
    private ProgressDialog dialog;
    private ArrayList<LugarMercado> mercados;
    JSONArray jsonArray = null;
    private LugarAdapter adapter;




    public DescargaMercados(ArrayList<LugarMercado> mercados,LugarAdapter adapter) {
        this.mercados = mercados;
        this.adapter = adapter;
    }

    @Override
    protected Void doInBackground(String... strings) {
        Log.i("cadenas","llega hasta aca");
        CrearJSArray crea = new CrearJSArray(Constantes.urlMercados,"@graph");

        jsonArray = crea.creaJsonArray();
        String id;
        String nombre;
        String descripcion;
        String direccion;
        String latitud;
        String longitud;
        String servicios;
        String horario;
        LugarMercado mercado = null;
        int i=0;
        int cp=0;
        String cpString;
        boolean flip = false;
        while (i< jsonArray.length()){
            try {
                cpString= jsonArray.getJSONObject(i).getJSONObject("address").getString("postal-code");
                if(!cpString.equals("")){
                    cp= Integer.parseInt(cpString);
                    if(cp>=Constantes.CP_MIN && cp <= Constantes.CP_MAX){
                        flip = true;
                    }
                }


                if(jsonArray.getJSONObject(i).has("location") && flip && !lugarRepetido(jsonArray.getJSONObject(i).getString("title"))){

                    id = jsonArray.getJSONObject(i).getString("id");
                    nombre = jsonArray.getJSONObject(i).getString("title");
                    descripcion = jsonArray.getJSONObject(i).getJSONObject("organization").getString("organization-desc");
                    direccion = jsonArray.getJSONObject(i).getJSONObject("address").getString("street-address");
                    latitud =jsonArray.getJSONObject(i).getJSONObject("location").getString("latitude");
                    longitud =jsonArray.getJSONObject(i).getJSONObject("location").getString("longitude");
                    horario= jsonArray.getJSONObject(i).getJSONObject("organization").getString("schedule");
                    servicios =jsonArray.getJSONObject(i).getJSONObject("organization").getString("services");
                    mercado = new LugarMercado(id,nombre,descripcion,direccion,latitud,longitud,horario,servicios);

                    mercados.add(mercado);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;
            flip = false;

        }




        return null;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        mercados = new ArrayList<>();

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
        for(LugarMercado lugar : mercados){
            if(lugar.getNombre().equals(nombre)){
                flip = true;
            }
        }
        return flip;
    }

    public ArrayList<LugarMercado> getMercados() {
        return mercados;
    }

    public void setMercados(ArrayList<LugarMercado> mercados) {
        this.mercados = mercados;
    }
}
