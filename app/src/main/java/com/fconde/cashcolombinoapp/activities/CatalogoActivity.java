package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.fconde.cashcolombinoapp.R;


/**
 * Created by FConde on 24/05/2017.
 */

public class CatalogoActivity extends AppCompatActivity {

    ImageView imagenFidelidad, imagenOferta;
    final static String URLFIDELIDAD = "https://www.barea.com/wp-content/uploads/2014/12/MONOGRA%CC%81FICOS-10-SEPTIEMBRE-2017.pdf";
    final static String URLOFERTAMES = "https://www.barea.com/wp-content/uploads/2014/12/OFERTA-CASH-JUNIO-2017-SIN-PRECIOS.pdf";
    //String urlString;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        final Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_catalogo);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        setSupportActionBar(toolbar);

        imagenFidelidad = (ImageView)findViewById(R.id.imagen_fidelidad);
        imagenOferta = (ImageView)findViewById(R.id.imagen_oferta_mes);

        imagenFidelidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargaPdf(URLFIDELIDAD);
            }
        });

        imagenOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargaPdf(URLOFERTAMES);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_catalogo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu){

        int id = option_menu.getItemId();
        switch (id){
            case R.id.fidelidad:
                descargaPdf(URLFIDELIDAD);
                break;
            case R.id.oferta_mes:
                descargaPdf(URLOFERTAMES);
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }
    public void descargaPdf(String urlString) {
        Uri uri = Uri.parse(urlString);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
