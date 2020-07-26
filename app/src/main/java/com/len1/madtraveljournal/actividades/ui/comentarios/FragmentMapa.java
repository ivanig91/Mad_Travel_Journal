package com.len1.madtraveljournal.actividades.ui.comentarios;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.lugares.LugarBar;

public class FragmentMapa extends Fragment implements OnMapReadyCallback {
    private LugarBar bar;
    private GoogleMap mMap;
    public FragmentMapa newInstance(LugarBar bar){
        FragmentMapa fragment = new FragmentMapa();
        this.bar = bar;
        return  fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_mapa,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        double latitud = Double.parseDouble(bar.getLatitud());
        double longitud = Double.parseDouble(bar.getLongitud());
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title(bar.getNombre()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
