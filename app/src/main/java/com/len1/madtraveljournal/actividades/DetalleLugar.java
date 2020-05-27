package com.len1.madtraveljournal.actividades;

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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.actividades.fragments.tabbed;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

import com.len1.madtraveljournal.modelos.Constantes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetalleLugar extends AppCompatActivity implements View.OnClickListener {

    ImageView fotoLugar;
    LugarBar bar;
    TextView tvDetalleCalle;
    FloatingActionButton fab;
    ClaseUsuario usuario;
    Switch swEstoyAca;
    Button btEntrarBar;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_lugar);
        fotoLugar = findViewById(R.id.ivFoto3);
        swEstoyAca = findViewById(R.id.swGuardarBar);

        tvDetalleCalle = findViewById(R.id.tvDetalleCalle);
        Intent intent = getIntent();
        bar = (LugarBar) intent.getSerializableExtra("bar");
        btEntrarBar = findViewById(R.id.btEntraBar);
        btEntrarBar.setOnClickListener(this);
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        db  = FirebaseFirestore.getInstance();

        this.setTitle(bar.getNombre());
        comprobarSwitch();
        metodoDelSwitch();
        tvDetalleCalle.setText(bar.getDireccion());
        fab = findViewById(R.id.fabPost);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(),PostearComentario.class);
                intent2.putExtra("bar",bar);
                intent2.putExtra("usuario",usuario);
                startActivity(intent2);
            }
        });
        Picasso.get().load(bar.getFotoUrl()).into(fotoLugar);



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumapa,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(getApplicationContext(),MapaActivity.class);
        intent.putExtra("bar",bar);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == btEntrarBar.getId()&& swEstoyAca.isChecked()){
            Intent intent = new Intent(getApplicationContext(), PersonasEnBar.class);
            intent.putExtra("usuario",usuario);
            intent.putExtra("bar",bar);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Tienes que estar en el bar",Toast.LENGTH_LONG);
        }
    }
}
