package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;

/**
 * Created by FConde on 24/05/2017.
 */

public class NosotrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView texto_nosotros;
    private ImageView imagen_nosotros;
    private SharedPreferences prefs;
    private ImageButton imgBtnMap;
    private String empresa;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_nosotros);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        setSupportActionBar(toolbar);
        texto_nosotros = (TextView)findViewById(R.id.texto_nosotros);
        imagen_nosotros = (ImageView)findViewById(R.id.imagen_nosotros);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        empresa = getEmpresaPrefs();

        switch (empresa){
            case "Cash Colombino":
                texto_nosotros.setText(R.string.nosotros_colombino);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_colombino);
                break;
            case "Cash Barea":
                texto_nosotros.setText(R.string.nosotros_cash_barea);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                break;
            case "Manuel Barea":
                texto_nosotros.setText(R.string.nosotros_manuel_barea);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                break;
            case "Cash Extremeño":
                texto_nosotros.setText(R.string.nosotros_cash_extremeno);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_extremeno);
                break;
            default:
                return;
        }

        /*imgBtnMap = (ImageButton)findViewById(R.id.imbBtnMaps);

        imgBtnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MapsActivity.class);
                intent.putExtra("empresa", empresa);
                startActivity(intent);
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_nosotros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem option_menu){

        int id = option_menu.getItemId();
        switch (id){
            case R.id.nosotrosColombino:
                empresa = "Cash Colombino";
                updateVistaPrefs(empresa);
                texto_nosotros.setText(R.string.nosotros_colombino);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_colombino);
                break;
            case R.id.nosotrosCBarea:
                empresa = "Cash Barea";
                updateVistaPrefs(empresa);
                texto_nosotros.setText(R.string.nosotros_cash_barea);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                break;
            case R.id.nosotrosMBarea:
                empresa = "Manuel Barea";
                updateVistaPrefs(empresa);
                texto_nosotros.setText(R.string.nosotros_manuel_barea);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                break;
            case R.id.nosotrosExtremadura:
                empresa = "Cash Extremeño";
                updateVistaPrefs(empresa);
                texto_nosotros.setText(R.string.nosotros_cash_extremeno);
                imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_extremeno);
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }

    private void updateVistaPrefs(String empresa){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("empresa", empresa);
        editor.commit();
        editor.apply();
    }

    private String getEmpresaPrefs(){
        return prefs.getString("empresa", "null");
    }
}
