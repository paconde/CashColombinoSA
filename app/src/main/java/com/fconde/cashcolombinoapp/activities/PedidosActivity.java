package com.fconde.cashcolombinoapp.activities;

//import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorPedidos;
import com.fconde.cashcolombinoapp.adapters.MyAdapter;
import com.fconde.cashcolombinoapp.models.CSVFile;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;
import com.fconde.cashcolombinoapp.models.Recetas;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

import static java.lang.String.valueOf;

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
    private Pedidos pedAux;
    private RealmList<LineasPedido> lineasPedido;

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
                pedAux = realm.where(Pedidos.class).equalTo("enviado", false).findFirst();
                if(pedAux == null){
                    createNewPedido(codCliente);
                }else {
                    Toast.makeText(getApplicationContext(), "HAY UN PEDIDO PENDIENTE DE ENVIO. IMPOSIBLE CREACION", Toast.LENGTH_LONG).show();
                }

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

    private void duplicarPedido(Pedidos pedidos){
        // Guardo las lineas del pedido a duplicar
        lineasPedido = pedidos.getLineasPedido();
        // Creo un nuevo pedido
        createNewPedido(pedidos.getCodCliente());
        // Me posiciono en el pedido creado
        pedAux = realm.where(Pedidos.class).equalTo("enviado", false).findFirst();
        // Guardo el ID del pedido creado
        int pedidoID = pedAux.getId();
        // Creamos las lineas para el nuevo pedido
        realm.beginTransaction();
        for(int i = 0; i < lineasPedido.size(); i ++){
            // Guardo en unas variables cada linea a crear
            String codArticulo = lineasPedido.get(i).getCodArticulo();
            String descArticulo = lineasPedido.get(i).getDescArticulo();
            int cantidad = lineasPedido.get(i).getCantidad();
            // Creo la linea nueva
            LineasPedido lineaPedido = new LineasPedido(codArticulo, descArticulo, cantidad);
            realm.copyToRealm(lineaPedido);
            pedAux.getLineasPedido().add(lineaPedido);
        }
        realm.commitTransaction();
    }

    private void editPedido(boolean enviado, Pedidos pedido){
        realm.beginTransaction();
        pedido.setEnviado(enviado);
        realm.copyToRealmOrUpdate(pedido);
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
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.enviarPedido:
                editPedido(true, pedidos.get((info.position)));
                return true;
            case R.id.duplicarPedido:
                pedAux = realm.where(Pedidos.class).equalTo("enviado", false).findFirst();
                if(pedAux == null){
                    duplicarPedido(pedidos.get(info.position));
                }else {
                    Toast.makeText(getApplicationContext(), "HAY UN PEDIDO PENDIENTE DE ENVIO. IMPOSIBLE DUPLICACION", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.borrarPedido:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("¿Estás seguro?")
                        .setCancelable(false)
                        .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deletePedido(pedidos.get(info.position));
                            }
                        })
                        .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onChange(RealmResults<Pedidos> pedidos) {
        adaptadorPedidos.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(PedidosActivity.this, LineasPedidoActivity.class);
        intent.putExtra("id", pedidos.get(position).getId());
        startActivity(intent);
    }
}
