package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.adapters.ChatAdapter;
import com.len1.madtraveljournal.adapters.ClaseUsuarioAdapter;
import com.len1.madtraveljournal.modelos.Chat;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Matches;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private EditText mensaje;
    private Button enviar;
    private FirebaseFirestore db;
    private Matches match;
    private String coleccion;
    private RecyclerView listView;
    private ClaseUsuario usuarioOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        db = FirebaseFirestore.getInstance();


        listView = findViewById(R.id.listviewchat);
        mensaje = findViewById(R.id.edittext_chatbox);
        enviar = findViewById(R.id.button_chatbox_send);
        Intent intent = getIntent();
        usuarioOwner = (ClaseUsuario) intent.getSerializableExtra("usuario");
        match = (Matches) intent.getSerializableExtra("match");
        coleccion = intent.getStringExtra("coleccion");
        descargarMensajes();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirMensaje();
            }
        });

    }
    private void subirMensaje(){
        String mensString = mensaje.getText().toString();
        if(!TextUtils.isEmpty(mensString)){
            Map<String,Object> mapa = new HashMap<>();
            mapa.put("nombre",usuarioOwner.getNombreUsuario());
            mapa.put("foto", usuarioOwner.getUrlFoto());
            mapa.put("email",usuarioOwner.getEmail());
            mapa.put("mensaje",mensString);
            Date ahora = Calendar.getInstance().getTime();
            mapa.put("fecha",ahora.toString());
            db.collection(coleccion).document().set(mapa).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    descargarMensajes();
                    mensaje.setText("");
                }
            });
        }
    }
    private void descargarMensajes(){

        db.collection(coleccion).orderBy("fecha", Query.Direction.ASCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<Chat> listaChat = new ArrayList<>();

                    for(QueryDocumentSnapshot document : task.getResult()){
                        Chat mensajeChat = document.toObject(Chat.class);
                        listaChat.add(mensajeChat);
                    }

                    ChatAdapter adapter = new ChatAdapter(getApplicationContext(),listaChat,usuarioOwner);
                    listView.setAdapter(adapter);
                    listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    adapter.notifyDataSetChanged();

                }
            }
        });
    }
}
