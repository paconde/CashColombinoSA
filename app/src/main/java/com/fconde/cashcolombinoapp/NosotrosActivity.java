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

public class NosotrosActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        /*Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar_nosotros);
        myToolbar.setTitle(R.string.act_name_nosotros);
        myToolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(myToolbar);*/
    }
}
