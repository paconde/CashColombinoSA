package com.fconde.cashcolombinoapp.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by FConde on 22/08/2017.
 */

public class CSVFileUser {
    InputStream inputStream;

    public CSVFileUser(InputStream inputStream){
        this.inputStream = inputStream;
    }

    public List<Usuarios> read(){
        List<Usuarios> usuarios = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        try {
            String csvLine;
            while ((csvLine = reader.readLine()) != null) {
                String[] tokens = csvLine.split(";");

                Usuarios users = new Usuarios();
                users.setNifLogin(tokens[0]);
                users.setPassword(tokens[1]);
                users.setIsActivo(tokens[2]);

                usuarios.add(users);

                //Log.d("CSV FILE", " Creado: " + users);
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
        return usuarios;
    }
}
