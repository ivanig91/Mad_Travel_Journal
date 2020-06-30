package com.len1.madtraveljournal.descargas;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.lugares.Lugar;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.ListasYAdapters;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DescargaBares extends AsyncTask<String,Void,Void> {

    private ListasYAdapters listasYAdapters;
    private Context context;
    private ProgressDialog dialog;
    LugarBar bar = null;
    public DescargaBares(ListasYAdapters listasYAdapters,Context context) {
        this.context = context;
        this.listasYAdapters = listasYAdapters;

    }

    @Override
    protected Void doInBackground(String... strings) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();

            URL url = new URL(Constantes.urlBares);
            InputStream stream = url.openStream();
            Document doc = docBuilder.parse(stream);

            Element root = doc.getDocumentElement();
            root.normalize();

            NodeList listaNodos = doc.getElementsByTagName("service");

            String id;
            String nombre;
            String descripcion;
            String direccion;
            String latitud;
            String longitud;
            String fotoUrl;
            String categoria ="";


            for(int i =0; i<listaNodos.getLength();i++){
                Node nodo = listaNodos.item(i);
                if(nodo.getNodeType() ==Node.ELEMENT_NODE){
                    Element element = (Element) nodo;
                    id = element.getAttribute("id");
                    nombre = element.getElementsByTagName("name").item(0).getTextContent();
                    descripcion = element.getElementsByTagName("body").item(0).getTextContent();
                    direccion = element.getElementsByTagName("address").item(0).getTextContent();
                    latitud = element.getElementsByTagName("latitude").item(0).getTextContent();
                    longitud = element.getElementsByTagName("longitude").item(0).getTextContent();
                    fotoUrl = element.getElementsByTagName("url").item(0).getTextContent();
                    NodeList listaItems = element.getElementsByTagName("item");
                    for(int j=0;j<listaItems.getLength();j++){
                        Node nodoEnSi = listaItems.item(j);
                        Element elementoItem = (Element) nodoEnSi;
                        if(elementoItem.getAttribute("name").equals("Categoria")){
                            categoria = elementoItem.getTextContent();
                        }
                    }
                    nombre = Constantes.arreglaStrings(nombre);
                    descripcion = Constantes.arreglaStrings(descripcion);
                    bar = new LugarBar(id,nombre,descripcion,direccion,latitud,longitud,fotoUrl,
                            categoria);
                    calsificarCategoria(bar);
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return null;
    }
    private void calsificarCategoria(LugarBar bar){
        String categoria = bar.getCategoria();
        if(categoria.equals(Constantes.CAT_BAR_DE_COPAS) ||
                categoria.equals(Constantes.CAT_BARES)||
                categoria.equals(Constantes.CAT_TERRAZAS) ||
                categoria.equals(Constantes.CAT_COCTELES)){
            bar.setIcono(R.drawable.copcan);
            listasYAdapters.getCocteleriasTerrazasYBares().add(bar);
        }else if(categoria.equals(Constantes.CAT_FLAMENCO)){
            listasYAdapters.getFlamenco().add(bar);
        }else if (categoria.equals(Constantes.CAT_MUSICA_DIRECTO)
        ){
            bar.setIcono(R.drawable.mdriect);
            listasYAdapters.getMusicaDirecto().add(bar);
        }else if (categoria.equals(Constantes.CAT_KARAOKE)){
            listasYAdapters.getKaraoke().add(bar);
        }else if(categoria.equals(Constantes.CAT_DISCOTECA)){
            bar.setIcono(R.drawable.disco);
            listasYAdapters.getDiscoteca().add(bar);
        }else{
            listasYAdapters.getOtros().add(bar);
        }
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(context);
        dialog.setTitle(R.string.mensaje_cargando);
        dialog.show();
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
        listasYAdapters.notificarAdapters();
        if(dialog!=null){
            dialog.dismiss();
        }

    }

}
