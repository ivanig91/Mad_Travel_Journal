package com.len1.madtraveljournal.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.ChatActivity;
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
        final String nombreDocumento = personaBuscada.getEmail()+usuarioOwner.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Matches match = document.toObject(Matches.class);
                    if(document.exists()){
                        crearMatch(match, nombreDocumento);
                    }else{
                        soyIntenso(personaBuscada,v);
                    }
                }
            }
        });

    }
    private void soyIntenso(final ClaseUsuario personaBuscada,final View v){
        final String nombreDocumento = usuarioOwner.getEmail()+personaBuscada.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        Matches posibleMatch = document.toObject(Matches.class);
                        if(posibleMatch.isMatch()){
                            Intent intent = new Intent(context, ChatActivity.class);
                            intent.putExtra("coleccion",nombreDocumento);
                            intent.putExtra("match",posibleMatch);
                            intent.putExtra("usuario",usuarioOwner);
                            context.startActivity(intent);
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
        mostrarDialogo(recibeUsuario.getNombreUsuario()).show();
        Map<String,Object> mapaMatch = new HashMap<>();
        mapaMatch.put("envia",usuarioOwner.getEmail());
        mapaMatch.put("fotoEnvia",usuarioOwner.getUrlFoto());
        mapaMatch.put("nombreEnvia",usuarioOwner.getNombreUsuario());
        mapaMatch.put("recibe",recibeUsuario.getEmail());
        mapaMatch.put("nombreRecibe",recibeUsuario.getNombreUsuario());
        mapaMatch.put("fotoRecibe",recibeUsuario.getUrlFoto());
        mapaMatch.put("bar",usuarioOwner.getBarActual());
        mapaMatch.put("match",false);
        String nombreDocumento = usuarioOwner.getEmail()+recibeUsuario.getEmail()+usuarioOwner.getBarActual();
        db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).set(mapaMatch);
    }
    private void crearMatch(Matches match, String nombreDocumento){
        if(match.isMatch()){
           intentChat(match,nombreDocumento);
        }else{
            dialogoEncotnrado(match,nombreDocumento).show();
            Map<String,Object> mapaMatch = new HashMap<>();
            mapaMatch.put("envia",match.getEnvia());
            mapaMatch.put("nombreEnvia",match.getNombreEnvia());
            mapaMatch.put("fotoEnvia",match.getFotoEnvia());
            mapaMatch.put("recibe",match.getRecibe());
            mapaMatch.put("nombreRecibe",match.getNombreRecibe());
            mapaMatch.put("fotoRecibe",match.getFotoRecibe());
            mapaMatch.put("bar",match.getBar());
            mapaMatch.put("match",true);
            db.collection(Constantes.TABLA_MATCH).document(nombreDocumento).update(mapaMatch);
        }


    }
    private void intentChat(Matches match, String nombreDocumento){
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("coleccion",nombreDocumento);
        intent.putExtra("match",match);
        intent.putExtra("usuario",usuarioOwner);
        context.startActivity(intent);
    }
    private Dialog dialogoEncotnrado(final Matches match, final String nombreDocumento){
        String texto= "" ;
        texto = context.getString(R.string.match_ecnotnrado)+" "+match.getNombreEnvia();
        LayoutInflater inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.dialoglayout,null);
        TextView textView = dialogView.findViewById(R.id.tvDialog);
        textView.setText(texto);
        builder.setView(dialogView).setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        intentChat(match,nombreDocumento);
                    }
                });

        return  builder.create();
    }

    private Dialog mostrarDialogo(String nombre){
        String texto= "" ;
        texto= context.getString(R.string.match_enviado)+" "+nombre;
        LayoutInflater inflater = LayoutInflater.from(context);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.dialoglayout,null);
        TextView textView = dialogView.findViewById(R.id.tvDialog);
        textView.setText(texto);
        builder.setView(dialogView).setPositiveButton(R.string.aceptar,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        return  builder.create();
    }
}
