package com.len1.madtraveljournal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;


import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

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
    TextView tvNombreBar;
    TextView tvDescripcionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        fotoLugar = findViewById(R.id.ivFoto3);
        tvNombreBar = findViewById(R.id.tvNombreBar);
        tvDescripcionBar = findViewById(R.id.tvDescripcionBar);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        // para tener el url de la foto se utiliza el getFotoUrl
        //El URL de la foto tiene el siguiente formato: http://www.esmadrid.com/sites/default/files/recursosturisticos/noche/blackhaus_4.jpg
        tvNombreBar.setText(bar.getNombre());
        tvDescripcionBar.setText(bar.getDescripcion());
        initImageLoader(this.getApplicationContext());
        ImageLoader imageLoader = ImageLoader.getInstance();
        Uri myURi = Uri.parse(bar.getFotoUrl());
        imageLoader.displayImage(String.valueOf(myURi),fotoLugar);

    }



    public static void initImageLoader(Context context) {

        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by the
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        //
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs(); // Remove for release app

        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config.build());
    }

    //Clase que gestiona la descarga de la foto
    private class DescargaFoto extends AsyncTask{
    
        @Override
        protected Object doInBackground(Object[] urls) {
            Bitmap elBitMap = null;
            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                String src = (String) urls[0];

                InputStream input = new java.net.URL(src).openStream();
                if(input==null){
                    Log.i("inputNULL","el input es nulo");
                }
                elBitMap = BitmapFactory.decodeStream(input,null,options);
                if(elBitMap==null){
                    Log.i("bt","el bitmap es nulo");
                }
                return elBitMap;
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
               
            }
            return elBitMap;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            fotoLugar.setImageBitmap((Bitmap) o);
        }
    }

}
