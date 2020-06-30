package com.len1.madtraveljournal.actividades.fragments.ui.main;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.DetalleLugar;
import com.len1.madtraveljournal.actividades.MostrarPop;
import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.descargas.DescargaCYC;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.Constantes;

import java.util.ArrayList;

public class FragmentFavoritos extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ClaseUsuario usuario;
    private FirebaseFirestore db;
    private Context context;
    private BarAdapter adapter;
    private int origen;
    private ListView listView;
    private SearchView searchView=null;
    BarAdapter nuevoAdapter;
    private SearchView.OnQueryTextListener queryTextListener;

    public FragmentFavoritos newInstance(int index,ClaseUsuario usuario,Context context){
        FragmentFavoritos fragment = new FragmentFavoritos();
        this.usuario = usuario;
        this.context = context;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        origen =1;
        fragment.setArguments(bundle);
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PageViewModel pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        setHasOptionsMenu(true);
        int index = 1;
        db = FirebaseFirestore.getInstance();
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        ArrayList<LugarBar> lista = new ArrayList<>();
        adapter = new BarAdapter(getContext(), lista);

        //listasYAdapters = new ListasYAdapters(getActivity().getApplicationContext());
        /*
        desBar = new DescargaBares(listasYAdapters,getActivity().getApplicationContext());
        desBar.execute();*/
        //private DescargaBares desBar;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabbed,container,false);
        Intent intent = getActivity().getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        listView = view.findViewById(R.id.lali1);
        listView.setAdapter(adapter);
        descargaFavoritos(listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarBar bar = (LugarBar) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetalleLugar.class);
                intent.putExtra("bar",bar);
                intent.putExtra("usuario",usuario);
                intent.putExtra("origen",origen);
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
        return view;
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
                    nuevoAdapter = new BarAdapter(getContext(),listaFavs);
                    listView.setAdapter(nuevoAdapter);
                    nuevoAdapter.notifyDataSetChanged();
                }

            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.info, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if(searchItem!=null){
            searchView = (SearchView) searchItem.getActionView();
        }
        if(searchView!=null){
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // aca debo poner aglo que cierre el dialogo de texto
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    nuevoAdapter.getFilter().filter(newText);
                    return false;
                }
            };

            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
