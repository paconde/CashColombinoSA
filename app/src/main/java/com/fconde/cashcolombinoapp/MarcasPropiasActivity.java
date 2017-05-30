package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by FConde on 24/05/2017.
 */

public class MarcasPropiasActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcas_propias);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_marcas_propias);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);
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
            case R.id.marcaMBarea:
                Toast.makeText(this,"Selección Manuel Barea", Toast.LENGTH_SHORT).show();
                break;
            case R.id.marcaOlibarea:
                Toast.makeText(this,"Selección Olibarea", Toast.LENGTH_SHORT).show();
                break;
            case R.id.marcaBaresol:
                Toast.makeText(this,"Selección Baresol", Toast.LENGTH_SHORT).show();
                break;
            case R.id.marcaCIberica:
                Toast.makeText(this,"Selección Cumbre Ibérica", Toast.LENGTH_SHORT).show();
                break;
            case R.id.marcaConsemur:
                Toast.makeText(this,"Selección Consemur", Toast.LENGTH_SHORT).show();
                break;
            case R.id.marcaSVerde:
                Toast.makeText(this,"Selección Simón Verde", Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }
}

