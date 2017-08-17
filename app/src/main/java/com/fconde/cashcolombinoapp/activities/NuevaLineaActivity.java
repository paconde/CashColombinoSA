package com.fconde.cashcolombinoapp.activities;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.CSVFile;
import com.fconde.cashcolombinoapp.models.Catalogo;

import java.io.InputStream;
import java.util.List;

public class NuevaLineaActivity extends AppCompatActivity {

    EditText inputCodigo, inputArticulo, inputCantidad;
    ImageButton btnSearchCodigo, btnSearchDesc;
    TextView textViewFormato;
    Button addLinea;

    private boolean codigoEncontrado = false;
    private List<Catalogo> catalogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_linea);

        inputCodigo = (EditText) findViewById(R.id.editTextCodigoArticulo);
        btnSearchCodigo = (ImageButton) findViewById(R.id.imageButtonCodigoSearch);
        inputArticulo = (EditText) findViewById(R.id.editTextDescripcionArticulo);
        btnSearchDesc = (ImageButton) findViewById(R.id.imageButtonDescripcionSearch);
        inputCantidad = (EditText) findViewById(R.id.editTextCantidadArticulo);
        textViewFormato = (TextView) findViewById(R.id.textViewFormato);
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

        addLinea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!inputCantidad.getText().toString().equals("")){
                    String codigo = inputCodigo.getText().toString().trim();
                    String articulo = inputArticulo.getText().toString();
                    int cantidad = Integer.valueOf(inputCantidad.getText().toString());
                    if(codigo.length() > 0 && articulo.length() > 0 && cantidad > 0)
                        Toast.makeText(getApplicationContext(), "Linea creada", Toast.LENGTH_SHORT).show();
                        //crearNuevaLinea(codigo, articulo, cantidad);
                    else
                        Toast.makeText(getApplicationContext(), "Datos incompletos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        /*addLinea.setOnClickListener(new View.OnClickListener()  {
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
        });*/
    }
    public void cargaCatalogo(){
        InputStream inputStreamCatalogo = getResources().openRawResource(R.raw.catalogo);
        CSVFile csvFileCatalogo = new CSVFile(inputStreamCatalogo);
        catalogo = csvFileCatalogo.read();

    }
}
