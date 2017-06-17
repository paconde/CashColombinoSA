package com.fconde.cashcolombinoapp.models;

import com.fconde.cashcolombinoapp.app.MyApplication;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by FConde on 16/06/2017.
 */

public class Pedidos extends RealmObject {

    @PrimaryKey
    private int id;
    @Required
    private String codCliente;
    @Required
    private Date fechaCreacion;
    private boolean enviado;

    private RealmList<LineasPedido> lineasPedido;

    public Pedidos(){
    }

    public Pedidos(String codCliente, boolean enviado){
        this.id = MyApplication.pedidoID.incrementAndGet();
        this.codCliente = codCliente;
        this.fechaCreacion = new Date();
        this.lineasPedido = new RealmList<LineasPedido>();
    }

    public int getId() {
        return id;
    }

    public String getCodCliente() {
        return codCliente;
    }

    public void setCodCliente(String codCliente) {
        this.codCliente = codCliente;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public RealmList<LineasPedido> getLineasPedido() {
        return lineasPedido;
    }

}
