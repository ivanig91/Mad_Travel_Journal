package com.len1.madtraveljournal.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

import com.squareup.picasso.Picasso;

public class DetalleLugar extends AppCompatActivity {

    ImageView fotoLugar;
    LugarBar bar;
    TextView tvDescripcionBar;
    FloatingActionButton fab;
    ClaseUsuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        fotoLugar = findViewById(R.id.ivFoto3);

        tvDescripcionBar = findViewById(R.id.tvDescripcionBar);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        this.setTitle(bar.getNombre());
        tvDescripcionBar.setText(bar.getDescripcion());
        fab = findViewById(R.id.fabPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),PostearComentario.class);
                intent2.putExtra("bar",bar);
                intent2.putExtra("usuario",usuario);
                startActivity(intent2);
            }
        });
        Picasso.get().load(bar.getFotoUrl()).into(fotoLugar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumapa,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_mapa){
            Intent intent = new Intent(this,MapaActivity.class);
            intent.putExtra("bar",bar);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
