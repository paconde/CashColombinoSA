package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.Recetas;
import com.fconde.cashcolombinoapp.adapters.MyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 24/05/2017.
 */

public class RecetasActivityVistas extends AppCompatActivity {

    private Toolbar toolbar;
    public String pagina = "1";
    private List<Recetas> receta;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_vistas);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_recetas);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);

        receta = this.getAllRecetas(pagina);

        myRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(this);
        //
        myAdapter = new MyAdapter(receta, R.layout.recycler_view_item_vistas, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Recetas receta, int position) {
                Toast.makeText(RecetasActivityVistas.this, receta + " - " + position, Toast.LENGTH_SHORT).show();
            }
        });

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_recetas_vistas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu){

        int id = option_menu.getItemId();
        switch (id){
            case R.id.icon_recetas_lista:
                Toast.makeText(this, "Vista Lista activada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, RecetasActivityLista.class);
                startActivity(intent);
                break;
            case R.id.pagina1:
                pagina = "1";
                break;
            case R.id.pagina2:
                pagina = "2";
                break;
            case R.id.pagina3:
                pagina = "3";
                break;
            case R.id.pagina4:
                pagina = "4";
                break;
            case R.id.pagina5:
                pagina = "5";
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }

    private List<Recetas> getAllRecetas(String pagina){
        ArrayList<Recetas> recetasArrayList = new ArrayList<Recetas>();
        final String[] recetas_p1 = getResources().getStringArray(R.array.recetas_p1);

        for(int i = 1; i < 4; i++){
            int imagen = getResources().getIdentifier("p" + Integer.valueOf(pagina) + "_" + i, "drawable", getPackageName());
            recetasArrayList.add(new Recetas(recetas_p1[i -1], imagen));
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
