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

import com.google.firebase.firestore.FirebaseFirestore;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.actividades.DetalleLugar;
import com.len1.madtraveljournal.actividades.InfoLugar;
import com.len1.madtraveljournal.actividades.MostrarPop;
import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.descargas.DescargaBares;
import com.len1.madtraveljournal.descargas.DescargaCYC;
import com.len1.madtraveljournal.lugares.LugarBar;
import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.modelos.ListasYAdapters;

import java.util.ArrayList;

public class FragmentCc extends Fragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    //private DescargaBares desBar;
    DescargaCYC desBar;

    private PageViewModel pageViewModel;
    private ClaseUsuario usuario;
    private Context context;
    private FirebaseFirestore db;
    private ArrayList<LugarBar> lista;
    private BarAdapter adapter;
    private int origen;
    private ListView listView;
    private SearchView searchView=null;
    private SearchView.OnQueryTextListener queryTextListener;

    public FragmentCc newInstance(int index,ClaseUsuario usuario,Context context){
        FragmentCc fragment = new FragmentCc();
        this.usuario = usuario;
        this.context = context;
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER,index);
        origen=0;
        fragment.setArguments(bundle);

        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
        db = FirebaseFirestore.getInstance();
        setHasOptionsMenu(true);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
        lista = new ArrayList<>();
        adapter = new BarAdapter(getContext(),lista);
        desBar = new DescargaCYC(lista,getContext(),adapter);
        desBar.execute();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabbed,container,false);
        final Intent intent = getActivity().getIntent();
        usuario = (ClaseUsuario) intent.getSerializableExtra("usuario");
        listView = view.findViewById(R.id.lali1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LugarBar bar = (LugarBar) parent.getItemAtPosition(position);
                /*
                Intent intent = new Intent(getContext(), DetalleLugar.class);
                intent.putExtra("bar",bar);
                intent.putExtra("usuario",usuario);
                intent.putExtra("origen",origen);
                startActivity(intent);
                 */
                Intent nuevoIntent = new Intent(getContext(), InfoLugar.class);
                nuevoIntent.putExtra("bar",bar);
                nuevoIntent.putExtra("usuario",usuario);
                nuevoIntent.putExtra("origen",origen);
                startActivity(nuevoIntent);

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
                    adapter.getFilter().filter(newText);
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
