package com.len1.madtraveljournal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.Post;

import java.util.ArrayList;


public class PostAdapter extends BaseAdapter {
    private ArrayList<Post> lista;
    private LayoutInflater inflater;
    private Context context;


    public PostAdapter(ArrayList<Post> lista, Context context) {
        this.lista = lista;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    static class ViewHolder{

        ImageView foto;
        TextView nombre;
        TextView comentario;
    }

    @Override
    public int getCount() {
        return lista.size();
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


        if(convertView==null){
            convertView = inflater.inflate(R.layout.contenido_comentario,null);
            holder = new ViewHolder();

            holder.foto = convertView.findViewById(R.id.ivFotoUsuario);
            holder.comentario = convertView.findViewById(R.id.tvComentario);
            holder.nombre = convertView.findViewById(R.id.tvNombreUsuario);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Post post = lista.get(position);
        holder.nombre.setText(post.getUsuario().getNombreUsuario());
        holder.comentario.setText(post.getComentario());
        // acá tengo que poner un if para que se baje la foto del usuario
        // de firebase storage, en caso de que el link sea nulo pongo imagenes predeterminadas
        // de acuerdo al genero de la persona
        holder.foto.setImageResource(R.drawable.p18);
        return convertView;
    }
}
