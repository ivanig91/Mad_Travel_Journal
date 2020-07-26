package com.len1.madtraveljournal.actividades.ui.comentarios;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.squareup.picasso.Picasso;

public class FragmentInfo extends Fragment {
    LugarBar bar;
    public FragmentInfo newInstance(LugarBar bar){
        FragmentInfo fragment = new FragmentInfo();
        this.bar=bar;
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mostrar_pop,container,false);
        ImageView foto ;
        TextView nombre;
        TextView calle;
        TextView descripcion;
        foto = view.findViewById(R.id.ivPopFoto);
        nombre = view.findViewById(R.id.tvPopNombre);
        calle = view.findViewById(R.id.tvPopDir);
        descripcion = view.findViewById(R.id.tvPopDesc);
        nombre.setText(bar.getNombre());
        calle.setText(bar.getDireccion());
        descripcion.setText(bar.getDescripcion());
        //Picasso.get().load(lugar.getFotoUrl()).into(holder.icono);
        Picasso.get().load(bar.getFotoUrl()).into(foto);

        return view;
    }
}
