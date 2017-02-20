package com.gruporosul.vallasrosul.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.MenuOpcion;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Cristian Ramírez on 9/02/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class MenuAdapter
        extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context mContext;
    private List<MenuOpcion> mListaOptions;

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

    public MenuAdapter(List<MenuOpcion> options, Context context) {
        this.mListaOptions = options;
        this.mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        @BindView(R.id.txtMenu)
        TextView txtMenu;

        private MenuAdapter parent = null;

        public ViewHolder(View v, MenuAdapter parent) {
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
        return mListaOptions.get(position).getIdOpcion();
    }

    @Override
    public int getItemCount() {
        return mListaOptions.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_content_menu, parent, false);
        return new ViewHolder(v, this);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuOpcion opcion = MenuOpcion.OPCIONES.get(position);
        holder.txtMenu.setText(opcion.getTitle());
    }
}
