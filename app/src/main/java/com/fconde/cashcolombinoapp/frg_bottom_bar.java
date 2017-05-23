package com.fconde.cashcolombinoapp;

import android.app.Fragment;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by FConde on 22/05/2017.
 */

public class frg_bottom_bar extends Fragment {


    public frg_bottom_bar(){

        //imgBtnContacto = (ImageButton)find
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ImageButton imgBtnContacto, imgBtnFacebook, imgBtnGoogle, imgBtnBlog, imgBtnCompartir;

        final View view;
        view = inflater.inflate(R.layout.lay_frg_bottom_bar, container);

        imgBtnContacto = (ImageButton)view.findViewById(R.id.imgBttContacto);
        imgBtnFacebook = (ImageButton)view.findViewById(R.id.imgBttFacebook);
        imgBtnGoogle = (ImageButton)view.findViewById(R.id.imgBttGooglePlus);
        imgBtnBlog = (ImageButton)view.findViewById(R.id.imgBttBlog);
        imgBtnCompartir = (ImageButton)view.findViewById(R.id.imgBttCompartir);

        imgBtnContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Contacto", Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Facebook", Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Google plus", Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Blog", Toast.LENGTH_SHORT).show();
            }
        });

        imgBtnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(),"Compartir",Toast.LENGTH_SHORT).show();
            }
        });

        return view;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
