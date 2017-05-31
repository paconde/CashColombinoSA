package com.fconde.cashcolombinoapp;

//import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by FConde on 24/05/2017.
 */

public class CatalogoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ImageView imagenFidelidad, imagenOferta;
    String urlString;

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        toolbar = (Toolbar) findViewById(R.id.tb_main);
        toolbar.setTitle(R.string.act_name_catalogo);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blanco,null));
        setSupportActionBar(toolbar);

        imagenFidelidad = (ImageView)findViewById(R.id.imagen_fidelidad);
        imagenOferta = (ImageView)findViewById(R.id.imagen_oferta_mes);

        imagenFidelidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = "https://www.barea.com/wp-content/uploads/2014/12/MONOGRA%C2%B4FICOS-10-MAYO-2017.pdf";
                //descargaPdf(urlString);
                Uri uri = Uri.parse(urlString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        imagenOferta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urlString = "https://www.barea.com/wp-content/uploads/2014/12/OFERTA-CASH-MAYO-2017-SIN-PRECIOS.pdf";
                descargaPdf(urlString);
            }
        });
    }

    public void descargaPdf(String urlString){
        try{
        //primero especificaremos el origen de nuestro archivo a descargar utilizando
        //la ruta completa
        URL url = new URL(urlString);

        //establecemos la conexión con el destino
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        //establecemos el método jet para nuestra conexión
        //el método setdooutput es necesario para este tipo de conexiones
        urlConnection.setRequestMethod("GET");
        urlConnection.setDoOutput(true);

        //por último establecemos nuestra conexión y cruzamos los dedos <img draggable="false" class="emoji" alt="??" src="https://s.w.org/images/core/emoji/72x72/1f61b.png">
        urlConnection.connect();

        //vamos a establecer la ruta de destino para nuestra descarga
        //para hacerlo sencillo en este ejemplo he decidido descargar en
        //la raíz de la tarjeta SD
        File SDCardRoot = Environment.getExternalStorageDirectory();

        //vamos a crear un objeto del tipo de fichero
        //donde descargaremos nuestro fichero, debemos darle el nombre que
        //queramos, si quisieramos hacer esto mas completo
        //cogeríamos el nombre del origen
        File file = new File(SDCardRoot,"ejemplo.txt");

        //utilizaremos un objeto del tipo fileoutputstream
        //para escribir el archivo que descargamos en el nuevo
        FileOutputStream fileOutput = new FileOutputStream(file);

        //leemos los datos desde la url
        InputStream inputStream = urlConnection.getInputStream();

        //obtendremos el tamaño del archivo y lo asociaremos a una
        //variable de tipo entero
        int totalSize = urlConnection.getContentLength();
        int downloadedSize = 0;

        //creamos un buffer y una variable para ir almacenando el
        //tamaño temporal de este
        byte[] buffer = new byte[1024];
        int bufferLength = 0;

        //ahora iremos recorriendo el buffer para escribir el archivo de destino
        //siempre teniendo constancia de la cantidad descargada y el total del tamaño
        //con esto podremos crear una barra de progreso
        while ( (bufferLength = inputStream.read(buffer)) > 0 ) {

            fileOutput.write(buffer, 0, bufferLength);
            downloadedSize += bufferLength;
            //podríamos utilizar una función para ir actualizando el progreso de lo
            //descargado
            //actualizaProgreso(downloadedSize, totalSize);

        }
        //cerramos
        fileOutput.close();

    //y gestionamos errores
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }


    }



}
