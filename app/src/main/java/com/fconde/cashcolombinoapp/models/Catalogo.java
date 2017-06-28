package com.fconde.cashcolombinoapp.models;

/**
 * Created by FConde on 28/06/2017.
 */

public class Catalogo {

    private String codigoBarras;
    private String codigoInterno;
    private String articulo;
    private String formato;

    public Catalogo(String codigoBarras, String codigoInterno, String articulo, String formato){
        this.codigoBarras = codigoBarras;
        this.codigoInterno = codigoInterno;
        this.articulo = articulo;
        this.formato = formato;
    }


    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCodigoInterno() {
        return codigoInterno;
    }

    public void setCodigoInterno(String codigoInterno) {
        this.codigoInterno = codigoInterno;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
