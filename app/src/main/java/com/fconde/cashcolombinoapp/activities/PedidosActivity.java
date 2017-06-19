package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorPedidos;
import com.fconde.cashcolombinoapp.adapters.MyAdapter;
import com.fconde.cashcolombinoapp.models.Pedidos;
import com.fconde.cashcolombinoapp.models.Recetas;

import java.text.SimpleDateFormat;

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
        listView.setOnItemClickListener(this);

        fabAddPedido = (FloatingActionButton) findViewById(R.id.fabAddPedido);
        fabAddPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewPedido(codCliente);
            }
        });

        registerForContextMenu(listView);
    }

    private void createNewPedido(String codCliente){
        realm.beginTransaction();
        Pedidos pedidos = new Pedidos(codCliente, false);
        realm.copyToRealm(pedidos);
        realm.commitTransaction();
    }

    private void deletePedido(Pedidos pedido){
        realm.beginTransaction();
        pedido.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAllPedidos(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pedidos, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAllPedidos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = df.format(pedidos.get(info.position).getFechaCreacion());
        menu.setHeaderTitle("Pedido: " + fecha);

        getMenuInflater().inflate(R.menu.context_menu_pedidos, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.enviarPedido:

                return true;
            case R.id.duplicarPedido:

                return true;
            case R.id.borrarPedido:
                deletePedido(pedidos.get(info.position));
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
