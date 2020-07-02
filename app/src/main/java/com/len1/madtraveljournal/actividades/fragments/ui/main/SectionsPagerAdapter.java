package com.len1.madtraveljournal.actividades.fragments.ui.main;

import android.content.Context;
import android.util.Log;


import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.len1.madtraveljournal.modelos.ClaseUsuario;
import com.len1.madtraveljournal.R;
import com.len1.madtraveljournal.modelos.Constantes;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private ClaseUsuario usuario;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1
            , R.string.tab_text_2,R.string.tab_text_3,R.string.tab_text_4,R.string.tab_text_5,R.string.tab_text_7};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm,ClaseUsuario usuario) {
        super(fm);
        this.usuario = usuario;
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        // Los objetos de descarga de datos los voy a descargar desde tabbed y le tienen que
        //


        if(position==0){
            FragmentCc fragmentCc = new FragmentCc();

            return  fragmentCc.newInstance(position+1,usuario,mContext);
        }else if (position ==1){
            /*
            PlaceholderFragment fragment = new PlaceholderFragment();
            return fragment.newInstance(position + 1,usuario,mContext);
             */
            FragmentCondicion fragment = new FragmentCondicion(Constantes.CAT_DISCOTECA);

            return fragment.newInstance(position+1,usuario,mContext);
        }else if(position ==2){
            FragmentCondicion fragment = new FragmentCondicion(Constantes.CAT_MUSICA_DIRECTO);
            return fragment.newInstance(position+1,usuario,mContext);
        }else if(position==3){
            FragmentCondicion fragment = new FragmentCondicion(Constantes.CAT_FLAMENCO);
            return fragment.newInstance(position+1,usuario,mContext);
        }else if( position ==4){
            FragmentCondicion fragment = new FragmentCondicion(Constantes.CAT_KARAOKE);
            return fragment.newInstance(position+1,usuario,mContext);
        }else{
            FragmentFavoritos fragmentFavoritos = new FragmentFavoritos();
            return fragmentFavoritos.newInstance(position+1,usuario,mContext);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {

        return TAB_TITLES.length;
    }
}