package com.fconde.cashcolombinoapp.models;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by FConde on 16/08/2017.
 */

public class Comunicador {
    private static ArrayList<Catalogo> resultados = null;
    private static int pos;
    private static volatile boolean articuloEncontrado = false;

    public static void setResultados(ArrayList<Catalogo> newResultados){
        resultados = newResultados;
    }

    public static ArrayList<Catalogo> getResultados(){
        return resultados;
    }

    public static void setResultadoFinal(int position){
        pos = position;
    }

    public static int getResultadoFinal(){
        return pos;
    }

    public static void setArticuloEncontrado (boolean encontrado){articuloEncontrado = encontrado;}

    public static boolean getArticuloEncontrado(){return articuloEncontrado;}

    public static String getLoginPreferences(SharedPreferences preferences){
        return preferences.getString("login", "");
    }

    public static String getPasswordPreferences(SharedPreferences preferences){
        return preferences.getString("password", "");
    }

}
