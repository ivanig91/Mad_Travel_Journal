package com.len1.madtraveljournal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.len1.madtraveljournal.descargas.DescargaCultura;
import com.len1.madtraveljournal.descargas.DescargaMercados;
import com.len1.madtraveljournal.descargas.DescargaMuseo;
import com.len1.madtraveljournal.descargas.descargaMonumento;
import com.len1.madtraveljournal.lugares.Lugar;
import com.len1.madtraveljournal.lugares.LugarCultura;
import com.len1.madtraveljournal.lugares.LugarMercado;
import com.len1.madtraveljournal.lugares.LugarMonumento;
import com.len1.madtraveljournal.lugares.LugarMuseo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button descargaTarea;
    private ArrayList<LugarMonumento> monumentos;
    private  ArrayList<LugarMercado> mercados;
    private  ArrayList<LugarCultura> actCulturas;
    private  ArrayList<LugarMuseo> museos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monumentos = new ArrayList<>();
        mercados = new ArrayList<>();
        actCulturas = new ArrayList<>();
        museos = new ArrayList<>();
        descargaTarea = findViewById(R.id.descargaTarea);
        descargaTarea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        descargaMonumento descarga = new descargaMonumento(monumentos);
        descarga.execute(Constantes.urlMonumentos);

        DescargaMercados descarga1 = new DescargaMercados(mercados);
        descarga1.execute(Constantes.urlMercados);

        DescargaCultura cultura = new DescargaCultura(actCulturas);
        cultura.execute(Constantes.urlCultura);

        DescargaMuseo desMuseo = new DescargaMuseo(museos);
        desMuseo.execute(Constantes.urlMuseos);


    }
}
