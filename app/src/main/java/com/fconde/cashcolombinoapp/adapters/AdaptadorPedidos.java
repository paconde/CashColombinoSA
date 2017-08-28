package com.fconde.cashcolombinoapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.Pedidos;

import java.text.SimpleDateFormat;
import java.util.List;

import static android.R.color.holo_red_dark;
import static android.R.color.holo_red_light;
import static android.R.color.white;
import static com.fconde.cashcolombinoapp.R.color.colorAccent;

/**
 * Created by FConde on 16/06/2017.
 */

public class AdaptadorPedidos extends BaseAdapter {
    private Context context;
    private List<Pedidos> pedidos;
    private int layout;

    public AdaptadorPedidos(Context context, List<Pedidos> pedidos, int layout){
        this.context = context;
        this.pedidos = pedidos;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return pedidos.size();
    }

    @Override
    public Pedidos getItem(int position) {
        return pedidos.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.pedido = (TextView)convertView.findViewById(R.id.textViewPedido);
            vh.enviado = (TextView)convertView.findViewById(R.id.textViewEnviado);
            vh.lineas = (TextView)convertView.findViewById(R.id.textViewLineas);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        Pedidos pedido = pedidos.get(position);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = df.format(pedido.getFechaCreacion());
        vh.pedido.setText("Pedido: " + fecha);

        String enviado = (pedido.isEnviado()) ? "ENVIADO" : "PTE. ENVÍO";
        vh.enviado.setText(enviado);

        if(enviado == "ENVIADO"){
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.backPedEnviado));
        }else {
            convertView.setBackgroundColor(ContextCompat.getColor(context, R.color.backPedNoEnviado));
        }

        String numeroLineas = String.valueOf(pedido.getLineasPedido().size());
        vh.lineas.setText("Nº de referencias del pedido: " + numeroLineas);

        return convertView;
    }


    public class ViewHolder{
        TextView pedido;
        TextView enviado;
        TextView lineas;
    }
}
