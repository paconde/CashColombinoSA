package com.fconde.cashcolombinoapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorLineasPedido;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;

import java.text.SimpleDateFormat;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LineasPedidoActivity extends AppCompatActivity implements RealmChangeListener<Pedidos>{

    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton fab;
    private AdaptadorLineasPedido adaptadorLineasPedido;
    private RealmList<LineasPedido> lineasPedido;
    private Realm realm;

    private int pedidoID;
    private Pedidos pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineas_pedido);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            pedidoID = getIntent().getExtras().getInt("id");
        }

        pedido = realm.where(Pedidos.class).equalTo("id", pedidoID).findFirst();
        pedido.addChangeListener(this);
        lineasPedido = pedido.getLineasPedido();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = df.format(pedido.getFechaCreacion());

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        toolbar.setTitle("Pedido: " + fecha);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton)findViewById(R.id.fabAddLineaPedido);
        listView = (ListView)findViewById(R.id.listViewLineasPedidos);
        adaptadorLineasPedido = new AdaptadorLineasPedido(this, lineasPedido, R.layout.list_view_lineas_pedido);

        listView.setAdapter(adaptadorLineasPedido);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                int viewId = view.getId();
                switch (viewId){
                    case R.id.imgBtnEditarLinea:
                        if(pedido.isEnviado()){
                            Toast.makeText(view.getContext(), "PEDIDO ENVIADO. NO SE PUEDE EDITAR.", Toast.LENGTH_LONG).show();
                            break;
                        }else{
                            showAlertEditarLinea("Editar Línea", "Modifique la cantidad para este artículo", lineasPedido.get(position));
                        }
                        break;
                    case R.id.imgBtnBorrarLinea:
                        if(pedido.isEnviado()){
                            Toast.makeText(view.getContext(), "PEDIDO ENVIADO. NO SE PUEDE EDITAR.", Toast.LENGTH_LONG).show();
                            break;
                        }else {
                            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                            alertDialogBuilder.setMessage("¿Estás seguro?")
                                    .setCancelable(false)
                                    .setPositiveButton("SI", new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            deleteLineaPedido(lineasPedido.get(position));
                                        }
                                    })
                                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });

                            AlertDialog alertDialog = alertDialogBuilder.create();
                            alertDialog.show();
                        }
                        break;
                    default:
                        //Toast.makeText(view.getContext(),"Item lista: "+ position,Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertNuevaLinea("Nueva Línea", "Seleccione un artículo para añadir al pedido");
            }
        });

        //registerForContextMenu(listView);
    }

    private void crearNuevaLinea(String codigo, String articulo, int cantidad) {
        realm.beginTransaction();
        LineasPedido lineaPedido = new LineasPedido(codigo, articulo, cantidad);
        realm.copyToRealm(lineaPedido);
        pedido.getLineasPedido().add(lineaPedido);
        realm.commitTransaction();
    }

    private void editLineaPedido(int newCantidad, LineasPedido lineapedido){
        realm.beginTransaction();
        lineapedido.setCantidad(newCantidad);
        realm.copyToRealmOrUpdate(lineapedido);
        realm.commitTransaction();
    }

    private void deleteLineaPedido(LineasPedido lineaPedido){
        realm.beginTransaction();
        lineaPedido.deleteFromRealm();
        realm.commitTransaction();
    }

    private void deleteAllLineasPedidos(){
        realm.beginTransaction();
        pedido.getLineasPedido().deleteAllFromRealm();
        realm.commitTransaction();
    }
    private void showAlertNuevaLinea(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(titulo != null) builder.setTitle(titulo);
        if(mensaje != null) builder.setMessage(mensaje);

        View inflatedView = LayoutInflater.from(this).inflate(R.layout.dialogo_nueva_linea_pedido, null);
        builder.setView(inflatedView);

        final EditText inputCodigo = (EditText) inflatedView.findViewById(R.id.editTextCodigoArticulo);
        final EditText inputArticulo = (EditText) inflatedView.findViewById(R.id.editTextDescripcionArticulo);
        final EditText inputCantidad = (EditText) inflatedView.findViewById(R.id.editTextCantidadArticulo);

        builder.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String codigo = inputCodigo.getText().toString().trim();
                String articulo = inputArticulo.getText().toString();
                int cantidad = Integer.valueOf(inputCantidad.getText().toString());
                if(codigo.length() > 0 && articulo.length() > 0 && cantidad > 0)
                    crearNuevaLinea(codigo, articulo, cantidad);
                else
                    Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAlertEditarLinea(String titulo, String mensaje, final LineasPedido lineaPedido){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(titulo != null) builder.setTitle(titulo);
        if(mensaje != null) builder.setMessage(mensaje);

        View inflatedView = LayoutInflater.from(this).inflate(R.layout.dialogo_edicion_linea_pedido, null);
        builder.setView(inflatedView);

        final TextView codigo = (TextView) inflatedView.findViewById(R.id.textViewCodigoArticulo);
        final TextView articulo = (TextView) inflatedView.findViewById(R.id.textViewDescripcionArticulo);
        final EditText inputCantidad = (EditText) inflatedView.findViewById(R.id.editTextCantidadArticulo);

        codigo.setText(lineaPedido.getCodArticulo());
        articulo.setText(lineaPedido.getDescArticulo());
        inputCantidad.setHint(String.valueOf(lineaPedido.getCantidad()));

        builder.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!inputCantidad.getText().toString().equals("")){
                    int cantidad = Integer.valueOf(inputCantidad.getText().toString());
                    if(cantidad > 0) {
                        editLineaPedido(cantidad, lineaPedido);
                        //lineasPedido = pedido.getLineasPedido();
                        onChange(pedido);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "La cantidad no puede ser 0", Toast.LENGTH_SHORT).show();
                }

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onChange(Pedidos pedidos) {
        adaptadorLineasPedido.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lineas_pedido, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAllLineasPedidos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        getMenuInflater().inflate(R.menu.context_menu_lineas_pedido, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.editarLineaPedido:
                if(pedido.isEnviado()){
                    Toast.makeText(this, "PEDIDO ENVIADO. NO SE PUEDE EDITAR.", Toast.LENGTH_LONG).show();
                    break;
                }else{
                    showAlertEditarLinea("Editar Línea", "Modifique la cantidad para este artículo", lineasPedido.get(info.position));
                }
                return true;
            case R.id.borrarLineaPedido:
                if(pedido.isEnviado()){
                    Toast.makeText(this, "PEDIDO ENVIADO. NO SE PUEDE EDITAR.", Toast.LENGTH_LONG).show();
                    break;
                }else {
                    deleteLineaPedido(lineasPedido.get(info.position));
                }
                return true;
            default:

        }
        return super.onContextItemSelected(item);
    }
*/

}
