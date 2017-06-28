package com.fconde.cashcolombinoapp.models;

import android.widget.ListView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.activities.LocalizadorActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 28/06/2017.
 */

public class CSVFile {
    InputStream inputStream;

    public CSVFile(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Catalogo> read(){
        List<Catalogo> listaResultados = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] codBarra = csvLine.split(";");
                String[] codInterno = csvLine.split(";");
                String[] articulo = csvLine.split(";");
                String[] formato = csvLine.split(";");

                Catalogo cat = new Catalogo(codBarra.toString(), codInterno.toString(), articulo.toString(), formato.toString());
                cat.setCodigoBarras(codBarra.toString());
                cat.setCodigoInterno(codInterno.toString());
                cat.setArticulo(articulo.toString());
                cat.setFormato(formato.toString());

                listaResultados.add(cat);
                //listaResultados.add(codBarra, codInterno, articulo, formato);
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
        return listaResultados;
    }
}

