package com.fconde.cashcolombinoapp;

/**
 * Created by FConde on 08/06/2017.
 */

public class Recetas {

    public String nombreReceta;
    public int codigoImagen;

    public Recetas(){
    }

    public Recetas(String nombreReceta, int codigoImagen){
        this.nombreReceta = nombreReceta;
        this.codigoImagen = codigoImagen;
    }

    public String getNombreReceta(){

        return nombreReceta;
    }

    public int getCodigoImagen(){
        return codigoImagen;
    }

}
