package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 24/05/2017.
 */

public class RecetasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public String pagina = "1";

    private List<Recetas> receta;
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

        receta = this.getAllRecetas(pagina);

        myRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(this);
        myAdapter = new MyAdapter(receta, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recetas receta, int position) {
                Toast.makeText(RecetasActivity.this, receta + " - " + position, Toast.LENGTH_SHORT).show();
            }
        });

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }


    private List<Recetas> getAllRecetas(String pagina){
        ArrayList<Recetas> recetasArrayList = new ArrayList<Recetas>();
        //String codigoImagen;
        final String[] recetas_p1 = getResources().getStringArray(R.array.recetas_p1);

        for(int i = 0; i < 3; i++){
            //codigoImagen = "R.drawable.";
            //int imagen = getResources(R.drawable.p1_+i);
            recetasArrayList.add(new Recetas(recetas_p1[i], ("R.drawable.p1_" + Integer.toString(i))));
        }
        return recetasArrayList;
    }

 /*   private List<Recetas> getAllRecetas(String pagina){
        //final String nombreReceta = "R.string/p1_1";
        //final int codImagen = getResources().getIdentifier(R.drawable.p1_1);
        //final int imagenReceta = getResources(@R.drawable.p1_1);
        final String[] recetas_p1 = getResources().getStringArray(R.array.recetas_p1);

        return new ArrayList<Recetas>(){{
            add(new Recetas(recetas_p1[5], R.drawable.p1_1));
            add(new Recetas(getString(R.string.p1_2), R.drawable.p1_2));
            add(new Recetas(getString(R.string.p1_3), R.drawable.p1_3));
        }};
    }*/

/*
    private List<String> getAllNames(){
        return new ArrayList<String>(){{
                add("Antonio");
                add("Alejandro");
                add("Javier");
                add("Francisco");
            }};
    }*/
}
