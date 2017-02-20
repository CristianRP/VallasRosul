package com.gruporosul.vallasrosul.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.adapter.ContratosAdapter;
import com.gruporosul.vallasrosul.bean.Contrato;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ContratosActivity extends AppCompatActivity
            implements ContratosAdapter.OnItemClickListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recyclerContratos)
    RecyclerView mRecyclerContratos;
    @BindView(R.id.contentContratos)
    CoordinatorLayout mContentContratos;
    @BindColor(R.color.md_divider_white)
    int white;

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private ProgressDialog mProgressDialog;
    private ContratosAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contratos);
        ButterKnife.bind(this);

        setToolbar();

        if (true) {
            startActivity(new Intent(ContratosActivity.this, NuevaVallaActivity.class));
        }

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

        mRecyclerContratos.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerContratos.setLayoutManager(mLayoutManager);

        mRecyclerContratos.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        );

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Obteniendo contratos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();

        getContratos(getIntent().getIntExtra("codProyecto", 0));
    }

    private void getContratos(int codProyecto) {
        Call<List<Contrato>> getContrato = mVallasApi.getContratos(codProyecto);
        getContrato.enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                mProgressDialog.dismiss();
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getError();

                        Log.e("LoginActivity", " " + apiError.getErrorDescription());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API

                            Log.e("LoginActivity", " " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Toast.makeText(ContratosActivity.this, error, Toast.LENGTH_SHORT).show();

                    Log.e("LoginAcitivity", " " + error);
                    return;
                }
                Contrato.CONTRATOS = response.body();
                mAdapter = new ContratosAdapter(Contrato.CONTRATOS, ContratosActivity.this);
                mAdapter.setHasStableIds(true);
                mAdapter.setOnItemClickListener(ContratosActivity.this);
                mRecyclerContratos.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable t) {
                Log.e("onFailure: GetContratos", t.getMessage() + " " + t.getLocalizedMessage() + " " + t.toString());
                mProgressDialog.dismiss();
                //Toast.makeText(ContratosActivity.this, "Error de red!", Toast.LENGTH_SHORT).show();
                /*startActivity(new Intent(ContratosActivity.this, ProyectosActivity.class));
                finish();*/
            }
        });
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("Contratos");
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Establecer icono del drawer toggle
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onItemClick(final ContratosAdapter.ViewHolder item, int position) {
        final int itemId = Integer.parseInt(Long.toString(item.getItemId()));
        new BottomSheetBuilder(this, R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_LIST)
                .setMenu(R.menu.menu_publicidad)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.ver_vallas:
                                Intent publicidad = new Intent(ContratosActivity.this, PublicidadActivity.class);
                                publicidad.putExtra("codProyecto", getIntent().getIntExtra("codProyecto", 0));
                                publicidad.putExtra("codContrato", itemId);
                                startActivity(publicidad);
                                break;
                            case R.id.crear_vallas:
                                startActivity(new Intent(ContratosActivity.this, NuevaVallaActivity.class));
                                break;
                            default:
                                Toast.makeText(ContratosActivity.this, "Debes seleccionar una opción!", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                })
                .createDialog()
                .show();
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
