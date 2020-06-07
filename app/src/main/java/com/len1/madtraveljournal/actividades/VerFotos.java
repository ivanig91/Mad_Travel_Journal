package com.len1.madtraveljournal.actividades;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.adapters.FotoDelUsuarioAdapter;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.FotoDelUsuario;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VerFotos extends AppCompatActivity {
    private ListView listView;
    private ImageButton agregar;
    private StorageReference storageReference;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private static final int RESULT_LOAD_IMG=1;
    private Uri filePath;
    private ClaseUsuario usuarioOwner;
    FirebaseUser user;
    private String nombreBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_ver_fotos);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        usuarioOwner = (ClaseUsuario) intent.getSerializableExtra("usuario");
        nombreBar = intent.getStringExtra("bar");
        user = mAuth.getCurrentUser();
        listView = findViewById(R.id.lvVerFotos);
        descargarFotos();
        agregar = findViewById(R.id.btSubirFoto);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });
    }

    private void subirFoto(final FirebaseUser user){

        String path = "fotosBar/"+ UUID.randomUUID().toString();
        final StorageReference ref = storageReference.child(path);
        ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String downloadUri = uri.toString();
                        subirDocumento(downloadUri);
                        descargarFotos();
                    }
                });

            }
        });
    }
    private void descargarFotos(){
        db.collection(Constantes.TABLA_FOTOS).whereEqualTo("nombreBar",nombreBar)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<FotoDelUsuario> lista = new ArrayList<>();
                    for(QueryDocumentSnapshot document : task.getResult()){
                        FotoDelUsuario obj =document.toObject(FotoDelUsuario.class);
                        lista.add(obj);
                    }
                    FotoDelUsuarioAdapter adapter = new FotoDelUsuarioAdapter(lista,getApplicationContext());
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }
    private void subirDocumento(String downloadUri){
        Map<String, Object> mapa = new HashMap<>();
        mapa.put("nombreUsuario",usuarioOwner.getNombreUsuario());
        mapa.put("nombreBar",nombreBar);
        mapa.put("urlFotoBar",downloadUri);
        mapa.put("urlFotoUsuario",usuarioOwner.getUrlFoto());
        db.collection(Constantes.TABLA_FOTOS).document().set(mapa);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            filePath = data.getData();
            subirFoto(user);
            Toast.makeText(VerFotos.this, "Foto subida con exito",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(VerFotos.this, "No escogiste foto",Toast.LENGTH_LONG).show();
        }
    }
}
