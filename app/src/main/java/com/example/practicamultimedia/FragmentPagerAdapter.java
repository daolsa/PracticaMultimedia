package com.example.practicamultimedia;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

public class FragmentPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {

    final int PAGE_COUNT = 4;
    private String titulos[] = new String[]{"Animales","Música","Vídeo","Pelota"};
    private Context context;


    public FragmentPagerAdapter(FragmentManager fm,Context context) {
        super(fm,FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.context = context;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return Fragmento1.newInstance();
            case 1:
                return Fragmento2.newInstance();
            case 2:
                return Fragmento3.newInstance();
            case 3:
                return Fragmento4.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return titulos[position];


    }
}
