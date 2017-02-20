package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Proyecto;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cristian Ramírez on 24/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class ProyectosAdapter
        extends RecyclerView.Adapter<ProyectosAdapter.ViewHolder> {

    private Context mContext;
    private List<Proyecto> mListaProyectos;

    public interface OnItemClickListener {
        void onItemClick(ViewHolder item, int position);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return listener;
    }

    public ProyectosAdapter(List<Proyecto> proyectos, Context context) {
        this.mListaProyectos = proyectos;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.imgLogoProyecto)
        ImageView mLogoProyecto;
        @BindView(R.id.codigoProyecto)
        TextView mCodigoProyecto;
        @BindView(R.id.txtNombreProyecto)
        TextView mNombreProyecto;

        private ProyectosAdapter parent = null;

        public ViewHolder(View v, ProyectosAdapter parent) {
            super(v);
            v.setOnClickListener(this);
            this.parent = parent;
            ButterKnife.bind(this, v);
        }

        @Override
        public void onClick(View view) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if (listener != null) {
                listener.onItemClick(this, getAdapterPosition());
            }
        }
    }

    @Override
    public long getItemId(int position) {
        return mListaProyectos.get(position).getCodigoProyecto();
    }

    @Override
    public int getItemCount() {
        return mListaProyectos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_proyectos, parent, false);
        return new ViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Proyecto proyecto = mListaProyectos.get(position);
        holder.mCodigoProyecto.setText(String.valueOf(proyecto.getCodigoProyecto()));
        holder.mNombreProyecto.setText(proyecto.getDescripcion());
        Glide.with(mContext)
                .load(proyecto.getUrlImagen())
                .into(holder.mLogoProyecto);
    }
}
