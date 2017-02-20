package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Tarea;

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


public class TareaAdapter
        extends RecyclerView.Adapter<TareaAdapter.ViewHolder> {

    private Context mContext;
    private List<Tarea> mListaTareas;

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

    public TareaAdapter(List<Tarea> tareas, Context context) {
        this.mListaTareas = tareas;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.txtCodigoBusqueda)
        TextView mCodPublicidad;
        @BindView(R.id.txtDescripcion)
        TextView mDescripcion;
        @BindView(R.id.txtSupervision)
        TextView mSupervision;
        @BindView(R.id.txtEstado)
        TextView mEstado;
        @BindColor(R.color.colorPrimary)
        int green;
        @BindColor(R.color.status_default)
        int red;

        private TareaAdapter parent = null;

        public ViewHolder(View v, TareaAdapter parent) {
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
        return mListaTareas.get(position).getCodigoPublicidad();
    }

    @Override
    public int getItemCount() {
        return mListaTareas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tareas, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tarea tarea = mListaTareas.get(position);
        holder.mCodPublicidad.setText(String.valueOf(tarea.getCodigoBusqueda()));
        holder.mDescripcion.setText(tarea.getDescripcionPublicidad());
        if (tarea.getVencimientoTarea().toUpperCase().equals("ACTIVO")) {
            holder.mEstado.setText(tarea.getVencimientoTarea());
            holder.mEstado.setTextColor(holder.green);
        } else {
            holder.mEstado.setText(tarea.getVencimientoTarea());
            holder.mEstado.setTextColor(holder.red);
        }
        String latlong = String.format(Locale.getDefault(), "Supervisión: %1$s",
                tarea.getCalendarioPeriodo());
        holder.mSupervision.setText(latlong);
    }
}
