package com.len1.madtraveljournal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.len1.madtraveljournal.ClaseUsuario;
import com.len1.madtraveljournal.R;

public class PostearComentario extends AppCompatActivity {
    private ClaseUsuario usuario;
    private TextView tvNombreUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postear_comentario);
        Intent intent = getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario);
        tvNombreUsuario.setText(usuario.getNombreUduario());
    }
}
