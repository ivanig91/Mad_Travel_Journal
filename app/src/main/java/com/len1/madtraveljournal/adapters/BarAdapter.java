package com.len1.madtraveljournal.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

import java.util.ArrayList;

public class BarAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<LugarBar> listaLugar;
    private LayoutInflater inflater;

    public BarAdapter(Context context, ArrayList<LugarBar> listaLugar) {
        this.context = context;
        this.listaLugar = listaLugar;
        inflater = LayoutInflater.from(context);
    }
    static class ViewHolder{
        ImageView icono;
        TextView nombre;
        TextView calle;

    }

    @Override
    public int getCount() {
        return listaLugar.size();
    }

    @Override
    public Object getItem(int position) {
        return listaLugar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lvlugar, null);

            holder = new ViewHolder();
            holder.icono = (ImageView) convertView.findViewById(R.id.ivFoto);
            holder.nombre = (TextView) convertView.findViewById(R.id.tvNombrLugar);
            holder.calle = (TextView) convertView.findViewById(R.id.tvCalleLugar);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        LugarBar lugar = listaLugar.get(position);
        holder.icono.setImageResource(lugar.getIcono());
        holder.nombre.setText(lugar.getNombre());
        holder.calle.setText(lugar.getDireccion());

        return convertView;
    }
}
