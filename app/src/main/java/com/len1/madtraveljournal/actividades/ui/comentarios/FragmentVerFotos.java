package com.len1.madtraveljournal.actividades.ui.comentarios;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class FragmentVerFotos extends Fragment {
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

    public FragmentVerFotos newInstance(ClaseUsuario usuario, String bar){
        FragmentVerFotos fragment = new FragmentVerFotos();
        this.usuarioOwner = usuario;
        this.nombreBar = bar;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        storageReference = FirebaseStorage.getInstance().getReference();
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_ver_fotos,container,false);
        listView = view.findViewById(R.id.lvVerFotos);
        descargarFotos();
        agregar = view.findViewById(R.id.btSubirFoto);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        return view;
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
                    FotoDelUsuarioAdapter adapter = new FotoDelUsuarioAdapter(lista,getContext());
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            filePath = data.getData();
            subirFoto(user);
            Toast.makeText(getContext(), "Foto subida con exito",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(getContext(), "No escogiste foto",Toast.LENGTH_LONG).show();
        }
    }
}
