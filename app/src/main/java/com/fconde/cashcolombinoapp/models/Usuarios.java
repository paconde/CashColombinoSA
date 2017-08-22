package com.fconde.cashcolombinoapp.models;

/**
 * Created by FConde on 22/08/2017.
 */

public class Usuarios {

    private String nifLogin;
    private String password;
    private String isActivo;

    public Usuarios(String nifLogin, String password, String isActivo){
        this.nifLogin = nifLogin;
        this.password = password;
        this.isActivo = isActivo;
    }

    public Usuarios(){

    }

    public String getNifLogin() {
        return nifLogin;
    }

    public void setNifLogin(String nifLogin) {
        this.nifLogin = nifLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIsActivo() {
        return isActivo;
    }

    public void setIsActivo(String isActivo) {
        this.isActivo = isActivo;
    }

}
