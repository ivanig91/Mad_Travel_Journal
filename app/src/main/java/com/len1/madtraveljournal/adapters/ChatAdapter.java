package com.len1.madtraveljournal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.Chat;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter {
    private static final int ENVIADO = 1;
    private static  final int RECIBIDO = 2;

    private Context context;
    private List<Chat> listaChat;
    private ClaseUsuario usuarioOwner;

    public ChatAdapter(Context context, List<Chat> listaChat,ClaseUsuario usuarioOwner) {
        this.context = context;
        this.listaChat = listaChat;
        this.usuarioOwner = usuarioOwner;
    }

    private class RecibidosHolder extends RecyclerView.ViewHolder {
        CircleImageView foto;
        TextView nombre;
        TextView mensaje;
        TextView hora;

        public RecibidosHolder(@NonNull View itemView) {
            super(itemView);
            foto = itemView.findViewById(R.id.image_message_profile);
            nombre = itemView.findViewById(R.id.text_message_name);
            mensaje = itemView.findViewById(R.id.text_message_body2);

        }

        void bind (Chat chat){
            // debo poner un metodo que ponga la hora en el textView
            nombre.setText(chat.getNombre());
            mensaje.setText(chat.getMensaje());
            Picasso.get().load(chat.getFoto()).into(foto);
        }
    }
    private class EnviadosHolder extends RecyclerView.ViewHolder {
        TextView mensaje;
        TextView hora;

        public EnviadosHolder(@NonNull View itemView) {
            super(itemView);
            mensaje = itemView.findViewById(R.id.text_message_body);

        }

        void bind(Chat chat){
            mensaje.setText(chat.getMensaje());
            // debo poner un metodo que ponga la hora tambien
        }

    }

    @Override
    public int getItemCount() {
        return listaChat.size();
    }

    @Override
    public int getItemViewType(int position) {
       Chat mensaje = listaChat.get(position);
        if(mensaje.getEmail().equals(usuarioOwner.getEmail())){
            return ENVIADO;
        }else{
            return  RECIBIDO;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType==ENVIADO){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_sent,parent,false);
            return new EnviadosHolder(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received,parent,false);
            return new RecibidosHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat mensjae = listaChat.get(position);
        if(holder.getItemViewType() == ENVIADO){
            ((EnviadosHolder) holder).bind(mensjae);
        }else{
            ((RecibidosHolder)holder).bind(mensjae);
        }
    }
}
