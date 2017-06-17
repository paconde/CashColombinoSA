package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorPedidos;
import com.fconde.cashcolombinoapp.adapters.MyAdapter;
import com.fconde.cashcolombinoapp.models.Pedidos;
import com.fconde.cashcolombinoapp.models.Recetas;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * Created by FConde on 24/05/2017.
 */

public class PedidosActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Pedidos>>, AdapterView.OnItemClickListener{

    private Toolbar toolbar;
    private FloatingActionButton fabAddPedido;
    private Realm realm;
    private String codCliente = "123456";
    private ListView listView;
    private AdaptadorPedidos adaptadorPedidos;
    private RealmResults<Pedidos> pedidos;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedidos);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_pedidos);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);

        // DB Realm
        realm = Realm.getDefaultInstance();
        pedidos = realm.where(Pedidos.class).findAll();
        pedidos.addChangeListener(this);

        listView = (ListView)findViewById(R.id.listViewPedidos);
        adaptadorPedidos = new AdaptadorPedidos(this, pedidos, R.layout.list_view_pedidos);
        listView.setAdapter(adaptadorPedidos);

        fabAddPedido = (FloatingActionButton) findViewById(R.id.fabAddPedido);

        fabAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPedido(codCliente);
            }
        });
    }

    private void createNewPedido(String codCliente){
        realm.beginTransaction();

        Pedidos pedidos = new Pedidos(codCliente, false);

        realm.copyToRealm(pedidos);
        realm.commitTransaction();

    }

    @Override
    public void onChange(RealmResults<Pedidos> pedidoses) {
        adaptadorPedidos.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PedidosActivity.this, LineasPedidoActivity.class);
        intent.putExtra("id", pedidos.get(position).getId());
        startActivity(intent);
    }
}
