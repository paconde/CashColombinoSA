package com.fconde.cashcolombinoapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.LineasPedido;
import com.fconde.cashcolombinoapp.models.Pedidos;

import java.util.List;

/**
 * Created by FConde on 17/06/2017.
 */

public class AdaptadorLineasPedido extends BaseAdapter {

    private Context context;
    private List<LineasPedido> lineasPedido;
    private int layout;

    public AdaptadorLineasPedido(Context context, List<LineasPedido> lineasPedido, int layout){
        this.context = context;
        this.lineasPedido = lineasPedido;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return lineasPedido.size();
    }

    @Override
    public LineasPedido getItem(int position) {
        return lineasPedido.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);
            vh = new ViewHolder();
            vh.codigo = (TextView)convertView.findViewById(R.id.textViewCodigo);
            vh.cantidad = (TextView)convertView.findViewById(R.id.textViewCantidad);
            vh.descripcion = (TextView)convertView.findViewById(R.id.textViewArticulo);
            vh.editLinea = (ImageView)convertView.findViewById(R.id.imgBtnEditarLinea);
            vh.deleteLinea = (ImageView)convertView.findViewById(R.id.imgBtnBorrarLinea);

            vh.editLinea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });
            vh.deleteLinea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });

            /*convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ListView) parent).performItemClick(v, position, 0);
                }
            });*/

            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }

        LineasPedido lineaPedido = lineasPedido.get(position);
        vh.codigo.setText("Codigo: " + lineaPedido.getCodArticulo());
        vh.cantidad.setText("Cantidad: " + lineaPedido.getCantidad());
        vh.descripcion.setText(lineaPedido.getDescArticulo());

        return convertView;
    }

    public class ViewHolder{
        TextView codigo, descripcion, cantidad;
        ImageView editLinea, deleteLinea;


    }
}
