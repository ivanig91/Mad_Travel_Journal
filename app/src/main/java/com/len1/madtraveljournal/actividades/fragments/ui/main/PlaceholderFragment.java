package com.len1.madtraveljournal.actividades.fragments.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.actividades.MostrarPop;
import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;
import com.len1.madtraveljournal.modelos.ListasYAdapters;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.DetalleLugar;
import com.len1.madtraveljournal.descargas.DescargaBares;
import com.len1.madtraveljournal.lugares.LugarBar;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private DescargaBares desBar;


    private ListasYAdapters listasYAdapters;
    private PageViewModel pageViewModel;
    private ClaseUsuario usuario;
    private Context context;
    private FirebaseFirestore db;




    public  PlaceholderFragment newInstance(int index,ClaseUsuario usuario,Context context) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        this.usuario = usuario;
        this.context = context;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        db = FirebaseFirestore.getInstance();
        int index = 1;


        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);

        listasYAdapters = new ListasYAdapters(getActivity().getApplicationContext());

        desBar = new DescargaBares(listasYAdapters,getActivity().getApplicationContext());
        desBar.execute();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_tabbed, container, false);
        Intent intent = getActivity().getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");

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
                if(s.equals("7")){
                    descargaFavoritos(listView);
                }

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarBar bar = (LugarBar) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalleLugar.class);
                intent.putExtra("bar",bar);
                intent.putExtra("usuario",usuario);
                startActivity(intent);

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Intent pop = new Intent(getContext(), MostrarPop.class);
                LugarBar bar = (LugarBar) parent.getItemAtPosition(position);
                pop.putExtra("bar",bar);
                startActivity(pop);
                return false;
            }
        });

        return root;
    }
    private void descargaFavoritos(final ListView listView){
        db.collection(Constantes.TABLA_USUARIOS).document(usuario.getEmail())
                .collection(Constantes.TABLA_FAVS).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    ArrayList<LugarBar> listaFavs = new ArrayList<>();
                    for(QueryDocumentSnapshot document :task.getResult()){

                        LugarBar bar = document.toObject(LugarBar.class);
                        listaFavs.add(bar);

                    }
                    BarAdapter nuevoAdapter = new BarAdapter(getContext(),listaFavs);
                    listView.setAdapter(nuevoAdapter);
                    nuevoAdapter.notifyDataSetChanged();
                }

            }
        });
    }
}