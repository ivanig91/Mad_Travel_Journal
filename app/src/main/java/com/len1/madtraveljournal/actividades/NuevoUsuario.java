package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class NuevoUsuario extends AppCompatActivity implements View.OnClickListener {
    /*
    PONER EL SCROLLVIEW EN EL LAYOUT
     */

    private EditText nombre;
    private EditText eMail;
    private EditText pass;
    private EditText pass2;
    private Button nuevo;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private RadioButton rbFem;
    private RadioButton rbHom;
    private RadioButton rbLGBT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nombre = findViewById(R.id.etNuevoNombre);
        eMail = findViewById(R.id.etNuevoMail);
        pass = findViewById(R.id.etNuevaPass);
        pass2 = findViewById(R.id.etNuevaPass2);
        rbFem = findViewById(R.id.rbFEM);
        rbHom = findViewById(R.id.rbHOM);
        rbLGBT = findViewById(R.id.rbLG);

        rbLGBT.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbHom.setChecked(false);
                    rbFem.setChecked(false);
                }

            }
        });

        rbHom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbLGBT.setChecked(false);
                    rbFem.setChecked(false);
                }

            }
        });
        rbFem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    rbHom.setChecked(false);
                    rbLGBT.setChecked(false);
                }
            }
        });

        nuevo = findViewById(R.id.btNuevoUsuario);
        nuevo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String nombreString = nombre.getText().toString();
        String emailString = eMail.getText().toString();
        String passString = pass.getText().toString();
        String pass2String = pass2.getText().toString();

        if(TextUtils.isEmpty(nombreString) || TextUtils.isEmpty(emailString) || TextUtils.isEmpty(
                passString) || TextUtils.isEmpty(pass2String)){
            Toast.makeText(this,"Debes rellenar todos los campos",Toast.LENGTH_LONG).show();
        }else{
            if(!pass2String.equals(passString)){
                Toast.makeText(this,"La contraseña no coincide",Toast.LENGTH_LONG).show();
            }else{
                nuevoUsuario(emailString,passString,v);
            }
        }
    }
    private void nuevoUsuario(String emailString,String passwordString,View v){

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
    private String devuelveGenero(){
        String genero=null;
        if(rbFem.isChecked()){
            genero = Constantes.GENERO_MUJ;
        }else if (rbLGBT.isChecked()){
            genero = Constantes.GENERO_LGBT;
        }else if(rbHom.isChecked()){
            genero = Constantes.GENERO_HOM;
        }
        return  genero;
    }
    private void updateUI(FirebaseUser user){

        String emailString = eMail.getText().toString();
        String passwordString = pass.getText().toString();
        String userName = nombre.getText().toString();

        final ClaseUsuario usuarioOB = new ClaseUsuario(emailString,passwordString);
        usuarioOB.setNombreUduario(userName);
        usuarioOB.setPassword(passwordString);
        usuarioOB.setGenero(devuelveGenero());


        Map<String,Object> usuario = new HashMap<>();
        usuario.put("email",emailString);
        usuario.put("password",passwordString);
        usuario.put("userName",userName);
        usuario.put("genero",devuelveGenero());


        if(user!=null){

            db.collection(Constantes.TABLA_USUARIOS).document().set(usuario)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Usuario creado con exito",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), tabbed.class);
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
