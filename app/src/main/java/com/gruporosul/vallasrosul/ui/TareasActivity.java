package com.gruporosul.vallasrosul.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.adapter.TareaAdapter;
import com.gruporosul.vallasrosul.bean.Tarea;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TareasActivity extends AppCompatActivity
        implements TareaAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindString(R.string.title_activity_tareas)
    String activityName;
    @BindView(R.id.recyclerTareas)
    RecyclerView mRecyclerTareas;
    @BindView(R.id.contentTareas)
    CoordinatorLayout mContentTareas;

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLayoutManager;
    private TareaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tareas);
        ButterKnife.bind(this);
        setToolbar();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(VallasWebAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        mVallasApi = mRestAdapter.create(VallasWebAPI.class);

        mRecyclerTareas.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerTareas.setLayoutManager(mLayoutManager);

        mRecyclerTareas.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Obteniendo tareas...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        getTareas();
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(activityName);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Establecer icono del drawer toggle
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getTareas() {
        Log.e("TAREAS", "COD _ PROYECTO: " + getIntent().getIntExtra("codProyecto", 0));
        Call<List<Tarea>> getListadoTareas = mVallasApi.getTareas(getIntent().getIntExtra("codProyecto", 0));
        getListadoTareas.enqueue(new Callback<List<Tarea>>() {
            @Override
            public void onResponse(Call<List<Tarea>> call, Response<List<Tarea>> response) {
                mProgressDialog.dismiss();
                // Procesar errores
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getError();
                        showError();
                        Log.e("LoginActivity", " " + apiError.getErrorDescription());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            showError();
                            Log.e("LoginActivity", " " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //Toast.makeText(TareasActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Tarea.TAREAS = response.body();
                mAdapter = new TareaAdapter(response.body(), TareasActivity.this);
                mAdapter.setHasStableIds(true);
                mAdapter.setOnItemClickListener(TareasActivity.this);
                mRecyclerTareas.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Tarea>> call, Throwable t) {
                Log.e("failure_retrofit", t.getMessage() + t.getCause());
                Toast.makeText(TareasActivity.this, "Error en el servidor! Tiempo de espera agotado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TareasActivity.this, ProyectosActivity.class));
                finish();
            }
        });
    }


    private void showError() {
        showSnackbar(mContentTareas, "Error en la conexión!");
    }

    /**
     * Snackbar prefabricada para un uso rapido y eficiente
     */
    public void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Ok", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(TareaAdapter.ViewHolder item, int position) {
        Tarea tarea = Tarea.TAREAS.get(position);
        Intent detalle = new Intent(TareasActivity.this, RegistrarVisitaActivity.class);
        detalle.putExtra("latitud", tarea.getLatitud());
        detalle.putExtra("longitud", tarea.getLongitud());
        detalle.putExtra("codigotarea", tarea.getCodigoBusqueda());
        detalle.putExtra("descripcion", tarea.getDescripcionPublicidad());
        startActivity(detalle);
    }
}
