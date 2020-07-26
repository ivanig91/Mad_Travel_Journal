package com.len1.madtraveljournal.actividades.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.ActualizarUsuario;
import com.len1.madtraveljournal.actividades.fragments.ui.main.SectionsPagerAdapter;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class tabbed extends AppCompatActivity  {
    private ClaseUsuario usuario;
    private AppBarConfiguration mAppBarConfiguration;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent in = getIntent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.app_name);
        usuario = (ClaseUsuario) in.getSerializableExtra("usuario");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(),usuario);
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        // Codigo del NavigationDrawer
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView laNav = findViewById(R.id.nav_view);
        View headerLayOut = laNav.getHeaderView(0);

        CircleImageView fotoPerfil = headerLayOut.findViewById(R.id.fotoPerfil);
        TextView nombrePerfil = headerLayOut.findViewById(R.id.nombrePerfil);
        nombrePerfil.setText(usuario.getNombreUsuario());
        TextView correoPerfil = headerLayOut.findViewById(R.id.correoPerfil);
        correoPerfil.setText(usuario.getEmail());
        Picasso.get().load(usuario.getUrlFoto()).into(fotoPerfil);
        laNav.bringToFront();
        if(laNav!=null){
           laNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
               @Override
               public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                   if(item.getItemId() == R.id.nav_home){
                       Intent nuevoIntent = new Intent(getApplicationContext(),ActualizarUsuario.class);
                       nuevoIntent.putExtra("usuario",usuario);
                       startActivity(nuevoIntent);
                   }else if( item.getItemId() == R.id.nav_gallery){
                       Toast.makeText(getApplicationContext(),"Configuración de sistema",Toast.LENGTH_LONG).show();
                   }else{
                       FirebaseAuth.getInstance().signOut();
                       finish();
                   }
                   return true;
               }
           });
        }



    }

}