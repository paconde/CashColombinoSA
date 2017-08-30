package com.fconde.cashcolombinoapp.models;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 28/06/2017.
 */

public class CSVFileCat {
    InputStream inputStream;

    public CSVFileCat(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Catalogo> read(){
        List<Catalogo> catalogo = new ArrayList<>();
        //BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("ISO-8859-1")));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] tokens = csvLine.split(";");

                Catalogo cat = new Catalogo();
                cat.setCodigoBarras(tokens[0]);
                cat.setCodigoInterno(tokens[1]);
                cat.setArticulo(tokens[2]);
                cat.setFormato(tokens[3]);

                catalogo.add(cat);

                //Log.d("CSV FILE", " Creado: " + cat);
            }
        }
        catch (IOException ex) {
            throw new RuntimeException("Error leyendo fichero CSV: "+ex);
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                throw new RuntimeException("Error cerrando input stream: "+e);
            }
        }
        return catalogo;
    }
}

