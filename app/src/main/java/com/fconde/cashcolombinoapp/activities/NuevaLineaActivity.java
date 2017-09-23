package com.fconde.cashcolombinoapp.activities;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.CSVFileCat;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.Comunicador;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NuevaLineaActivity extends AppCompatActivity {

    EditText inputCodigo, inputArticulo, inputCantidad;
    ImageButton btnSearchCodigo, btnSearchDesc, btnLimpiar, btnBarcodeScan;
    TextView textViewFormato;
    Button addLinea;

    private Toolbar toolbar;
    private boolean codigoEncontrado = false;
    private List<Catalogo> catalogo;

    private String codigo, articulo;
    private int cantidad;

    static final int REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_linea);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        toolbar.setTitle("Selección de artículos");
        setSupportActionBar(toolbar);

        inputCodigo = (EditText) findViewById(R.id.editTextCodigoArticulo);
        btnSearchCodigo = (ImageButton) findViewById(R.id.imageButtonCodigoSearch);
        btnBarcodeScan = (ImageButton) findViewById(R.id.imageButtonBarcodeScan);
        inputArticulo = (EditText) findViewById(R.id.editTextDescripcionArticulo);
        btnSearchDesc = (ImageButton) findViewById(R.id.imageButtonDescripcionSearch);
        inputCantidad = (EditText) findViewById(R.id.editTextCantidadArticulo);
        textViewFormato = (TextView) findViewById(R.id.textViewFormato);
        btnLimpiar = (ImageButton)findViewById(R.id.imageButtonLimpiar);
        addLinea = (Button)findViewById(R.id.btnValidar);

        // Cargar catalogo desde CSV
        cargaCatalogo();

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

        btnBarcodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator scanIntegrator = new IntentIntegrator(NuevaLineaActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        btnSearchDesc.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v) {
                 // Para almacenar los resultados
                 boolean resultados = false;
                 ArrayList<Catalogo> catalogoResultados = new ArrayList<Catalogo>();
                 codigoEncontrado = false;
                 if (inputArticulo.getText().toString().trim().length() > 0) {
                     //String[] palabras = inputArticulo.getText().toString().toUpperCase().trim().replace(" ", "").split(",");
                     String[] palabras = inputArticulo.getText().toString().toUpperCase().split(" ");
                     // recorro catalogo en busca del articulo
                     for (int i = 0; i < catalogo.size(); i++) {
                         //recorro todas las palabras a buscar
                         for(int j = 0; j < palabras.length; j++){
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
                     Comunicador.setResultados(catalogoResultados);
                     Intent intent = new Intent(NuevaLineaActivity.this, BusquedaArticuloActivity.class);
                     startActivityForResult(intent, REQUEST_CODE);
                 }
             }
         });

        btnLimpiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputCodigo.setText("");
                inputCodigo.setHint("Cód.de barras o cod.Barea");
                inputArticulo.setText("");
                inputArticulo.setHint("Nombre Artículo: aaa bbb ccc");
                inputCantidad.setText("");
                inputCantidad.setHint("Cantidad pedida");
                textViewFormato.setText("Und./caja");
            }
        });

        addLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputCantidad.getText().toString().equals("")){
                    codigo = inputCodigo.getText().toString().trim();
                    articulo = inputArticulo.getText().toString();
                    cantidad = Integer.valueOf(inputCantidad.getText().toString());
                    if(codigo.length() > 0 && articulo.length() > 0 && cantidad > 0){
                        finish();
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void cargaCatalogo(){
        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFileCat csvFileCatCatalogo = new CSVFileCat(inputStreamCatalogo);
        catalogo = csvFileCatCatalogo.read();
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra("codigo", codigo);
        data.putExtra("articulo", articulo);
        data.putExtra("cantidad", cantidad);
        setResult(RESULT_OK, data);
        super.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("codigo") && data.hasExtra("articulo")){
                inputCodigo.setText(data.getExtras().getString("codigo"));
                inputArticulo.setText(data.getExtras().getString("articulo"));
                textViewFormato.setText(data.getExtras().getString("formato") + " ud./caja");
            }
        }

        // se reciben datos del lector de codigos de barras.
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null && data != null) {
            String scanContent = scanningResult.getContents();
            for (int i = 0; i < catalogo.size(); i++) {
                if((scanContent.equals(catalogo.get(i).getCodigoBarras().toString())) || (scanContent.equals(catalogo.get(i).getCodigoInterno().toString()))){
                    inputCodigo.setText(catalogo.get(i).getCodigoInterno().toString());
                    inputArticulo.setText(catalogo.get(i).getArticulo().toString());
                    textViewFormato.setText(catalogo.get(i).getFormato().toString() + " ud./caja");
                    codigoEncontrado = true;
                    return;
                }
            }
            if(!codigoEncontrado) Toast.makeText(getApplicationContext(), "Códgo no localizado, pruebe con otro código", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(), "No se reciben datos", Toast.LENGTH_SHORT).show();
        }
    }
}
