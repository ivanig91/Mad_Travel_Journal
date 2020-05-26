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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

public class ClaseUsuarioAdapter extends BaseAdapter {
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
    static class ViewHolder  {
        TextView nombre;
        ImageView foto;
        Button btAccion;
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
                invitarUnaCopa(recibeUsuario);
            }
        });

        return convertView;
    }
    private void invitarUnaCopa(final ClaseUsuario recibeUsuario){
        Map<String,Object> mapaMatch = new HashMap<>();
        mapaMatch.put("envia",usuarioOwner.getEmail());
        mapaMatch.put("recibe",recibeUsuario.getEmail());
        db.collection(Constantes.TABLA_MATCH).document().set(mapaMatch).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                buscarmeMatch(recibeUsuario.getEmail());
            }
        });
    }
    private void buscarmeMatch(final String personaBuscada){
        db.collection(Constantes.TABLA_MATCH).whereEqualTo("recibe",usuarioOwner.getEmail()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document: task.getResult()){
                                Matches posibleMatch = document.toObject(Matches.class);

                                if(posibleMatch.getEnvia().equals(personaBuscada)){
                                    Log.i("matchEncontrado","match encotnrado");
                                }
                            }
                        }
                    }
                });
    }

}
