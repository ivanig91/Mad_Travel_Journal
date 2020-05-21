package com.len1.madtraveljournal.modelos;

import android.content.Context;

import com.len1.madtraveljournal.adapters.BarAdapter;
import com.len1.madtraveljournal.lugares.LugarBar;

import java.util.ArrayList;

public class ListasYAdapters {
    private ArrayList<LugarBar> flamenco;
    private ArrayList<LugarBar> cocteleriasTerrazasYBares;
    private ArrayList<LugarBar> musicaDirecto;
    private ArrayList<LugarBar> discoteca;
    private ArrayList<LugarBar> otros;
    private ArrayList<LugarBar> karaoke;


    private BarAdapter adapterFlamenco;
    private BarAdapter adapterCoctelerias;
    private BarAdapter adapterMusicaDirecto;
    private BarAdapter adapterOtros;
    private BarAdapter adapterKaraoke;
    private BarAdapter adapterDiscoteca;
    public ListasYAdapters(Context context) {
        flamenco = new ArrayList<>();
        cocteleriasTerrazasYBares = new ArrayList<>();
        musicaDirecto = new ArrayList<>();
        discoteca = new ArrayList<>();
        otros = new ArrayList<>();
        karaoke = new ArrayList<>();

        adapterFlamenco = new BarAdapter(context,flamenco);
        adapterCoctelerias = new BarAdapter(context,cocteleriasTerrazasYBares);
        adapterOtros = new BarAdapter(context,otros);
        adapterKaraoke = new BarAdapter(context,karaoke);
        adapterMusicaDirecto = new BarAdapter(context,musicaDirecto);
        adapterDiscoteca = new BarAdapter(context,discoteca);

    }
    public void notificarAdapters(){

        adapterFlamenco.notifyDataSetChanged();
        adapterCoctelerias.notifyDataSetChanged();
        adapterMusicaDirecto.notifyDataSetChanged();
        adapterOtros.notifyDataSetChanged();
        adapterKaraoke.notifyDataSetChanged();
        adapterDiscoteca.notifyDataSetChanged();
    }

    public ArrayList<LugarBar> getFlamenco() {
        return flamenco;
    }

    public void setFlamenco(ArrayList<LugarBar> flamenco) {
        this.flamenco = flamenco;
    }

    public ArrayList<LugarBar> getCocteleriasTerrazasYBares() {
        return cocteleriasTerrazasYBares;
    }

    public void setCocteleriasTerrazasYBares(ArrayList<LugarBar> cocteleriasTerrazasYBares) {
        this.cocteleriasTerrazasYBares = cocteleriasTerrazasYBares;
    }

    public ArrayList<LugarBar> getMusicaDirecto() {
        return musicaDirecto;
    }

    public void setMusicaDirecto(ArrayList<LugarBar> musicaDirecto) {
        this.musicaDirecto = musicaDirecto;
    }

    public ArrayList<LugarBar> getDiscoteca() {
        return discoteca;
    }

    public void setDiscoteca(ArrayList<LugarBar> discoteca) {
        this.discoteca = discoteca;
    }

    public ArrayList<LugarBar> getOtros() {
        return otros;
    }

    public void setOtros(ArrayList<LugarBar> otros) {
        this.otros = otros;
    }

    public ArrayList<LugarBar> getKaraoke() {
        return karaoke;
    }

    public void setKaraoke(ArrayList<LugarBar> karaoke) {
        this.karaoke = karaoke;
    }

    public BarAdapter getAdapterFlamenco() {
        return adapterFlamenco;
    }

    public void setAdapterFlamenco(BarAdapter adapterFlamenco) {
        this.adapterFlamenco = adapterFlamenco;
    }

    public BarAdapter getAdapterCoctelerias() {
        return adapterCoctelerias;
    }

    public void setAdapterCoctelerias(BarAdapter adapterCoctelerias) {
        this.adapterCoctelerias = adapterCoctelerias;
    }

    public BarAdapter getAdapterMusicaDirecto() {
        return adapterMusicaDirecto;
    }

    public void setAdapterMusicaDirecto(BarAdapter adapterMusicaDirecto) {
        this.adapterMusicaDirecto = adapterMusicaDirecto;
    }

    public BarAdapter getAdapterOtros() {
        return adapterOtros;
    }

    public void setAdapterOtros(BarAdapter adapterOtros) {
        this.adapterOtros = adapterOtros;
    }

    public BarAdapter getAdapterKaraoke() {
        return adapterKaraoke;
    }

    public void setAdapterKaraoke(BarAdapter adapterKaraoke) {
        this.adapterKaraoke = adapterKaraoke;
    }

    public BarAdapter getAdapterDiscoteca() {
        return adapterDiscoteca;
    }

    public void setAdapterDiscoteca(BarAdapter adapterDiscoteca) {
        this.adapterDiscoteca = adapterDiscoteca;
    }
}
