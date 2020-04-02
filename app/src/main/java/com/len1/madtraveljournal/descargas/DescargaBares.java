package com.len1.madtraveljournal.descargas;

import android.os.AsyncTask;
import android.util.Log;

import com.len1.madtraveljournal.Constantes;
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
    private ArrayList<LugarBar> listaBares;

    public DescargaBares(ArrayList<LugarBar> listaBares) {
        this.listaBares = listaBares;
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
            LugarBar bar = null;
            String probando;


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
                    //categoria = element.getElementsByTagName("item").item(3).getTextContent();
                    bar = new LugarBar(id,nombre,descripcion,direccion,latitud,longitud,fotoUrl,categoria);
                    Log.i("desc",categoria);
                    listaBares.add(bar);

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

    @Override
    protected void onCancelled() {
        super.onCancelled();
        listaBares = new ArrayList<>();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        //adapter.notifydata
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //adapter.notify;
    }
}
