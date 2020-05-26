package com.len1.madtraveljournal.adapters;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ClaseUsuarioAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ClaseUsuario> listaUsuarios;
    private ClaseUsuario usuario;


    public ClaseUsuarioAdapter(Context context, ArrayList<ClaseUsuario> listaUsuarios) {
        this.context = context;
        this.listaUsuarios = listaUsuarios;

    }
    static class ViewHolder  {
        TextView nombre;
        ImageView foto;
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
            holder.foto.setTag(position);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        usuario = listaUsuarios.get(position);
        Log.i("usuarioAfuera",usuario.getNombreUsuario());
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

        return convertView;
    }


}
