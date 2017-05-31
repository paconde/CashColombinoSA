package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by FConde on 24/05/2017.
 */

public class CatalogoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ImageView imagenFidelidad, imagenOferta;
    String urlString;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_catalogo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);

        imagenFidelidad = (ImageView)findViewById(R.id.imagen_fidelidad);
        imagenOferta = (ImageView)findViewById(R.id.imagen_oferta_mes);

        imagenFidelidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = "https://www.barea.com/wp-content/uploads/2014/12/MONOGRA%C2%B4FICOS-10-MAYO-2017.pdf";
                descargaPdf(urlString);

            }
        });

        imagenOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = "https://www.barea.com/wp-content/uploads/2014/12/OFERTA-CASH-MAYO-2017-SIN-PRECIOS.pdf";
                descargaPdf(urlString);
            }
        });
    }

    public void descargaPdf(String urlString) {
        Uri uri = Uri.parse(urlString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
