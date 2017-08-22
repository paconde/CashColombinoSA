package com.fconde.cashcolombinoapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.fconde.cashcolombinoapp.models.CSVFileCat;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class LineasPedidoActivity extends AppCompatActivity implements RealmChangeListener<Pedidos>{

    private Toolbar toolbar;
    private ListView listView;
    private FloatingActionButton fab;
    private AdaptadorLineasPedido adaptadorLineasPedido;
    private RealmList<LineasPedido> lineasPedido;
    private Realm realm;
    private List<Catalogo> catalogo;

    private int pedidoID;
    private Pedidos pedido;

    static final int REQUEST_CODE = 0;

    private String codigo, articulo;
    private int cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lineas_pedido);

        realm = Realm.getDefaultInstance();

        if(getIntent().getExtras() != null){
            pedidoID = getIntent().getExtras().getInt("id");
        }

        // Cargar catalogo desde CSV
        cargaCatalogo();

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
                        break;
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LineasPedidoActivity.this, NuevaLineaActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
            }


        });
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

    public void cargaCatalogo(){
        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFileCat csvFileCatCatalogo = new CSVFileCat(inputStreamCatalogo);
        catalogo = csvFileCatCatalogo.read();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("codigo") && data.hasExtra("articulo") && data.hasExtra("cantidad")){
                codigo = data.getExtras().getString("codigo");
                articulo = data.getExtras().getString("articulo");
                cantidad = data.getExtras().getInt("cantidad");
                if(codigo != null || articulo != null || cantidad != 0){
                    crearNuevaLinea(codigo, articulo, cantidad);
                    onResume();
                }
            }
        }
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
        final TextView formato = (TextView) inflatedView.findViewById(R.id.textViewFormato);

        codigo.setText(lineaPedido.getCodArticulo());
        articulo.setText(lineaPedido.getDescArticulo());
        inputCantidad.setHint(String.valueOf(lineaPedido.getCantidad()));

        for (int i = 0; i < catalogo.size(); i++) {
            if (lineaPedido.getCodArticulo().equals(catalogo.get(i).getCodigoBarras().toString())) {
                formato.setText(catalogo.get(i).getFormato().toString() + " ud./caja");
                i = catalogo.size();
            }
        }

        builder.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!inputCantidad.getText().toString().equals("")){
                    int cantidad = Integer.valueOf(inputCantidad.getText().toString());
                    if(cantidad > 0) {
                        editLineaPedido(cantidad, lineaPedido);
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
}
