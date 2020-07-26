package com.len1.madtraveljournal.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.ui.comentarios.FragmentInfo;
import com.len1.madtraveljournal.actividades.ui.comentarios.FragmentMapa;
import com.len1.madtraveljournal.actividades.ui.comentarios.FragmentPostearComentario;
import com.len1.madtraveljournal.actividades.ui.comentarios.FragmentVerFotos;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;

public class InfoLugar extends AppCompatActivity {
    private ClaseUsuario usuario;
    private LugarBar bar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infolugarlayout);
        Intent intent = getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        bar = (LugarBar) intent.getSerializableExtra("bar");
        FragmentInfo fragmentInfo = new FragmentInfo();
        fragmentInfo.newInstance(bar);
        ponerFragment(fragmentInfo);
        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottomNav);

        setTitle(bar.getNombre());
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                FragmentInfo fragmentInfo = new FragmentInfo();
                                fragmentInfo.newInstance(bar);
                                ponerFragment(fragmentInfo);
                                break;
                            case R.id.navigation_dashboard:
                                FragmentVerFotos fragment = new FragmentVerFotos();
                                fragment.newInstance(usuario,bar.getNombre());
                                ponerFragment(fragment);
                                break;
                            case R.id.navigation_notifications:
                                FragmentMapa fragmentMapa = new FragmentMapa();
                                fragmentMapa.newInstance(bar);
                                ponerFragment(fragmentMapa);
                                break;
                            case R.id.navigation_info:
                                FragmentPostearComentario fragmentPostearComentario = new FragmentPostearComentario();
                                fragmentPostearComentario.newInstance(usuario,bar);
                                ponerFragment(fragmentPostearComentario);

                        }
                        return false;
                    }
                });




    }

    public void ponerFragment(Fragment fragmentInterno){
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.frameAja, fragmentInterno).commit();
    }
}
