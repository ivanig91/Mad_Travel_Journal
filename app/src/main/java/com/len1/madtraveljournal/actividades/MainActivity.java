package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
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
import com.len1.madtraveljournal.ClaseUsuario;
import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.fragments.tabbed;

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
        String emailString = email.getText().toString();
        String passwordString = password.getText().toString();

        if(v.getId()== btRegist.getId()){
            Intent intent = new Intent(getApplicationContext(),NuevoUsuario.class);
            startActivity(intent);
        }else{
            if(TextUtils.isEmpty(emailString)|| TextUtils.isEmpty(passwordString) ){
                Snackbar.make(v, Constantes.MENSAJE_DATOS_VACIOS, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //nuevoUsuario(emailString,passwordString,v);
            }else{
                iniciarSes(emailString,passwordString,v);
            }
        }

    }


    private void iniciarSes(String emailString,String passwordString,View v){
        final ClaseUsuario usuarioOB = new ClaseUsuario(emailString,passwordString);
            mAuth.signInWithEmailAndPassword(emailString,passwordString).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent = new Intent(getApplicationContext(),tabbed.class);
                    intent.putExtra("usuario",usuarioOB);
                    startActivity(intent);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
            });

    }

}
