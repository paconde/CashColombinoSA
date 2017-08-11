package com.fconde.cashcolombinoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.Catalogo;

import java.util.ArrayList;

/**
 * Created by FConde on 11/08/2017.
 */

public class AdaptadorBusquedaArticulo extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<Catalogo> resultados;

    public AdaptadorBusquedaArticulo(Context context, int layout, ArrayList<Catalogo> resultados){
        this.context = context;
        this.layout = layout;
        this.resultados = resultados;
    }

    @Override
    public int getCount() {
        return this.resultados.size();
    }

    @Override
    public Object getItem(int position) {
        return this.resultados.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        v = layoutInflater.inflate(R.layout.list_view_busqueda_articulos, null);

        String resultadoItem = resultados.get(position).getArticulo();

        TextView textViewResultadoItem = (TextView) v.findViewById(R.id.textViewBusquedaArticulo);
        textViewResultadoItem.setText(resultadoItem);

        return v;
    }
}
