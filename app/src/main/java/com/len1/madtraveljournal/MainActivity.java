package com.len1.madtraveljournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.len1.madtraveljournal.descargas.DescargaCultura;
import com.len1.madtraveljournal.descargas.DescargaMercados;
import com.len1.madtraveljournal.descargas.DescargaMuseo;
import com.len1.madtraveljournal.descargas.DescargaPiscina;
import com.len1.madtraveljournal.descargas.DescargaTurismo;
import com.len1.madtraveljournal.descargas.descargaMonumento;
import com.len1.madtraveljournal.lugares.Lugar;
import com.len1.madtraveljournal.lugares.LugarCultura;
import com.len1.madtraveljournal.lugares.LugarMercado;
import com.len1.madtraveljournal.lugares.LugarMonumento;
import com.len1.madtraveljournal.lugares.LugarMuseo;
import com.len1.madtraveljournal.lugares.LugarPiscina;
import com.len1.madtraveljournal.lugares.LugarTurismo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button descargaTarea;
    private ArrayList<LugarMonumento> monumentos;
    private  ArrayList<LugarMercado> mercados;
    private  ArrayList<LugarCultura> actCulturas;
    private  ArrayList<LugarMuseo> museos;
    private  ArrayList<LugarPiscina> piscinas;
    private  ArrayList<LugarTurismo> puntos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monumentos = new ArrayList<>();
        mercados = new ArrayList<>();
        actCulturas = new ArrayList<>();
        museos = new ArrayList<>();
        piscinas = new ArrayList<>();
        puntos = new ArrayList<>();
        descargaTarea = findViewById(R.id.descargaTarea);
        descargaTarea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        DescargaPiscina desPiscina = new DescargaPiscina(piscinas);
        desPiscina.execute(Constantes.urlPiscinas);
        DescargaTurismo descargaTurismo = new DescargaTurismo(puntos);
        descargaTurismo.execute(Constantes.urlTurismo);
        //DescargaCultura descargaCultura = new DescargaCultura(actCulturas);
        //descargaCultura.execute(Constantes.urlCultura);
        /*DescargaMercados descargaMercados = new DescargaMercados(mercados);
        descargaMercados.execute(Constantes.urlMercados);*/
        //descargaMonumento desMonumento = new descargaMonumento(monumentos);
        //desMonumento.execute(Constantes.urlMonumentos);
        DescargaMuseo descargaMuseo = new DescargaMuseo(museos);
        descargaMuseo.execute(Constantes.urlMuseos);



    }
}
