package com.gruporosul.vallasrosul.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.adapter.MenuAdapter;
import com.gruporosul.vallasrosul.bean.MenuOpcion;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
            implements MenuAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerMain)
    RecyclerView mRecyclerMain;
    @BindView(R.id.coodinatorMain)
    CoordinatorLayout mCoodinatorMain;

    private MenuAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setToolbar();

        mRecyclerMain.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerMain.setLayoutManager(mLayoutManager);

        mRecyclerMain.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        MenuOpcion menuOpcion[] = {
                new MenuOpcion(1, "Tareas Supervisor"),
                new MenuOpcion(2, "Mapa Supervisor"),
                new MenuOpcion(3, "Cotizar vallas")
        };

        if (MenuOpcion.OPCIONES.isEmpty()) {
            for (MenuOpcion opcion : menuOpcion) {
                MenuOpcion.OPCIONES.add(opcion);
            }
        }

        mAdapter = new MenuAdapter(MenuOpcion.OPCIONES, this);
        mAdapter.setHasStableIds(true);
        mAdapter.setOnItemClickListener(this);
        mRecyclerMain.setAdapter(mAdapter);

    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("Menú principal");
    }


    @Override
    public void onItemClick(MenuAdapter.ViewHolder item, int position) {
        switch (position) {
            case 0: // listado zona geografica
                startActivity(new Intent(MainActivity.this, ProyectosActivity.class));
                break;
            case 1: // mostrar visitas mapa
                break;
            case 2: // cotizar vallas (crear)
                startActivity(new Intent(MainActivity.this, NuevaVallaActivity.class));
                break;
            default:
                break;
        }
    }
}
