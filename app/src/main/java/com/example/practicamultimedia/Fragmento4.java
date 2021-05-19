package com.example.practicamultimedia;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class Fragmento4 extends Fragment implements View.OnClickListener {

    public static Fragmento4 newInstance(){
        Fragmento4 fragmento4 = new Fragmento4();
        return fragmento4;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment4,container,false);
        return view;
    }





















    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = (ImageView) view.findViewById(R.id.pelota);
        imageView.setOnClickListener(this);
        //imageView.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(),android.R.color.holo_orange_dark)));
        //imageView.setImageTintMode(PorterDuff.Mode.MULTIPLY);



    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.pelota){

            Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.botar);
            v.startAnimation(animation);

        }


    }
}
