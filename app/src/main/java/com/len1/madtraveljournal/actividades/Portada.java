package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.fragments.tabbed;

public class Portada extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;// esto lo voy a utilizar despues
    private FirebaseUser usuario;
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portada);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        if(usuario!=null){
            String emailUsuario = usuario.getEmail();
            Log.i("prueba3",emailUsuario);
            buscarUsuario(emailUsuario);
        }else{
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        }
    }
    private void buscarUsuario(String usuario){
        Log.i("prueba2",usuario);
        DocumentReference docRef = db.collection(Constantes.TABLA_USUARIOS).document(usuario);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document!=null){
                        ClaseUsuario usuario1 =document.toObject(ClaseUsuario.class);
                        Intent intent = new Intent(getApplicationContext(), tabbed.class);
                        intent.putExtra("usuario",usuario1);
                        startActivity(intent);
                    }

                }
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
