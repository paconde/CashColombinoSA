package com.fconde.cashcolombinoapp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.CSVFileCalles;
import com.fconde.cashcolombinoapp.models.CSVFileCat;
import com.fconde.cashcolombinoapp.models.Calles;
import com.fconde.cashcolombinoapp.models.Catalogo;
import com.fconde.cashcolombinoapp.models.Comunicador;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by FConde on 24/05/2017.
 */

public class LocalizadorActivity extends AppCompatActivity {

    EditText inputCodigo, inputArticulo;
    ImageButton btnSearchCodigo, btnSearchDesc, btnLimpiar, btnBarcodeScan;
    TextView ubicacion;
    Button validar, verPlano;

    private List<Catalogo> catalogo;
    private List<Calles> calles;

    private String codigo, articulo, calle;
    private boolean codigoEncontrado = false;

    static final int REQUEST_CODE = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localizador);

        final Toolbar toolbar;
        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_localizador);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.blanco));
        setSupportActionBar(toolbar);

        inputCodigo = (EditText) findViewById(R.id.editTextCodigoArticulo);
        btnSearchCodigo = (ImageButton) findViewById(R.id.imageButtonCodigoSearch);
        btnBarcodeScan = (ImageButton) findViewById(R.id.imageButtonBarcodeScan);
        inputArticulo = (EditText) findViewById(R.id.editTextDescripcionArticulo);
        btnSearchDesc = (ImageButton) findViewById(R.id.imageButtonDescripcionSearch);
        ubicacion = (TextView)findViewById(R.id.textViewUbicacion);
        btnLimpiar = (ImageButton)findViewById(R.id.imageButtonLimpiar);
        validar = (Button)findViewById(R.id.btnValidar);
        verPlano = (Button)findViewById(R.id.btnVerPlano);

        // Cargar catalogo y calles desde CSV
        cargaCatalogo();
        cargaCalles();

        btnSearchCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion.setText("Ubicación");
                codigoEncontrado = false;
                if (inputCodigo.getText().toString().trim().length() > 0) {
                    for (int i = 0; i < catalogo.size(); i++) {
                        if((inputCodigo.getText().toString().trim().equals(catalogo.get(i).getCodigoBarras().toString())) || (inputCodigo.getText().toString().trim().equals(catalogo.get(i).getCodigoInterno().toString()))){
                            inputCodigo.setText(catalogo.get(i).getCodigoInterno().toString());
                            inputArticulo.setText(catalogo.get(i).getArticulo().toString());
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
                IntentIntegrator scanIntegrator = new IntentIntegrator(LocalizadorActivity.this);
                scanIntegrator.initiateScan();
            }
        });

        btnSearchDesc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // Para almacenar los resultados
                ubicacion.setText("Ubicación");
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
                    Intent intent = new Intent(LocalizadorActivity.this, BusquedaArticuloActivity.class);
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
                ubicacion.setText("Ubicación :");
            }
        });

        validar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codigoEncontrado = false;
                if(!inputCodigo.getText().toString().isEmpty() && !inputArticulo.getText().toString().isEmpty()){
                    for (int i = 0; i < calles.size(); i++) {
                        if(inputCodigo.getText().toString().equals(calles.get(i).getCodigo())){
                            calle = calles.get(i).getCalle();
                            if(calle.equals("HS0") || calle.equals("HS2")){
                                ubicacion.setText("Departamento anexo Hostelería");
                            }else{
                                if(calle.equals("DP0") || calle.equals("DP2") || calle.equals("CM2")){
                                    ubicacion.setText("Departamento anexo Pescadería");
                                }else ubicacion.setText("Ubicación calle: " + calle);
                            }

                            i = calles.size();
                            codigoEncontrado = true;
                        }
                    }

                    if(!codigoEncontrado){
                        Toast.makeText(getApplicationContext(), "Actualmente no disponemos de la ubicación de esta referencia. Es muy posible que no se trabaje.", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        verPlano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocalizadorActivity.this, PlanoActivity.class);
                startActivity(intent);
            }
        });
    }

    public void cargaCatalogo(){
        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFileCat csvFileCatCatalogo = new CSVFileCat(inputStreamCatalogo);
        catalogo = csvFileCatCatalogo.read();
    }

    public void cargaCalles(){
        InputStream inputStreamCalles = getResources().openRawResource(R.raw.calles);
        CSVFileCalles csvFileCalles = new CSVFileCalles(inputStreamCalles);
        calles = csvFileCalles.read();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            if(data.hasExtra("codigo") && data.hasExtra("articulo")){
                inputCodigo.setText(data.getExtras().getString("codigo"));
                inputArticulo.setText(data.getExtras().getString("articulo"));
            }
        }

        // se reciben datos del lector de codigos de barras.
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            for (int i = 0; i < catalogo.size(); i++) {
                if((scanContent.equals(catalogo.get(i).getCodigoBarras().toString())) || (scanContent.equals(catalogo.get(i).getCodigoInterno().toString()))){
                    inputCodigo.setText(catalogo.get(i).getCodigoInterno().toString());
                    inputArticulo.setText(catalogo.get(i).getArticulo().toString());
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
