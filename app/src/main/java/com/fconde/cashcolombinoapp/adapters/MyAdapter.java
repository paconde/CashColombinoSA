package com.fconde.cashcolombinoapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fconde.cashcolombinoapp.R;
import com.fconde.cashcolombinoapp.models.Recetas;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by FConde on 08/06/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Recetas> receta;
    private int layout;
    private OnItemClickListener itemClickListener;
    private Context context;

    public MyAdapter(List<Recetas> receta, int layout, OnItemClickListener itemClickListener){
        this.receta = receta;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde mejoraremos
        //toda la lógica comoextraer los datos, referencias ...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Llamamos al método Bind del ViewHolder pasandole objeto y listener
        holder.bind(receta.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return receta.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // Elementos UI a rellenar
        public TextView textViewName;
        public ImageView imageViewReceta;

        public ViewHolder(View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            super(itemView);
            //this.textViewName = (TextView)itemView.findViewById(R.id.textViewName);

            textViewName = (TextView) itemView.findViewById(R.id.textViewReceta);
            imageViewReceta = (ImageView)itemView.findViewById(R.id.imageViewReceta);
        }

        public void bind(final Recetas receta, final OnItemClickListener itemClickListener){
            //this.textViewName.setText(recetas);
            textViewName.setText(receta.getNombreReceta());
            Picasso.with(context).load(receta.getCodigoImagen()).fit().into(imageViewReceta);
            //imageViewReceta.setImageResource(receta.getCodigoImagen());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(receta, getAdapterPosition());
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Recetas receta, int position);
    }
}
