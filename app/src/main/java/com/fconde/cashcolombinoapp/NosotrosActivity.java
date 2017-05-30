package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by FConde on 24/05/2017.
 */

public class NosotrosActivity extends AppCompatActivity {

    private Toolbar toolbar;
    TextView texto_nosotros;
    ImageView imagen_nosotros;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nosotros);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_nosotros);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);
        texto_nosotros = (TextView)findViewById(R.id.texto_nosotros);
        imagen_nosotros = (ImageView)findViewById(R.id.imagen_nosotros);

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
                texto_nosotros.setText(R.string.nosotros_colombino);

                //imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_colombino);
                Toast.makeText(this,"Selección Cash Colombino", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nosotrosCBarea:
                texto_nosotros.setText(R.string.nosotros_cash_barea);
                //imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                Toast.makeText(this,"Selección Cash Barea", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nosotrosMBarea:
                texto_nosotros.setText(R.string.nosotros_manuel_barea);
                //imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_barea);
                Toast.makeText(this,"Selección Manuel Barea", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nosotrosExtremadura:
                texto_nosotros.setText(R.string.nosotros_cash_extremeño);
                //imagen_nosotros.setImageResource(R.drawable.instalaciones_cash_extremeno);
                Toast.makeText(this,"Selección Cash Extremeño", Toast.LENGTH_SHORT).show();
                break;
            default:
                return true;
        }
        return super.onOptionsItemSelected(option_menu);
    }
}
