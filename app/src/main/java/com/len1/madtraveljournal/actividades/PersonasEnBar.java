package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.adapters.ClaseUsuarioAdapter;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;

import java.util.ArrayList;

public class PersonasEnBar extends AppCompatActivity {
    private ListView listView;
    private ClaseUsuarioAdapter adapter;
    private LugarBar bar;
    private ClaseUsuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personas_en_bar);
        listView = findViewById(R.id.lvClaseUsuario);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        descargaUsuarios();

    }

    private void descargaUsuarios(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection(Constantes.TABLA_USUARIOS).whereEqualTo("barActual",bar.getNombre()).
                whereEqualTo("genero",buscaGenero(usuario)).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                final ArrayList<ClaseUsuario> listaUsuarios = new ArrayList<>();
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document :task.getResult()){

                         ClaseUsuario  persona = document.toObject(ClaseUsuario.class);
                         listaUsuarios.add(persona);
                    }
                    adapter = new ClaseUsuarioAdapter(PersonasEnBar.this,listaUsuarios,usuario);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private String buscaGenero(ClaseUsuario usuario){
        String genero;

        if(usuario.getGenero().equals(Constantes.GENERO_HOM)){
            genero = Constantes.GENERO_MUJ;
        }else if(usuario.getGenero().equals(Constantes.GENERO_MUJ)){
            genero = Constantes.GENERO_HOM;
        }else{
            genero = Constantes.GENERO_LGBT;
        }

        return genero;
    }

}
