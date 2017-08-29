package com.fconde.cashcolombinoapp.models;

/**
 * Created by FConde on 29/08/2017.
 */

public class Calles {
    private String codigo;
    private String calle;

    public Calles(String codigo, String calle){
        this.codigo = codigo;
        this.calle = calle;
    }

    public Calles(){

    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}
