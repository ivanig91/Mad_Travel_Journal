package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.fragments.tabbed;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActualizarUsuario extends AppCompatActivity implements View.OnClickListener  {
    private EditText nombre;
    private Button nuevo,cargarFoto;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private RadioButton rbFem,rbHom,rbLGBT;
    private CircleImageView fotoInicial;
    private static final int RESULT_LOAD_IMG=1;
    private StorageReference storageReference;
    private Uri filePath;
    private ClaseUsuario usuarioOwner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        usuario = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();
        Intent intent = getIntent();
        usuarioOwner = (ClaseUsuario) intent.getSerializableExtra("usuario");
        nombre = findViewById(R.id.etNuevoNombre);
        nombre.setText(usuarioOwner.getNombreUsuario());
        rbFem = findViewById(R.id.rbFEM);
        rbHom = findViewById(R.id.rbHOM);
        rbLGBT = findViewById(R.id.rbLG);
        fotoInicial = findViewById(R.id.ivFotoInicial);
        Picasso.get().load(usuarioOwner.getUrlFoto()).into(fotoInicial);
        metodoAdivina();
        cargarFoto = findViewById(R.id.btCargarFoto);


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
        cargarFoto.setOnClickListener(this);
    }
    private void metodoAdivina(){
        if (usuarioOwner.getGenero().equals(Constantes.GENERO_HOM)) {

            rbHom.setChecked(true);
        }else if(usuarioOwner.getGenero().equals(Constantes.GENERO_MUJ)){
            rbFem.setChecked(true);
        }else{
            rbLGBT.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == nuevo.getId()){
            String nombreString = nombre.getText().toString();

            if(TextUtils.isEmpty(nombreString)){
                Toast.makeText(this,"Debes rellenar todos los campos",Toast.LENGTH_LONG).show();
            }else{

                if(filePath!=null){
                    subirFoto(usuario);
                }else{
                    updateUI(usuario,null);
                }

            }
        }else{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
        }


    }

    private void subirFoto(final FirebaseUser user){

        String path = "fotoperfil/"+ UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child(path);
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri = uri.toString();
                        updateUI(user,downloadUri);
                    }
                });

            }
        });
    }
    private void updateUI(FirebaseUser user, String urlFoto){

        String userName = nombre.getText().toString();

        final ClaseUsuario usuarioOB = usuarioOwner;
        usuarioOB.setNombreUsuario(userName);
        usuarioOB.setGenero(devuelveGenero());



        Map<String,Object> usuario = new HashMap<>();
        usuario.put("nombreUsuario",userName);
        usuario.put("genero",devuelveGenero());
        usuario.put("barActual","");

        if(urlFoto!=null){
            usuarioOB.setUrlFoto(urlFoto);
            usuario.put("urlFoto",usuarioOB.getUrlFoto());
        }



        if(user!=null){

            db.collection(Constantes.TABLA_USUARIOS).document(usuarioOB.getEmail()).update(usuario)
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

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                filePath = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(filePath);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                fotoInicial.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(ActualizarUsuario.this, "Error", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(ActualizarUsuario.this, "No escogiste foto",Toast.LENGTH_LONG).show();
        }
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


}
