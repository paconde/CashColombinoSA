package com.fconde.cashcolombinoapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorBusquedaArticulo;
import com.fconde.cashcolombinoapp.models.Catalogo;

import java.util.ArrayList;

public class BusquedaArticuloActivity extends AppCompatActivity {

    private ListView listViewBusquedaArticulo;
    private ArrayList<Catalogo> resultados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_articulo);

        listViewBusquedaArticulo = (ListView)findViewById(R.id.listViewBusquedaArticulo);

        /*
        if(getIntent().getExtras() != null){
            resultados = getIntent().getExtras().putStringArrayList(););
        }
        */

        AdaptadorBusquedaArticulo adaptadorBusquedaArticulo = new AdaptadorBusquedaArticulo(this, R.layout.list_view_busqueda_articulos, resultados);
        listViewBusquedaArticulo.setAdapter(adaptadorBusquedaArticulo);
    }
}
