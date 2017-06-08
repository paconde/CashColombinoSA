package com.fconde.cashcolombinoapp;

/**
 * Created by FConde on 08/06/2017.
 */

public class Recetas {

    public String nombreReceta;
    public String codigoImagen;

    public Recetas(){
    }

    public Recetas(String nombreReceta, String codigoImagen){
        this.nombreReceta = nombreReceta;
        this.codigoImagen = codigoImagen;
    }

    public String getNombreReceta(){
        return nombreReceta;
    }

    public String getCodigoImagen(){
        return codigoImagen;
    }

}
