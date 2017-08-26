package com.fconde.cashcolombinoapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
//import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.Comunicador;
import com.fconde.cashcolombinoapp.models.Pedidos;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SharedPreferences prefs, prefLogin;
    ImageButton imgBtnNosotros, imgBtnCatalogo, imgBtnMarcasPropias, imgBtnLocalizador, imgBtnRecetas, imgBtnPedidos;
    //Intent intent;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        prefLogin = getSharedPreferences("Login", Context.MODE_PRIVATE);
        onCreatePrefs();

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        setSupportActionBar(toolbar);

        imgBtnNosotros = (ImageButton)findViewById(R.id.imgBtnNosotros);
        imgBtnCatalogo = (ImageButton)findViewById(R.id.imgBtnCatalogo);
        imgBtnMarcasPropias = (ImageButton)findViewById(R.id.imgBtnMarcasPropias);
        imgBtnLocalizador = (ImageButton)findViewById(R.id.imgBtnLocalizador);
        imgBtnRecetas = (ImageButton)findViewById(R.id.imgBtnRecetas);
        imgBtnPedidos = (ImageButton)findViewById(R.id.imgBtnPedidos);

        imgBtnNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NosotrosActivity.class);
                startActivity(intent);
            }
        });

        imgBtnCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CatalogoActivity.class);
                startActivity(intent);
            }
        });

        imgBtnMarcasPropias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MarcasPropiasActivity.class);
                startActivity(intent);
            }
        });

        imgBtnLocalizador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(),LocalizadorActivity.class);
                startActivity(intent);
            }
        });

        imgBtnRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecetasActivity.class);
                startActivity(intent);
            }
        });

        imgBtnPedidos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPedidos = new Intent(v.getContext(), PedidosActivity.class);
                Intent intentLogin = new Intent(v.getContext(), LoginActivity.class);

                if(!TextUtils.isEmpty(Comunicador.getLoginPreferences(prefLogin)) &&
                    !TextUtils.isEmpty(Comunicador.getPasswordPreferences(prefLogin))){
                    intentPedidos.putExtra("NIF", Comunicador.getLoginPreferences(prefLogin));
                    intentPedidos.putExtra("codCliente", Comunicador.getPasswordPreferences(prefLogin));
                    intentPedidos.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intentPedidos);
                }else{
                    startActivity(intentLogin);
                }
            }
        });
    }

    private void onCreatePrefs(){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pagina", "1");
        editor.putString("vista", "lista");
        editor.commit();
        editor.apply();
    }
}
