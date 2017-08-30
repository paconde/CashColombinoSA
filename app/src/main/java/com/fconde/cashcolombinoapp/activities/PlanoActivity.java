package com.fconde.cashcolombinoapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.fconde.cashcolombinoapp.R;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PlanoActivity extends AppCompatActivity {

    ImageView imageViewPlano;
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plano);

        imageViewPlano = (ImageView)findViewById(R.id.imageViewPlano);
        PhotoViewAttacher mAttacher = new PhotoViewAttacher(imageViewPlano);
        mAttacher.update();
    }

}
