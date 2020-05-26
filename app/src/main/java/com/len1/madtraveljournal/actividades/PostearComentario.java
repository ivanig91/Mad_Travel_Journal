package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.adapters.PostAdapter;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PostearComentario extends AppCompatActivity {
    private ClaseUsuario usuario;
    private LugarBar bar;
    private FirebaseFirestore db;
    private ArrayList<LugarBar> listaBares;
    private ListView lv;
    private EditText comentario;
    private ImageButton ib;

    public PostearComentario() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postear_comentario);
        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();
        ib = findViewById(R.id.sendButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirComentario();
            }
        });
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        bar = (LugarBar) intent.getSerializableExtra("bar");
        listaBares = usuario.getBaresFavoritos();
        lv = findViewById(R.id.lvPosts);
        comentario = findViewById(R.id.commentEditText);
        descargaComentarios();
    }

    private void subirComentario(){
        String com = comentario.getText().toString();
        if(TextUtils.isEmpty(com)){
            Toast.makeText(this,"Escribe un comentario",Toast.LENGTH_LONG);
        }else{
            Map<String,Object> mapaComentario = new HashMap<>();
            mapaComentario.put("nombre",usuario.getNombreUsuario());
            mapaComentario.put("urlFoto",usuario.getUrlFoto());
            mapaComentario.put("comentario",com);
            mapaComentario.put("bar",bar.getNombre());
           // FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection(Constantes.TABLA_COMENTARIOS).document().set(mapaComentario).
                    addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            descargaComentarios();
                            comentario.setText("");
                        }
                    });

        }

    }

    private void descargaComentarios(){

        db.collection(Constantes.TABLA_COMENTARIOS).whereEqualTo("bar",bar.getNombre()).
                get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Post> listaPost = new ArrayList<>();
                    for(QueryDocumentSnapshot document: task.getResult()){
                        Post post = document.toObject(Post.class);
                        listaPost.add(post);
                    }
                    PostAdapter postAdapter = new PostAdapter(listaPost,getApplicationContext());
                    lv.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();

                }

            }
        });
    }


}
