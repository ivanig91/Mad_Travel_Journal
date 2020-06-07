package com.len1.madtraveljournal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.FotoDelUsuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FotoDelUsuarioAdapter extends BaseAdapter {
    private ArrayList<FotoDelUsuario> lista;
    private LayoutInflater inflater;
    private Context context;

    public FotoDelUsuarioAdapter(ArrayList<FotoDelUsuario> lista, Context context) {
        this.lista = lista;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return lista.size();
    }
    static class ViewHolder{
        TextView nombre;

        CircleImageView fotoUsuario;
        ImageView fotoSubida;
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView ==null){
            convertView = inflater.inflate(R.layout.fotolayout,null);
            holder = new ViewHolder();
            holder.nombre = convertView.findViewById(R.id.tvNombreFoto);

            holder.fotoUsuario = convertView.findViewById(R.id.ivFotoUsuarioSubida);
            holder.fotoSubida = convertView.findViewById(R.id.ivfotoSubida);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        FotoDelUsuario obj = lista.get(position);

        holder.nombre.setText(obj.getNombreUsuario());
        Picasso.get().load(obj.getUrlFotoUsuario()).into(holder.fotoUsuario);
        Picasso.get().load(obj.getUrlFotoBar()).into(holder.fotoSubida);
        return convertView;
    }
}
