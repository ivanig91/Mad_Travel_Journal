package com.len1.madtraveljournal.fragments.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.ListasYAdapters;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.DetalleLugar;
import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.adapters.CulturaAdapter;
import com.len1.madtraveljournal.adapters.LugarAdapter;
import com.len1.madtraveljournal.adapters.MonumentoAdapter;
import com.len1.madtraveljournal.adapters.MuseoAdapter;
import com.len1.madtraveljournal.adapters.PiscinaAdapter;
import com.len1.madtraveljournal.adapters.TurismoAdapter;
import com.len1.madtraveljournal.descargas.DescargaBares;
import com.len1.madtraveljournal.descargas.DescargaCultura;
import com.len1.madtraveljournal.descargas.DescargaMercados;
import com.len1.madtraveljournal.descargas.DescargaMuseo;
import com.len1.madtraveljournal.descargas.DescargaPiscina;
import com.len1.madtraveljournal.descargas.DescargaTurismo;
import com.len1.madtraveljournal.descargas.descargaMonumento;
import com.len1.madtraveljournal.lugares.Lugar;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.lugares.LugarCultura;
import com.len1.madtraveljournal.lugares.LugarMercado;
import com.len1.madtraveljournal.lugares.LugarMonumento;
import com.len1.madtraveljournal.lugares.LugarMuseo;
import com.len1.madtraveljournal.lugares.LugarPiscina;
import com.len1.madtraveljournal.lugares.LugarTurismo;


import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private DescargaBares desBar;


    private ListasYAdapters listasYAdapters;
    private PageViewModel pageViewModel;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        int index = 1;

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

        listasYAdapters = new ListasYAdapters(getActivity().getApplicationContext());

        desBar = new DescargaBares(listasYAdapters);
        desBar.execute();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabbed, container, false);

        final ListView listView = root.findViewById(R.id.lali1);

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                assert s != null;
                if(s.equals("1")){
                   listView.setAdapter(listasYAdapters.getAdapterCoctelerias());
                   listasYAdapters.getAdapterCoctelerias().notifyDataSetChanged();
                }
                if(s.equals("2")){
                    listView.setAdapter(listasYAdapters.getAdapterDiscoteca());
                    listasYAdapters.getAdapterDiscoteca().notifyDataSetChanged();
                }
                if(s.equals("3")){
                    listView.setAdapter(listasYAdapters.getAdapterMusicaDirecto());
                    listasYAdapters.getAdapterMusicaDirecto().notifyDataSetChanged();
                }
                if(s.equals("4")){
                    listView.setAdapter(listasYAdapters.getAdapterFlamenco());
                    listasYAdapters.getAdapterFlamenco().notifyDataSetChanged();
                }
                if(s.equals("5")){
                    listView.setAdapter(listasYAdapters.getAdapterKaraoke());
                    listasYAdapters.getAdapterKaraoke().notifyDataSetChanged();
                }
                if(s.equals("6")){
                    listView.setAdapter(listasYAdapters.getAdapterOtros());
                    listasYAdapters.getAdapterOtros().notifyDataSetChanged();
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarBar bar = (LugarBar) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalleLugar.class);
                intent.putExtra("bar",bar);
                startActivity(intent);

            }
        });
        return root;
    }
}