package com.len1.madtraveljournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.fragments.tabbed;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email;
    private EditText password;
    private Button iniciarSesion;
    private Button btRegist;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;// esto lo voy a utilizar despues
    private FirebaseUser usuario;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        iniciarSesion = findViewById(R.id.btIniciar);
        iniciarSesion.setOnClickListener(this);
        btRegist = findViewById(R.id.btRegistrar);
        btRegist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== btRegist.getId()){
            nuevoUsuario(v);
        }else{
            iniciarSes(v);
        }
    }

    private void nuevoUsuario(View v){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(emailString) || TextUtils.isEmpty(passwordString)){
            Snackbar.make(v, "Debe rellenar email y contraseña", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
           mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(
                   new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       usuario =mAuth.getCurrentUser();
                       updateUI(usuario);
                   }else{
                       Log.i("exc", String.valueOf(task.getException()));
                   }
               }
           });
        }
    }

    private void iniciarSes(View v){
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if(TextUtils.isEmpty(emailString) || TextUtils.isEmpty(passwordString)){
            Snackbar.make(v, "Debe rellenar email y contraseña", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }else{
            mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                }
            });
        }

    }
    private void updateUI(FirebaseUser user){

        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();
        Map<String,Object> usuario = new HashMap<>();
        usuario.put("email",emailString);
        usuario.put("password",passwordString);

        final ClaseUsuario usuarioOB = new ClaseUsuario(emailString,passwordString);
        if(user!=null){

            db.collection(Constantes.TABLA_USUARIOS).document().set(usuario)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getApplicationContext(),"Exito",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),tabbed.class);
                    intent.putExtra("usuario",usuarioOB);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.i("metodoUP",e.toString());
                }
            });
        }
    }
}
