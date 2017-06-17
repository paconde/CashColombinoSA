package com.fconde.cashcolombinoapp.models;

import com.fconde.cashcolombinoapp.app.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by FConde on 16/06/2017.
 */

public class LineasPedido extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String codArticulo;
    @Required
    private String descArticulo;
    private int cantidad;

    public LineasPedido(){
    }

    public LineasPedido(String codArticulo, String descArticulo, int cantidad){
        this.id = MyApplication.lineasPedidoID.incrementAndGet();
        this.codArticulo = codArticulo;
        this.descArticulo = descArticulo;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public String getCodArticulo() {
        return codArticulo;
    }

    public void setCodArticulo(String codArticulo) {
        this.codArticulo = codArticulo;
    }

    public String getDescArticulo() {
        return descArticulo;
    }

    public void setDescArticulo(String descArticulo) {
        this.descArticulo = descArticulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
