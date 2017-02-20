package com.gruporosul.vallasrosul.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
import com.gruporosul.vallasrosul.adapter.PublicidadAdapter;
import com.gruporosul.vallasrosul.bean.Publicidad;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;

import java.io.IOException;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PublicidadActivity extends AppCompatActivity
        implements PublicidadAdapter.OnItemClickListener {

    @BindString(R.string.app_name)
    String appName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerPublicidades)
    RecyclerView mRecyclerPublicidades;
    @BindView(R.id.content_publicidad)
    ConstraintLayout mContentPublicidad;

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLayoutManager;
    private PublicidadAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicidad);
        ButterKnife.bind(this);

        setToolbar();

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(VallasWebAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mVallasApi = mRestAdapter.create(VallasWebAPI.class);

        mRecyclerPublicidades.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerPublicidades.setLayoutManager(mLayoutManager);

        mRecyclerPublicidades.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Obteniendo proyectos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        int codProyecto = getIntent().getIntExtra("codProyecto", 0);
        int codContrato = getIntent().getIntExtra("codContrato", 0);
        getPublicidad(codContrato, codProyecto);
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        setTitle(appName);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Establecer icono del drawer toggle
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void getPublicidad(int codContrato, int codProyecto) {
        Call<List<Publicidad>> getPubli = mVallasApi.getPublicidad(codContrato, codProyecto);
        getPubli.enqueue(new Callback<List<Publicidad>>() {
            @Override
            public void onResponse(Call<List<Publicidad>> call, Response<List<Publicidad>> response) {
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
                    Toast.makeText(PublicidadActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Publicidad.PUBLICIDADES = response.body();
                System.out.print(": " + Publicidad.PUBLICIDADES.get(0).getImagen());
                mAdapter = new PublicidadAdapter(response.body(), PublicidadActivity.this);
                mAdapter.setHasStableIds(true);
                mAdapter.setOnItemClickListener(PublicidadActivity.this);
                mRecyclerPublicidades.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Publicidad>> call, Throwable t) {
                Log.e("failure_retrofit", t.getMessage());
                Toast.makeText(PublicidadActivity.this, "Error en el servidor! Tiempo de espera agotado", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(PublicidadActivity.this, ProyectosActivity.class));
                finish();
            }
        });
    }

    private void showError() {
        showSnackbar(mContentPublicidad, getString(R.string.login_error));
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
    public void onItemClick(PublicidadAdapter.ViewHolder item, int position) {
        Publicidad publicidad = Publicidad.PUBLICIDADES.get(position);
        Intent detalle = new Intent(PublicidadActivity.this, DetallePublicidadActivity.class);
        detalle.putExtra("latitud", publicidad.getLatitud());
        detalle.putExtra("longitud", publicidad.getLongitud());
        detalle.putExtra("codigoPublicidad", publicidad.getCodigoPublicidad());
        detalle.putExtra("descripcion", publicidad.getDescripcion());
        startActivity(detalle);
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
}
