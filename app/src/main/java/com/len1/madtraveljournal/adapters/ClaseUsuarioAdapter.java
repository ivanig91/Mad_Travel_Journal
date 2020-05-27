package com.len1.madtraveljournal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.Matches;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ClaseUsuarioAdapter extends BaseAdapter  {
    private Context context;
    private ArrayList<ClaseUsuario> listaUsuarios;
    private ClaseUsuario usuarioOwner;
    private FirebaseFirestore db;


    public ClaseUsuarioAdapter(Context context, ArrayList<ClaseUsuario> listaUsuarios,ClaseUsuario
                               usuarioOwner) {
        this.usuarioOwner = usuarioOwner;
        this.context = context;
        this.listaUsuarios = listaUsuarios;
        db = FirebaseFirestore.getInstance();

    }



    static class ViewHolder {
        TextView nombre;
        ImageView foto;
        ImageButton btAccion;
    }

    @Override
    public int getCount() {
        return listaUsuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return listaUsuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.claseusuarioadapter,null);
            holder = new ViewHolder();
            holder.foto = convertView.findViewById(R.id.ivEntraFoto);
            holder.nombre = convertView.findViewById(R.id.tvEntraNombre);
            holder.btAccion = convertView.findViewById(R.id.btAccion);
            holder.btAccion.setTag(position);
            holder.foto.setTag(position);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ClaseUsuario usuario = listaUsuarios.get(position);
        holder.nombre.setText(usuario.getNombreUsuario());
        Picasso.get().load(usuario.getUrlFoto()).into(holder.foto);
        View v = convertView.findViewById(R.id.paraVer);

        holder.foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog settingsDialog = new Dialog(context);
                settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(1000, 1000);
                lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                ImageView iv = new ImageView(context);
                ClaseUsuario uruaInt = listaUsuarios.get((int)holder.foto.getTag());
                iv.setLayoutParams(lp);
                Picasso.get().load(uruaInt.getUrlFoto()).into(iv);
                settingsDialog.addContentView(iv,lp);
                settingsDialog.show();
            }
        });
        holder.btAccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClaseUsuario recibeUsuario = listaUsuarios.get( (int)holder.btAccion.getTag());
                buscarDocumento(recibeUsuario,v);
            }
        });

        return convertView;
    }


    private void buscarDocumento(final ClaseUsuario personaBuscada, final View v){
        String nombreDocumento = personaBuscada.getEmail()+usuarioOwner.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        // Creo una coleccion nueva que tenga como clave nombreDocumento
                        // Tambien debo mover a TRUE el valor en este documento al moverlo a TRUE
                        //le debe llegar una notificación al usuario que lo mando
                        Log.i("ivan","se ha creado un chat");
                    }else{
                        soyIntenso(personaBuscada,v);
                    }
                }
            }
        });

    }
    private void soyIntenso(final ClaseUsuario personaBuscada,final View v){
        String nombreDocumento = usuarioOwner.getEmail()+personaBuscada.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Matches posibleMatch = document.toObject(Matches.class);
                        if(posibleMatch.isMatch()){
                            //Esto todavia no funciona porque todavia no modifico el true
                            Log.i("ivan","si hice click aca, quiero volver a abrir la conversacion");
                        }else{
                            Snackbar.make(v,"Esperando match",BaseTransientBottomBar.LENGTH_LONG).show();
                        }
                    }else{
                        invitarUnaCopa(personaBuscada);
                    }
                }
            }
        });
    }
    private void invitarUnaCopa(final ClaseUsuario recibeUsuario){
        Map<String,Object> mapaMatch = new HashMap<>();
        mapaMatch.put("envia",usuarioOwner.getEmail());
        mapaMatch.put("recibe",recibeUsuario.getEmail());
        mapaMatch.put("bar",usuarioOwner.getBarActual());
        mapaMatch.put("match",false);
        String nombreDocumento = usuarioOwner.getEmail()+recibeUsuario.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).set(mapaMatch);
    }



}
