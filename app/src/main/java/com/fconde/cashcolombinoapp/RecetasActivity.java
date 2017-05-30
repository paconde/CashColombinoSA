package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by FConde on 24/05/2017.
 */

public class RecetasActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_recetas);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);
    }
}
