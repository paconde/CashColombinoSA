package com.fconde.cashcolombinoapp.models;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;

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
                String urlContacto = "https://www.barea.com/cash-carry/cash-colombino/";
                goToUrl(urlContacto);
            }
        });

        imgBtnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlFacebook = "https://www.facebook.com/bareagrupo";
                goToUrl(urlFacebook);
            }
        });

        imgBtnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlGooglePlus = "https://plus.google.com/+BareaCash";
                goToUrl(urlGooglePlus);
            }
        });

        imgBtnBlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlBlog = "https://www.barea.com/blog/";
                goToUrl(urlBlog);
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

    private void goToUrl(String url){
        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intentWeb);
    }
}
