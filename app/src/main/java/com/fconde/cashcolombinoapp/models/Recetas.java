package com.fconde.cashcolombinoapp.models;

/**
 * Created by FConde on 08/06/2017.
 */

public class Recetas {

    public String nombreReceta;
    public int codigoImagen;
    public String urlReceta;

    public Recetas(){
    }

    public Recetas(String nombreReceta, int codigoImagen, String urlReceta){
        this.nombreReceta = nombreReceta;
        this.codigoImagen = codigoImagen;
        this.urlReceta = urlReceta;
    }

    public String getNombreReceta(){

        return nombreReceta;
    }

    public int getCodigoImagen(){
        return codigoImagen;
    }

    public String getUrlReceta(){

        return urlReceta;
    }

}
