package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.CSVFile;
import com.fconde.cashcolombinoapp.models.Catalogo;

import java.io.InputStream;
import java.util.List;

import static com.fconde.cashcolombinoapp.R.raw.codigos;

/**
 * Created by FConde on 24/05/2017.
 */

public class LocalizadorActivity extends AppCompatActivity {

   private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizador);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_localizador);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco, null));
        setSupportActionBar(toolbar);

        /*InputStream inputStreamCodigos = getResources().openRawResource(R.raw.codigos);
        CSVFile csvFileCodigos = new CSVFile(inputStreamCodigos);
        List<String[]> codigos = csvFileCodigos.read();

        InputStream inputStreamArticulos = getResources().openRawResource(R.raw.articulos);
        CSVFile csvFileArticulos = new CSVFile(inputStreamArticulos);
        List<String[]> articulos = csvFileArticulos.read();*/

        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFile csvFileCatalogo = new CSVFile(inputStreamCatalogo);
        List<Catalogo> catalogo = csvFileCatalogo.read();

        Toast.makeText(this, catalogo.size() + "-"
                + catalogo.get(1).getCodigoBarras().length() + "-"
                + catalogo.get(1).getCodigoInterno().length() + "- "
                + catalogo.get(1).getArticulo().length() + "-"
                + catalogo.get(1).getFormato().length(), Toast.LENGTH_LONG).show();
    }
}
