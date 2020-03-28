package com.len1.madtraveljournal.fragments.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.len1.madtraveljournal.Constantes;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.adapters.CulturaAdapter;
import com.len1.madtraveljournal.adapters.LugarAdapter;
import com.len1.madtraveljournal.adapters.MonumentoAdapter;
import com.len1.madtraveljournal.descargas.DescargaCultura;
import com.len1.madtraveljournal.descargas.DescargaMercados;
import com.len1.madtraveljournal.descargas.descargaMonumento;
import com.len1.madtraveljournal.lugares.LugarCultura;
import com.len1.madtraveljournal.lugares.LugarMercado;
import com.len1.madtraveljournal.lugares.LugarMonumento;


import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<String> cadenas;
    private DescargaMercados desMercados;
    private descargaMonumento desMonumento;
    private DescargaCultura desCultura;
    private ArrayList<LugarCultura> actsCultura;
    private ArrayList<LugarMercado> mercados;
    private ArrayList<LugarMonumento> monumentos;

    private LugarAdapter adapter;
    private MonumentoAdapter adapterMonumento;
    private CulturaAdapter adapterCultura;

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
        mercados = new ArrayList<>();
        monumentos = new ArrayList<>();
        actsCultura = new ArrayList<>();

        adapter = new  LugarAdapter(getActivity().getApplicationContext(),mercados);
        adapterMonumento = new MonumentoAdapter(getActivity().getApplicationContext(),monumentos);
        adapterCultura = new CulturaAdapter(getActivity().getApplicationContext(),actsCultura);
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

                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if(desMercados==null){
                        desMercados = new DescargaMercados(mercados,adapter);
                        desMercados.execute(Constantes.urlMercados);
                    }
                }else{
                    if(s.equals("2")){

                        listView.setAdapter(adapterMonumento);
                        adapterMonumento.notifyDataSetChanged();
                        if(desMonumento==null){
                            desMonumento = new descargaMonumento(monumentos,adapterMonumento);
                            desMonumento.execute(Constantes.urlMonumentos);
                         
                        }
                    }else{
                        if(s.equals("3")){

                            listView.setAdapter(adapterCultura);
                            adapterCultura.notifyDataSetChanged();
                            if(desCultura==null){
                                desCultura = new DescargaCultura(actsCultura,adapterCultura);
                                desCultura.execute(Constantes.urlCultura);
                            }

                        }
                    }
                }
                Log.i("tabF",s);
            }
        });
        return root;
    }
}