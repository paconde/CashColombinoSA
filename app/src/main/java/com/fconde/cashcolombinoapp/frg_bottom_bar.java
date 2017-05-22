package com.fconde.cashcolombinoapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by FConde on 22/05/2017.
 */

public class frg_bottom_bar extends Fragment {

    ImageButton imgBtnContacto, imgBtnFacebook, imgBtnGoogle, imgBtnBlog, imgBtnCompartir;

    public frg_bottom_bar(){

        //imgBtnContacto = (ImageButton)find
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view;
        view = inflater.inflate(R.layout.lay_frg_bottom_bar, container);

        return view;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
