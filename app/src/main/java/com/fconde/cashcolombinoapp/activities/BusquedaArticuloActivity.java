package com.fconde.cashcolombinoapp.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorBusquedaArticulo;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.Comunicador;

import java.util.ArrayList;

public class BusquedaArticuloActivity extends AppCompatActivity {

    private ListView listViewBusquedaArticulo;
    private ArrayList<Catalogo> resultados = Comunicador.getResultados();
    private int resultPosition = 9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda_articulo);

        listViewBusquedaArticulo = (ListView)findViewById(R.id.listViewBusquedaArticulo);

        AdaptadorBusquedaArticulo adaptadorBusquedaArticulo = new AdaptadorBusquedaArticulo(this, R.layout.list_view_busqueda_articulos, resultados);
        listViewBusquedaArticulo.setAdapter(adaptadorBusquedaArticulo);

        listViewBusquedaArticulo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Comunicador.setResultadoFinal(position);
                Comunicador.setArticuloEncontrado(true);
                resultPosition = position;
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("position", resultPosition);
        setResult(RESULT_OK, data);
        super.finish();
    }
}
