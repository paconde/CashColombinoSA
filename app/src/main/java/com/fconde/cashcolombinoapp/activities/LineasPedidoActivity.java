package com.fconde.cashcolombinoapp.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorLineasPedido;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class LineasPedidoActivity extends AppCompatActivity {

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
        lineasPedido = pedido.getLineasPedido();

        this.setTitle(pedido.getFechaCreacion().toString());

        fab = (FloatingActionButton)findViewById(R.id.fabAddLineaPedido);
        listView = (ListView)findViewById(R.id.listViewLineasPedidos);
        adaptadorLineasPedido = new AdaptadorLineasPedido(this, lineasPedido, R.layout.list_view_lineas_pedido);

        listView.setAdapter(adaptadorLineasPedido);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void showAlertNuevaLinea(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(titulo != null) builder.setTitle(titulo);
        if(mensaje != null) builder.setMessage(mensaje);

        View inflatedView = LayoutInflater.from(this).inflate(R.layout.dialogo_nueva_linea_pedido, null);

        final EditText inputCodigo = (EditText) inflatedView.findViewById(R.id.editTextCodigoArticulo);
        final EditText inputArticulo = (EditText) inflatedView.findViewById(R.id.editTextDescripcionArticulo);
        final EditText inputCantidad = (EditText) inflatedView.findViewById(R.id.editTextCantidadArticulo);

    }
}
