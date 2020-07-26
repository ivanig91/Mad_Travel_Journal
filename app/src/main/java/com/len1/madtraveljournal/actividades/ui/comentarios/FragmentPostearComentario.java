package com.len1.madtraveljournal.actividades.ui.comentarios;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.adapters.PostAdapter;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.Post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentPostearComentario extends Fragment {
    private ClaseUsuario usuario;
    private LugarBar bar;
    private FirebaseFirestore db;
    private ListView lv;
    private EditText comentario;
    private ImageButton ib;

    public FragmentPostearComentario newInstance(ClaseUsuario usuario, LugarBar bar){
        FragmentPostearComentario fragmentPostearComentario = new FragmentPostearComentario();
        this.usuario = usuario;
        this.bar = bar;
        return  fragmentPostearComentario;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_postear_comentario,container,false);

        ib = view.findViewById(R.id.sendButton);

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirComentario();
            }
        });

        lv = view.findViewById(R.id.lvPosts);
        comentario = view.findViewById(R.id.commentEditText);
        descargaComentarios();
        return  view;
    }
    private void subirComentario(){
        String com = comentario.getText().toString();
        if(TextUtils.isEmpty(com)){
            Toast.makeText(getContext(),"Escribe un comentario",Toast.LENGTH_LONG);
        }else{
            Map<String,Object> mapaComentario = new HashMap<>();
            mapaComentario.put("nombre",usuario.getNombreUsuario());
            mapaComentario.put("urlFoto",usuario.getUrlFoto());
            mapaComentario.put("comentario",com);
            mapaComentario.put("bar",bar.getNombre());
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
                        Log.i("miDebug",post.getComentario());
                        listaPost.add(post);
                    }
                    PostAdapter postAdapter = new PostAdapter(listaPost,getContext());
                    lv.setAdapter(postAdapter);
                    postAdapter.notifyDataSetChanged();

                }

            }
        });
    }
}
