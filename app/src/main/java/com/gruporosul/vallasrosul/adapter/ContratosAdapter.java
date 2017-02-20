package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Contrato;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cristian Ramírez on 1/02/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class ContratosAdapter
        extends RecyclerView.Adapter<ContratosAdapter.ViewHolder> {

    private Context mContext;
    private List<Contrato> mListaContratos;

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

    public ContratosAdapter(List<Contrato> contratos, Context context) {
        this.mListaContratos = contratos;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.txtCodContrato)
        TextView mCodContrato;
        @BindView(R.id.txtDescripcion)
        TextView mDescripcion;
        @BindView(R.id.txtPrecio)
        TextView mPrecio;
        @BindView(R.id.txtFechaInicio)
        TextView mFechaInicio;
        @BindView(R.id.txtFechaFin)
        TextView mFechaFin;

        private ContratosAdapter parent = null;

        public ViewHolder(View v, ContratosAdapter parent) {
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
        return mListaContratos.get(position).getCodigoContrato();
    }

    @Override
    public int getItemCount() {
        return mListaContratos.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_contratos, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contrato contrato = mListaContratos.get(position);
        holder.mCodContrato.setText(String.valueOf(contrato.getCodigoContrato()));
        holder.mDescripcion.setText(contrato.getDescripcion());
        String fechaInicio = String.format(Locale.getDefault(), "Fecha Inicio: %1$s",
                contrato.getFechaInicio().substring(0,10));
        holder.mFechaInicio.setText(fechaInicio);
        String fechaFin = String.format(Locale.getDefault(), "Fecha Fin: %1$s",
                contrato.getFechaFin().substring(0,10));
        holder.mFechaFin.setText(fechaFin);
        String precio = String.format(Locale.getDefault(), "Q.%1$.3f",contrato.getPrecio());
        holder.mPrecio.setText(precio);
    }

}