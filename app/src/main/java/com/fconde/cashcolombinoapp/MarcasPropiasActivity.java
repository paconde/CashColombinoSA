package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by FConde on 24/05/2017.
 */

public class MarcasPropiasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ImageView imagenMarcasPropias;
    final static String URLMARCASPROPIAS = "https://www.barea.com/wp-content/uploads/2017/04/catalogo-marcas-2017.pdf";

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas_propias);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_marcas_propias);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);

        imagenMarcasPropias = (ImageView)findViewById(R.id.imagen_descarga_marcas_propias);

        imagenMarcasPropias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                descargaPdf(URLMARCASPROPIAS);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_marcas_propias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu){

        int id = option_menu.getItemId();
        switch (id){
            case R.id.texto_descargar_marcas_propias:
                descargaPdf(URLMARCASPROPIAS);
                break;

            case R.id.descargar_marcas_propias:
                descargaPdf(URLMARCASPROPIAS);
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

