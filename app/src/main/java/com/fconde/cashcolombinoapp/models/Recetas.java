package com.fconde.cashcolombinoapp.models;

/**
 * Created by FConde on 08/06/2017.
 */

public class Recetas {

    public String nombreReceta;
    //public int codigoImagen;
    public String urlImagenReceta;
    public String urlReceta;

    public Recetas(){
    }

    public Recetas(String nombreReceta, String urlImagenReceta, String urlReceta){
        this.nombreReceta = nombreReceta;
        this.urlImagenReceta = urlImagenReceta;
        this.urlReceta = urlReceta;
    }

    public String getNombreReceta(){

        return nombreReceta;
    }

    public String getUrlImagenReceta(){

        return urlImagenReceta;
    }

    public String getUrlReceta(){

        return urlReceta;
    }

}
