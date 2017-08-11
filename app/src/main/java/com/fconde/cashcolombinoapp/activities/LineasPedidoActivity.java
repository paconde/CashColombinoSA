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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.adapters.AdaptadorLineasPedido;
import com.fconde.cashcolombinoapp.models.CSVFile;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

public class LineasPedidoActivity extends AppCompatActivity implements RealmChangeListener<Pedidos>{

    private Toolbar toolbar;
    private ListView listView, listViewBusquedaArticulo;
    private FloatingActionButton fab;
    private AdaptadorLineasPedido adaptadorLineasPedido;
    //private ArrayAdapter<String> adaptadorBusquedaArticulo;
    private RealmList<LineasPedido> lineasPedido;
    private Realm realm;
    private List<Catalogo> catalogo;
    private boolean codigoEncontrado = false;

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

    public void cargaCatalogo(){
        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFile csvFileCatalogo = new CSVFile(inputStreamCatalogo);
        catalogo = csvFileCatalogo.read();

    }

    private void showAlertNuevaLinea(String titulo, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if(titulo != null) builder.setTitle(titulo);
        if(mensaje != null) builder.setMessage(mensaje);

        View inflatedView = LayoutInflater.from(this).inflate(R.layout.dialogo_nueva_linea_pedido, null);
        builder.setView(inflatedView);

        final EditText inputCodigo = (EditText) inflatedView.findViewById(R.id.editTextCodigoArticulo);
        final ImageButton btnSearchCodigo = (ImageButton) inflatedView.findViewById(R.id.imageButtonCodigoSearch);
        final EditText inputArticulo = (EditText) inflatedView.findViewById(R.id.editTextDescripcionArticulo);
        final ImageButton btnSearchDesc = (ImageButton) inflatedView.findViewById(R.id.imageButtonDescripcionSearch);
        final EditText inputCantidad = (EditText) inflatedView.findViewById(R.id.editTextCantidadArticulo);
        final TextView textViewFormato = (TextView) inflatedView.findViewById(R.id.textViewFormato);

        btnSearchCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoEncontrado = false;
                if (inputCodigo.getText().toString().trim().length() > 0) {
                    for (int i = 0; i < catalogo.size(); i++) {
                        if((inputCodigo.getText().toString().trim().equals(catalogo.get(i).getCodigoBarras().toString())) || (inputCodigo.getText().toString().trim().equals(catalogo.get(i).getCodigoInterno().toString()))){
                            inputCodigo.setText(catalogo.get(i).getCodigoInterno().toString());
                            inputArticulo.setText(catalogo.get(i).getArticulo().toString());
                            textViewFormato.setText(catalogo.get(i).getFormato().toString() + " ud./caja");
                            codigoEncontrado = true;
                            return;
                        }
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Introduzca un código correcto", Toast.LENGTH_LONG).show();
                }

                if(!codigoEncontrado) Toast.makeText(getApplicationContext(), "Códgo no localizado, pruebe con otro código", Toast.LENGTH_LONG).show();
            }
        });

        btnSearchDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Para almacenar los resultados
                boolean resultados = false;
                ArrayList<Catalogo> catalogoResultados = new ArrayList<Catalogo>();
                codigoEncontrado = false;
                if (inputArticulo.getText().toString().trim().length() > 0) {
                    String[] palabras = inputArticulo.getText().toString().toUpperCase().trim().replace(" ", "").split(",");

                    // recorro catalogo en busca del articulo
                    for (int i = 0; i < catalogo.size(); i++) {

                        //recorro todas las palabras a buscar
                        for(int j = 0; j < palabras.length; j++){
                            //Toast.makeText(getApplicationContext(), palabras[j].toString(), Toast.LENGTH_LONG).show();
                            if(catalogo.get(i).getArticulo().contains(palabras[j])){
                                codigoEncontrado = true;
                            }else {
                                codigoEncontrado = false;
                                j = palabras.length;
                            }
                        }

                        if (codigoEncontrado){
                            resultados = true;
                            boolean repetido = false;
                            // compruebo si el articulo encontrado ya está guardado el el arraylist
                            if(!catalogoResultados.isEmpty()){
                                for(int n = 0; n < catalogoResultados.size(); n++){
                                    if(catalogoResultados.get(n).getCodigoInterno().equals(catalogo.get(i).getCodigoInterno())){
                                        repetido = true;
                                        n = catalogoResultados.size();
                                    }else {
                                        repetido = false;
                                    }
                                }
                                if(!repetido){
                                    catalogoResultados.add(catalogo.get(i));
                                }
                            }else{
                                catalogoResultados.add(catalogo.get(i));
                                //Toast.makeText(getApplicationContext(), catalogoResultados.get(0).getArticulo().toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Introduzca una cadena de texto", Toast.LENGTH_LONG).show();
                }

                if(!resultados){
                    Toast.makeText(getApplicationContext(), "Artículo no localizado, pruebe con otro texto", Toast.LENGTH_LONG).show();
                }else{
                    // Mostramos los resultados en pantalla
                    Toast.makeText(getApplicationContext(), catalogoResultados.get(20).getArticulo(), Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(LineasPedidoActivity.this, BusquedaArticuloActivity.class);
                    //intent.putExtras("resultados",catalogoResultados);
                    intent.putExtra("resultados", catalogoResultados);
                    startActivity(intent);
                    /*
                    listViewBusquedaArticulo = (ListView)findViewById(R.id.listViewBusquedaArticulos);
                    adaptadorBusquedaArticulo = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, catalogoResultados.get().getArticulo().toString());
                    listViewBusquedaArticulo.setAdapter(adaptadorBusquedaArticulo);
                    */

                }
            }
        });

        builder.setPositiveButton("Validar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!inputCantidad.getText().toString().equals("")){
                    String codigo = inputCodigo.getText().toString().trim();
                    String articulo = inputArticulo.getText().toString();
                    int cantidad = Integer.valueOf(inputCantidad.getText().toString());
                    if(codigo.length() > 0 && articulo.length() > 0 && cantidad > 0)
                        crearNuevaLinea(codigo, articulo, cantidad);
                    else
                        Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
                }
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
        Toast.makeText(this, codigo.getText().toString(), Toast.LENGTH_LONG).show();

        //Toast.makeText(this, articulo.getText().toString() + inputCantidad.getText().toString() + formato.getText().toString(), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, formato.getText().toString(), Toast.LENGTH_LONG).show();

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
