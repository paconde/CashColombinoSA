package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 24/05/2017.
 */

public class RecetasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public String pagina = "1";

    private List<String> names;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_recetas);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);
    }


    private List<Recetas> getAllRecetas(String pagina){
        ArrayList<Recetas> recetasArrayList = new ArrayList<Recetas>();
        String nombre = "";
        String codigo = "";
        for(int i = 1; i < 24; i++){
            String contador = String.valueOf(i);
            codigo = "R.drawable.p" + pagina + "_" + contador;
            nombre = "@string/" + codigo;
            if(codigo != null) {
                recetasArrayList.add(new Recetas(nombre, codigo));
            }
        }
        return recetasArrayList;
    }
}
