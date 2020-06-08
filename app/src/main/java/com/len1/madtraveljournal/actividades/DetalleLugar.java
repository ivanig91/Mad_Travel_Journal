package com.len1.madtraveljournal.actividades;

import android.app.PendingIntent;
import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.modelos.Chat;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.Matches;
import com.len1.madtraveljournal.modelos.NotificationUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DetalleLugar extends AppCompatActivity implements View.OnClickListener {

    ImageView fotoLugar;
    LugarBar bar;

    FloatingActionButton fab;
    ClaseUsuario usuario;
    Switch swEstoyAca;
    Button btEntrarBar, btComoLlegar,
            btComentarios,btVerFotos,btGuardarBar;
    FirebaseFirestore db;
    int origen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        db  = FirebaseFirestore.getInstance();
        fotoLugar = findViewById(R.id.ivFoto3);
        swEstoyAca = findViewById(R.id.swGuardarBar);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        origen = intent.getIntExtra("origen",0);
        btEntrarBar = findViewById(R.id.btEntraBar);
        btEntrarBar.setOnClickListener(this);
        btComentarios = findViewById(R.id.btComentarios);
        btComoLlegar = findViewById(R.id.btComoLlegar);
        btGuardarBar = findViewById(R.id.btGuardarBar);
        if(origen==1){
            btGuardarBar.setText("Quitar de favoritos");
        }
        btGuardarBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(origen==1){
                    borrarFav();
               }else{
                   guardarComoFav();
               }
            }
        });
        btVerFotos = findViewById(R.id.btVerFotos);
        btVerFotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),VerFotos.class);
                intent.putExtra("bar",bar.getNombre());
                intent.putExtra("usuario",usuario);
                startActivity(intent);
            }
        });
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");

        btComoLlegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MapaActivity.class);
                intent.putExtra("bar",bar);
                startActivity(intent);
            }
        });
        btComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),PostearComentario.class);
                intent2.putExtra("bar",bar);
                intent2.putExtra("usuario",usuario);
                startActivity(intent2);
            }
        });

        this.setTitle(bar.getNombre());
        metodoDelSwitch();
        Picasso.get().load(bar.getFotoUrl()).into(fotoLugar);
    }
    @Override
    protected void onStart() {
        super.onStart();
        comprobarSwitch();
    }

    private void comprobarSwitch(){
        DocumentReference docRef = db.collection(Constantes.TABLA_USUARIOS).document(usuario.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        usuario =document.toObject(ClaseUsuario.class);
                        if(usuario.getBarActual().equals(bar.getNombre())){
                            swEstoyAca.setChecked(true);
                            matchNotiON();

                        }else{
                            swEstoyAca.setChecked(false);
                        }
                    }
                }
            }
        });

    }

    private void metodoDelSwitch(){
        swEstoyAca.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if(isChecked){
                    usuario.setBarActual(bar.getNombre());
                }else{
                    usuario.setBarActual("");
                }
                db.collection(Constantes.TABLA_USUARIOS).document(usuario.getEmail()).set(usuario);
            }
        });
    }
    private void borrarFav(){
        db.collection(Constantes.TABLA_USUARIOS)
                .document(usuario.getEmail())
                .collection(Constantes.TABLA_FAVS).document(bar.getNombre()).delete();
        Toast.makeText(getApplicationContext(),"Eliminado de favoritos",Toast.LENGTH_LONG).show();
    }
    private void guardarComoFav(){
        Map<String,Object> mapa = new HashMap<>();
        mapa.put("nombre",bar.getNombre());
        mapa.put("descripcion",bar.getDescripcion());
        mapa.put("direccion",bar.getDireccion());
        mapa.put("latitud",bar.getLatitud());
        mapa.put("longitud",bar.getLongitud());
        mapa.put("categoria",bar.getCategoria());
        mapa.put("fotoUrl",bar.getFotoUrl());
        db.collection(Constantes.TABLA_USUARIOS)
                .document(usuario.getEmail())
                .collection(Constantes.TABLA_FAVS)
                .document(bar.getNombre())
                .set(mapa);
        String mensaje = "Agregaste "+bar.getNombre()+" a tus favoritos";
        Toast.makeText(getApplicationContext(),mensaje,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == btEntrarBar.getId()&& swEstoyAca.isChecked()){
            Intent intent = new Intent(getApplicationContext(), PersonasEnBar.class);
            intent.putExtra("usuario",usuario);
            intent.putExtra("bar",bar);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Tienes que estar en el bar",Toast.LENGTH_LONG).show();
        }
    }

    private void matchNotiON(){
       db.collection(Constantes.TABLA_MATCH).whereEqualTo("envia",usuario.getEmail())
                .whereEqualTo("bar",bar.getNombre()).limit(10).
                addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                for(DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()){

                    if(dc.getType() == DocumentChange.Type.MODIFIED){
                        Matches match = dc.getDocument().toObject(Matches.class);

                        NotificationUtils.createNotificationChannel(getApplicationContext(),"2");
                        Intent intent = new Intent (getApplicationContext(),ChatActivity.class);
                        intent.putExtra("match",match);
                        intent.putExtra("usuario",usuario);
                        intent.putExtra("coleccion",dc.getDocument().getId());

                        Random generator = new Random();

                        String mensaje = "Tienes un match con "+match.getNombreRecibe();
                        String tit = "Tienes un match en "+match.getBar();

                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                                generator.nextInt(),intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        NotificationUtils.showNotification(getApplicationContext(),tit,
                                mensaje,"3",pendingIntent);
                    }


               }
                //Envio la lista a un metodo para que actualice las colecciones que tengo match
            }
        });
    }


}
