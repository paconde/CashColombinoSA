package com.fconde.cashcolombinoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
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
import com.fconde.cashcolombinoapp.adapters.AdaptadorRecetas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 24/05/2017.
 */

public class RecetasActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences prefs;
    private String pagina;
    private String vista;

    private List<Recetas> receta;
    private RecyclerView myRecyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager myLayoutManager;
    public String[] recetas;                                // Nombre de las recetas
    public String[] recetas_url;                            // URL de las recetas
    public String[] recetas_imagen_url;                     // URL de las imagenes de las recetas

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        pagina = getPaginaPrefs();
        vista = getVistaPrefs();

        updateToolbar(pagina);

        receta = this.getAllRecetas(pagina);

        if(!isOnLine()){
            Toast.makeText(getApplicationContext(), "No hay conexión de datos.", Toast.LENGTH_SHORT).show();
        }

        myRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        myLayoutManager = new LinearLayoutManager(this);

        updateAdapter();
    }

    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo !=null && networkInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        vista = getVistaPrefs();
        if(vista.equals("lista")){
            getMenuInflater().inflate(R.menu.menu_recetas_lista, menu);
            return true;
        }else{
            getMenuInflater().inflate(R.menu.menu_recetas_vistas, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu){

        int id = option_menu.getItemId();
        switch (id){
            case R.id.icon_recetas_lista:
                vista = "lista";
                updateVistaPrefs(vista);
                Intent intent = new Intent(this, RecetasActivity.class);
                finish();
                startActivity(intent);
                break;
            case R.id.icon_recetas_vistas:
                vista = "imagenes";
                updateVistaPrefs(vista);
                Intent intent2 = new Intent(this, RecetasActivity.class);
                finish();
                startActivity(intent2);
                break;
            case R.id.pagina1:
                pagina = "1";
                updatePaginaPrefs(pagina);
                updateToolbar(pagina);
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina2:
                pagina = "2";
                updatePaginaPrefs(pagina);
                updateToolbar(pagina);
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina3:
                pagina = "3";
                updatePaginaPrefs(pagina);
                updateToolbar(pagina);
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina4:
                pagina = "4";
                updatePaginaPrefs(pagina);
                updateToolbar(pagina);
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            case R.id.pagina5:
                pagina = "5";
                updatePaginaPrefs(pagina);
                updateToolbar(pagina);
                receta = this.getAllRecetas(pagina);
                updateAdapter();
                myAdapter.notifyDataSetChanged();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }

    private List<Recetas> getAllRecetas(String pagina){
        ArrayList<Recetas> recetasArrayList = new ArrayList<>();

        switch (pagina){
            case "1":
                recetas = getResources().getStringArray(R.array.recetas_p1);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p1);
                recetas_imagen_url = getResources().getStringArray(R.array.recetas_imagen_url_p1);
                break;
            case "2":
                recetas = getResources().getStringArray(R.array.recetas_p2);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p2);
                recetas_imagen_url = getResources().getStringArray(R.array.recetas_imagen_url_p2);
                break;
            case "3":
                recetas = getResources().getStringArray(R.array.recetas_p3);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p3);
                recetas_imagen_url = getResources().getStringArray(R.array.recetas_imagen_url_p3);
                break;
            case "4":
                recetas = getResources().getStringArray(R.array.recetas_p4);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p4);
                recetas_imagen_url = getResources().getStringArray(R.array.recetas_imagen_url_p4);
                break;
            case  "5":
                recetas = getResources().getStringArray(R.array.recetas_p5);
                recetas_url = getResources().getStringArray(R.array.recetas_url_p5);
                recetas_imagen_url = getResources().getStringArray(R.array.recetas_imagen_url_p5);
                break;
            default:
        }

        for(int i = 0; i < recetas.length; i++){
            //int imagen = getResources().getIdentifier("p" + Integer.valueOf(pagina) + "_" + (i + 1), "drawable", getPackageName());
            recetasArrayList.add(new Recetas(recetas[i], recetas_imagen_url[i], recetas_url[i]));
        }
        return recetasArrayList;
    }

    private void updateToolbar(String pagina){
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        //String titulo = getResources(R.string.act_name_recetas).toString();
        toolbar.setTitle("Recetas " + "Pág. " + pagina);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        setSupportActionBar(toolbar);
    }

    private void updateAdapter(){
        vista = getVistaPrefs();
        if(vista.equals("imagenes") || vista.equals("null")) {
            myAdapter = new AdaptadorRecetas(receta, R.layout.recycler_view_item_vistas, new AdaptadorRecetas.OnItemClickListener() {
                @Override
                public void onItemClick(Recetas receta, int position) {
                    goToUrl(recetas_url[position]);
                }
            });
        }else{
            myAdapter = new AdaptadorRecetas(receta, R.layout.recycler_view_item_lista, new AdaptadorRecetas.OnItemClickListener() {
                @Override

                public void onItemClick(Recetas receta, int position) {
                    goToUrl(recetas_url[position]);
                }
            });
        }

        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.setItemAnimator(new DefaultItemAnimator());

        myRecyclerView.setLayoutManager(myLayoutManager);
        myRecyclerView.setAdapter(myAdapter);
    }

    private void goToUrl(String url){
        Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intentWeb);
    }

    private String getPaginaPrefs(){
        return prefs.getString("pagina", "null");
    }

    private String getVistaPrefs(){
        return prefs.getString("vista", "null");
    }

    private void updatePaginaPrefs(String pagina){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pagina", pagina);
        editor.commit();
        editor.apply();
    }

    private void updateVistaPrefs(String vista){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("vista", vista);
        editor.commit();
        editor.apply();
    }
}
