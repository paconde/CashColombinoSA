package com.fconde.cashcolombinoapp.models;

import android.telecom.Call;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 29/08/2017.
 */

public class CSVFileCalles {
    InputStream inputStream;

    public CSVFileCalles(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Calles> read(){
        List<Calles> calles = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] tokens = csvLine.split(";");

                Calles calle = new Calles();
                calle.setCodigo(tokens[0]);
                calle.setCalle(tokens[1]);


                calles.add(calle);

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
        return calles;
    }
}
