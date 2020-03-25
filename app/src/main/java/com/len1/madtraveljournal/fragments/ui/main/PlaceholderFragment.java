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
import com.len1.madtraveljournal.descargas.DescargaMercados;
import com.len1.madtraveljournal.lugares.LugarMercado;


import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private ArrayList<String> cadenas;
    private DescargaMercados desMercados;
    private ArrayList<LugarMercado> mercados;
    private ArrayAdapter<String> adapter;

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
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabbed, container, false);
        cadenas = new ArrayList<>();
        mercados = new ArrayList<>();
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,cadenas);

        final ListView listView = root.findViewById(R.id.lali1);
        listView.setAdapter(adapter);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                assert s != null;
                if(s.equals("1")){
                    if(desMercados==null){

                        desMercados = new DescargaMercados(mercados,adapter,cadenas);
                        desMercados.execute(Constantes.urlMercados);
                    }
                }else{
                    if(s.equals("2")){

                    }
                }
            }
        });
        return root;
    }
}