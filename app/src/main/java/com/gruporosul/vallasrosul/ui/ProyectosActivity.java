package com.gruporosul.vallasrosul.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.gruporosul.vallasrosul.adapter.ProyectosAdapter;
import com.gruporosul.vallasrosul.bean.Proyecto;
import com.gruporosul.vallasrosul.manager.PrefManager;
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

public class ProyectosActivity extends AppCompatActivity
        implements ProyectosAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerProyectos)
    RecyclerView mRecyclerProyectos;
    @BindView(R.id.content_proyectos)
    ConstraintLayout mContentProyectos;
    @BindString(R.string.app_name)
    String appName;

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLayoutManager;
    private ProyectosAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!PrefManager.get(this).isLoggedIn()) {
            startActivity(new Intent(ProyectosActivity.this, LoginActivity.class));
            finish();
            return;
        }

        checkPermission();

        setContentView(R.layout.activity_proyectos);
        ButterKnife.bind(this);

        setToolbar();

        mRestAdapter = new Retrofit.Builder()
                .baseUrl(VallasWebAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mVallasApi = mRestAdapter.create(VallasWebAPI.class);

        mRecyclerProyectos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerProyectos.setLayoutManager(mLayoutManager);

        mRecyclerProyectos.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Obteniendo proyectos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        getProyectos();

    }

    /**
     * Verifica si se a dado los permisos a la aplicación
     * esto es necesario para el API 23
     */
    private void checkPermission() {
        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    11 );
        }
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

    private void getProyectos() {
        Call<List<Proyecto>> getProjects = mVallasApi.getProyectos();
        getProjects.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {
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

                    Toast.makeText(ProyectosActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Log.e("Successful", "proyectos obtenidos");
                Proyecto.PROYECTOS = response.body();
                for (Proyecto pj: Proyecto.PROYECTOS) {
                    Log.e("kk", pj.getDescripcion());
                }
                for (Proyecto pj: response.body()) {
                    Log.e("kk", pj.getDescripcion());
                }
                mAdapter = new ProyectosAdapter(Proyecto.PROYECTOS, ProyectosActivity.this);
                mAdapter.setHasStableIds(true);
                mAdapter.setOnItemClickListener(ProyectosActivity.this);
                mRecyclerProyectos.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                Log.e("failure_retrofit", t.getMessage());
                startActivity(new Intent(ProyectosActivity.this, ProyectosActivity.class));
                finish();
            }
        });
    }

    private void showError() {
        showSnackbar(mContentProyectos, getString(R.string.login_error));
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
    public void onItemClick(ProyectosAdapter.ViewHolder item, int position) {
        Toast.makeText(this, "item" + item.getItemId(), Toast.LENGTH_SHORT).show();
        Intent publicidad = new Intent(ProyectosActivity.this, TareasActivity.class);
        publicidad.putExtra("codProyecto", Integer.parseInt(Long.toString(item.getItemId())));
        //Proyecto proyecto = Proyecto.PROYECTOS.get(item.getItemId());
        //publicidad.putExtra("proyecto", );
        startActivity(publicidad);
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
