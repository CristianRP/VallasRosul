package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Publicidad;

import java.util.List;
import java.util.Locale;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cristian Ramírez on 24/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class PublicidadAdapter
        extends RecyclerView.Adapter<PublicidadAdapter.ViewHolder> {

    private Context mContext;
    private List<Publicidad> mListaPublicidad;

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

    public PublicidadAdapter(List<Publicidad> publicidades, Context context) {
        this.mListaPublicidad = publicidades;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.txtCodPublicidad)
        TextView mCodPublicidad;
        @BindView(R.id.txtDescripcion)
        TextView mDescripcion;
        @BindView(R.id.txtLatLong)
        TextView mLatLong;
        @BindView(R.id.txtEstado)
        TextView mEstado;
        @BindColor(R.color.colorPrimary)
        int green;
        @BindColor(R.color.status_default)
        int red;

        private PublicidadAdapter parent = null;

        public ViewHolder(View v, PublicidadAdapter parent) {
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
        return mListaPublicidad.get(position).getCodigoPublicidad();
    }

    @Override
    public int getItemCount() {
        return mListaPublicidad.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_publicidades, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Publicidad publicidad = mListaPublicidad.get(position);
        holder.mCodPublicidad.setText(String.valueOf(publicidad.getCodigoPublicidad()));
        holder.mDescripcion.setText(publicidad.getDescripcion());
        if (publicidad.getEstado().toUpperCase().equals("ACTIVO")) {
            holder.mEstado.setText(publicidad.getEstado());
            holder.mEstado.setTextColor(holder.green);
        } else {
            holder.mEstado.setText(publicidad.getEstado());
            holder.mEstado.setTextColor(holder.red);
        }
        String latlong = String.format(Locale.getDefault(), "Lat: %1$.3f Long: %2$.3f",
                publicidad.getLatitud(),
                publicidad.getLongitud());
        holder.mLatLong.setText(latlong);
    }
}
