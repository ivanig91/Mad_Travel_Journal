package com.len1.madtraveljournal.actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.adapters.PostAdapter;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.Post;

import java.util.ArrayList;

public class PostearComentario extends AppCompatActivity {
    private ClaseUsuario usuario;
    private TextView tvNombreUsuario;
    private LugarBar bar;
    private Switch swBares;
    private FirebaseFirestore db;
    private ArrayList<LugarBar> listaBares;
    private ArrayList<ClaseUsuario> listaUsuarios;
    private ArrayList<Post> listaPost;
    private PostAdapter postAdapter;
    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postear_comentario);
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        bar = (LugarBar) intent.getSerializableExtra("bar");
        listaBares = usuario.getBaresFavoritos();
        lv = findViewById(R.id.lvPosts);
        listaPost = new ArrayList<>();
        for(int i=0; i<10;i++){
            ClaseUsuario u = new ClaseUsuario(String.valueOf(i),String.valueOf(i));
            u.setNombreUsuario(String.valueOf(i));
            String comentario = String.valueOf(i);
            Post p = new Post(u,comentario,bar.getNombre());
            listaPost.add(p);
        }

        postAdapter = new PostAdapter(listaPost,this);
        lv.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();


    }


}
