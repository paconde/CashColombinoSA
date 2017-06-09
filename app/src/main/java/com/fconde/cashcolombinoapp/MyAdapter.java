package com.fconde.cashcolombinoapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by FConde on 08/06/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<Recetas> receta;
    private int layout;
    private OnItemClickListener itemClickListener;

    public MyAdapter(List<Recetas> receta, int layout, OnItemClickListener itemClickListener){
        this.receta = receta;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(receta.get(position), itemClickListener);
    }

    @Override
    public int getItemCount() {
        return receta.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Elementos UI a rellenar
        public TextView textViewName;
        public ImageView imageViewReceta;

        public ViewHolder(View itemView) {
            super(itemView);
            //this.textViewName = (TextView)itemView.findViewById(R.id.textViewName);

            textViewName = (TextView) itemView.findViewById(R.id.textViewReceta);
            imageViewReceta = (ImageView)itemView.findViewById(R.id.imageViewReceta);
        }

        public void bind(final Recetas receta, final OnItemClickListener itemClickListener){
            //this.textViewName.setText(recetas);
            textViewName.setText(receta.getNombreReceta());
            imageViewReceta.setImageResource(receta.getCodigoImagen());
            //imageViewReceta.setImageDrawable(receta.getCodigoImagen());

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
