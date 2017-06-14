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

public class RecetasActivityLista extends AppCompatActivity {

    private Toolbar toolbar;
    public String pagina = "1";
    private List<Recetas> receta;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    public String[] recetas;
    public String[] recetas_url;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_lista);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_recetas);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco, null));
        setSupportActionBar(toolbar);

        receta = this.getAllRecetas(pagina);

        myRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(this);
        //
        updateAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recetas_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu) {

        int id = option_menu.getItemId();
        switch (id) {
            case R.id.icon_recetas_vistas:
                Intent intent = new Intent(this, RecetasActivityVistas.class);
                finish();
                startActivity(intent);
                break;
            case R.id.pagina1:
                pagina = "1";
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina2:
                pagina = "2";
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina3:
                pagina = "3";
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina4:
                pagina = "4";
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina5:
                pagina = "5";
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }

    private List<Recetas> getAllRecetas(String pagina) {
        ArrayList<Recetas> recetasArrayList = new ArrayList<Recetas>();

        switch (pagina) {
            case "1":
                recetas = getResources().getStringArray(R.array.recetas_p1);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p1);
                break;
            case "2":
                recetas = getResources().getStringArray(R.array.recetas_p2);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p2);
                break;
            case "3":
                recetas = getResources().getStringArray(R.array.recetas_p3);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p3);
                break;
            case "4":
                recetas = getResources().getStringArray(R.array.recetas_p4);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p4);
                break;
            case "5":
                recetas = getResources().getStringArray(R.array.recetas_p5);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p5);
                break;
            default:

        }


        for (int i = 0; i < recetas.length; i++) {
            int imagen = getResources().getIdentifier("p" + Integer.valueOf(pagina) + "_" + (i + 1), "drawable", getPackageName());
            recetasArrayList.add(new Recetas(recetas[i], imagen, recetas_url[i]));
        }
        return recetasArrayList;
    }

    private void updateAdapter() {
        myAdapter = new MyAdapter(receta, R.layout.recycler_view_item_lista, new MyAdapter.OnItemClickListener() {
            @Override

            public void onItemClick(Recetas receta, int position) {
                Toast.makeText(RecetasActivityLista.this, recetas_url[position], Toast.LENGTH_SHORT).show();
            }
        });

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }
}