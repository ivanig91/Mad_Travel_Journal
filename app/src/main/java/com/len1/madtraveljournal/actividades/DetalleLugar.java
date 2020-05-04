package com.len1.madtraveljournal.actividades;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.descargas.DescargaFoto;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class DetalleLugar extends AppCompatActivity {

    ImageView fotoLugar;
    LugarBar bar;
    TextView tvDescripcionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        fotoLugar = findViewById(R.id.ivFoto3);

        tvDescripcionBar = findViewById(R.id.tvDescripcionBar);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        this.setTitle(bar.getNombre());
        tvDescripcionBar.setText(bar.getDescripcion());
        ImageLoader imageLoader = ImageLoader.getInstance();
        Uri myURi = Uri.parse(bar.getFotoUrl());
        imageLoader.displayImage(String.valueOf(myURi),fotoLugar);


    }

}
