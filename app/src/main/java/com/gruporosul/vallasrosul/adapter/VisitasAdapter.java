package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Visitas;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cristian Ramírez on 23/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class VisitasAdapter
        extends RecyclerView.Adapter<VisitasAdapter.ViewHolder> {

    private Context mContext;
    private List<Visitas> mListVisitas;

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

    public VisitasAdapter(List<Visitas> visitas, Context context) {
        this.mListVisitas = visitas;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.txtNoVisita)
        TextView mNoVisita;
        @BindView(R.id.txtValla)
        TextView mValla;
        @BindView(R.id.txtProyecto)
        TextView mProyecto;
        @BindView(R.id.txtProblema)
        TextView mProblema;

        private VisitasAdapter parent = null;

        public ViewHolder(View v, VisitasAdapter parent) {
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
        return mListVisitas.get(position).getNoVisita();
    }

    @Override
    public int getItemCount() {
        return mListVisitas.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_visitas, parent, false);
        return new ViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Visitas visita = mListVisitas.get(position);
        holder.mNoVisita.setText(String.valueOf(visita.getNoVisita()));
        holder.mValla.setText(visita.getValla());
        holder.mProyecto.setText(visita.getDescripcion());
        holder.mProblema.setText(visita.getProblemaPublicidad());
    }
}
