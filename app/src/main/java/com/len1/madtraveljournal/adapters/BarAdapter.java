package com.len1.madtraveljournal.adapters;


import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.len1.madtraveljournal.R;

import com.len1.madtraveljournal.lugares.Lugar;
import com.len1.madtraveljournal.lugares.LugarBar;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BarAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<LugarBar> listaLugar;
    private ArrayList<LugarBar> listaFiltrada;
    private LayoutInflater inflater;
    private MyFilter myFilter = new MyFilter();
    LugarBar lugar=null;


    public BarAdapter(Context context, ArrayList<LugarBar> listaLugar) {
        this.context = context;
        this.listaLugar = listaLugar;
        this.listaFiltrada = listaLugar;
        inflater = LayoutInflater.from(context);
    }



    static class ViewHolder{
        ImageView icono;
        TextView nombre;
        TextView calle;
        TextView descripcion;

    }

    @Override
    public int getCount() {
        return listaFiltrada.size();
    }

    @Override
    public Object getItem(int position) {
        return listaFiltrada.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       final ViewHolder holder ;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.lvlugar, null);

            holder = new ViewHolder();
            holder.icono = (ImageView) convertView.findViewById(R.id.ivFoto3);
            holder.nombre = (TextView) convertView.findViewById(R.id.tvNombrLugar);
            holder.calle = (TextView) convertView.findViewById(R.id.tvCalleLugar);
            holder.descripcion = convertView.findViewById(R.id.tvDescripcionCover);
            holder.icono.setTag(position);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        lugar = listaFiltrada.get(position);
        holder.nombre.setText(lugar.getNombre());
        holder.calle.setText(lugar.getDireccion());
        String desc;
        if(lugar.getDescripcion().length()>=140){
            desc = lugar.getDescripcion().substring(0,140)+"...";
        }else{
            desc = lugar.getDescripcion();
        }
        

        holder.descripcion.setText(desc);
        Picasso.get().load(lugar.getFotoUrl()).into(holder.icono);


        return convertView;
    }
    @Override
    public Filter getFilter() {
        return myFilter;
    }
    private class MyFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String filterString = constraint.toString().toLowerCase();
            FilterResults results = new FilterResults();
            final ArrayList<LugarBar> list = listaLugar;
            int count = list.size();
            final ArrayList<LugarBar> nList = new ArrayList<>(count);

            for(LugarBar lugar: list){
                if(lugar.getNombre().toLowerCase().contains(filterString)){
                    nList.add(lugar);
                }
            }
            results.values = nList;
            results.count = nList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaFiltrada = (ArrayList<LugarBar>) results.values;
            notifyDataSetChanged();
        }
    }


}
