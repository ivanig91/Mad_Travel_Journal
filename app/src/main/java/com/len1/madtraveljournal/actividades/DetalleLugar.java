package com.len1.madtraveljournal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public class DetalleLugar extends AppCompatActivity {

    ImageView fotoLugar;
    LugarBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        fotoLugar = findViewById(R.id.ivFoto3);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        // para tener el url de la foto se utiliza el getFotoUrl abajo

        DescargaFoto deFoto = new DescargaFoto();
        deFoto.execute(bar.getFotoUrl());
    }


    private class DescargaFoto extends AsyncTask{
        HttpURLConnection connection;
        @Override
        protected Object doInBackground(Object[] urls) {
            try {

                String src = (String) urls[0];
                URL url = new URL(src);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream input = connection.getInputStream();
                BufferedInputStream buff = new BufferedInputStream(input);
                Bitmap elBitMap = BitmapFactory.decodeStream(buff);
                if(elBitMap==null){
                    Log.i("bt","el bitmap es nulo");
                }
                return elBitMap;
            } catch (IOException e) {
                e.printStackTrace();

                return null;
            }finally {
                connection.disconnect();
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            fotoLugar.setImageBitmap((Bitmap) o);
        }
    }

}
