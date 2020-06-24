package com.len1.madtraveljournal.actividades.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.ActualizarUsuario;
import com.len1.madtraveljournal.actividades.NuevoUsuario;
import com.len1.madtraveljournal.actividades.fragments.ui.main.SectionsPagerAdapter;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;

public class tabbed extends AppCompatActivity {
    private ClaseUsuario usuario;
    private AppBarConfiguration mAppBarConfiguration;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent in = getIntent();
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        setTitle(R.string.app_name);
        usuario = (ClaseUsuario) in.getSerializableExtra("usuario");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(),usuario);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menuPerfil){
            Intent intent = new Intent(this, ActualizarUsuario.class);
            intent.putExtra("origen", Constantes.ACT_MENU);
            intent.putExtra("usuario",usuario);
            startActivity(intent);
        }else if(item.getItemId() == R.id.menuCerrarSesion){
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}