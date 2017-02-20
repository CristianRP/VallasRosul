package com.gruporosul.vallasrosul.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Proyecto;
import com.gruporosul.vallasrosul.bean.VallaBody;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;
import com.nihaskalam.progressbuttonlibrary.CircularProgressButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NuevaVallaActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    @BindString(R.string.title_activity_nueva_valla)
    String appName;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.etCodigoBusqueda)
    TextInputEditText mCodigoBusqueda;
    @BindView(R.id.etDescripcion)
    TextInputEditText mDescripcion;
    @BindView(R.id.edLatLng)
    TextInputEditText mLatLng;
    @BindView(R.id.btnObtenerUbicacion)
    CircularProgressButton mObtenerUbicacion;
    @BindView(R.id.spinnerProyectos)
    Spinner mProyectos;
    @BindView(R.id.edAlto)
    TextInputEditText mAlto;
    @BindView(R.id.edAncho)
    TextInputEditText mAncho;
    @BindView(R.id.edDireccion)
    TextInputEditText mDireccion;
    @BindView(R.id.edFoto)
    TextInputEditText mFoto;
    @BindView(R.id.toggleDemo)
    ToggleButton mToggleDemo;
    @BindView(R.id.spinnerPeriodo)
    Spinner mSpinnerPeriodo;
    @BindView(R.id.enviarNuevaValla)
    CircularProgressButton mEnviarNuevaValla;
    @BindView(R.id.coordinatorNueva)
    CoordinatorLayout mCoordinatorNueva;
    @BindView(R.id.spinnerZona)
    Spinner mSpinnerZona;


    private ProgressDialog mProgressDialog;
    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Bitmap bitMap;
    String encoded_string;
    byte[] array;

    // Códigos de petición
    public static final int REQUEST_LOCATION = 1;
    private static int TAKE_PICTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_valla);

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

        getProyectos();
        getZonas();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .enableAutoManage(this, this)
                .build();


        mToggleDemo.setTextOn("Si");
        mToggleDemo.setTextOff("No");

        String[] periodos = {
                "Mensual", "Semanal", "Diario"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                periodos);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerPeriodo.setAdapter(adapter);

        mEnviarNuevaValla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnviarNuevaValla.setIndeterminateProgressMode(true);
                if (encoded_string != null) {
                    int codProyecto = 0;
                    for (Proyecto proyecto : Proyecto.PROYECTOS) {
                        if (proyecto.getDescripcion().equals(mProyectos.getSelectedItem().toString())) {
                            codProyecto = proyecto.getCodigoProyecto();
                        }
                    }
                    int codZona = 0;
                    for (Proyecto proyecto : Proyecto.ZONAS) {
                        if (proyecto.getDescripcion().equals(mSpinnerZona.getSelectedItem().toString())) {
                            codZona = proyecto.getCodigoProyecto();
                        }
                    }
                    VallaBody vallaBody = new VallaBody(
                            mDescripcion.getText().toString(),
                            String.valueOf(mLastLocation.getLatitude()),
                            String.valueOf(mLastLocation.getLongitude()),
                            7, 8,
                            mAlto.getText().toString(),
                            mAncho.getText().toString(),
                            mDireccion.getText().toString(),
                            encoded_string,
                            mToggleDemo.getText().toString(),
                            mSpinnerPeriodo.getSelectedItem().toString(),
                            mCodigoBusqueda.getText().toString(),
                            codZona
                    );
                    requestCrearValla(vallaBody);
                    Log.e("datos", ":v " + codProyecto +
                            mToggleDemo.getText().toString()+
                            mSpinnerPeriodo.getSelectedItem().toString()+
                            mCodigoBusqueda.getText().toString()+
                            codZona);
                    mEnviarNuevaValla.showProgress();
                    mEnviarNuevaValla.setIndeterminateProgressMode(true);
                    Log.e("Foto_detalle", ":v" + encoded_string.replace("\n", ""));
                } else {
                    mEnviarNuevaValla.showError();
                    Snackbar.make(mCoordinatorNueva, "Debes tomar la fotografia", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                }
            }
        });

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

    @OnClick(R.id.btnObtenerUbicacion)
    void obtenerUbicacion() {
        onConnected(null);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Aquí muestras confirmación explicativa al usuario
                // por si rechazó los permisos anteriormente
            } else {
                ActivityCompat.requestPermissions(
                        this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_LOCATION);
            }
        } else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                String latlng = String.format(Locale.getDefault(), "%1$s ; %2$s",
                        mLastLocation.getLatitude(), mLastLocation.getLongitude());
                mLatLng.setText(latlng);
                Toast.makeText(this, "Ubicación encontrada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ubicación no encontrada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @OnClick(R.id.btnTomarFoto)
    void OnClickTomarFoto() {
        takePicture();
    }


    private void takePicture() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_title_foto)
                .content(R.string.dialog_content_foto)
                .positiveText(R.string.dialog_positive_foto)
                .negativeText(R.string.dialog_negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent takePic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePic, TAKE_PICTURE);
                    }
                })
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            bitMap = (Bitmap) extras.get("data");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            array = outStream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            mFoto.setText(encoded_string.substring(0, 50));
            Log.e("foto", encoded_string.replace("\n", ""));
        }
    }

    private void getProyectos() {
        Call<List<Proyecto>> getProjects = mVallasApi.getListadoProyectos();
        getProjects.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {

                // Procesar errores
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

                    Toast.makeText(NuevaVallaActivity.this, error, Toast.LENGTH_SHORT).show();

                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Log.e("Successful", "proyectos obtenidos");
                Proyecto.PROYECTOS = response.body();
                setData();

            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                Log.e("failure_retrofit", t.getMessage());
                startActivity(new Intent(NuevaVallaActivity.this, ProyectosActivity.class));
                finish();
            }
        });
    }

    private void getZonas() {
        Call<List<Proyecto>> getProjects = mVallasApi.getProyectos();
        getProjects.enqueue(new Callback<List<Proyecto>>() {
            @Override
            public void onResponse(Call<List<Proyecto>> call, Response<List<Proyecto>> response) {

                // Procesar errores
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getError();

                        Log.e("zonas", " " + apiError.getErrorDescription());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API

                            Log.e("zonas", " " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    Toast.makeText(NuevaVallaActivity.this, error, Toast.LENGTH_SHORT).show();

                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Log.e("Successful", "proyectos obtenidos");
                Proyecto.ZONAS = response.body();
                setDataZonas();

            }

            @Override
            public void onFailure(Call<List<Proyecto>> call, Throwable t) {
                Log.e("failure_retrofit", t.getMessage());
                startActivity(new Intent(NuevaVallaActivity.this, ProyectosActivity.class));
                finish();
            }
        });
    }

    private void setData() {
        List<String> descProyecto = new ArrayList<>();
        for (Proyecto project : Proyecto.PROYECTOS) {
            descProyecto.add(project.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                descProyecto);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mProyectos.setAdapter(adapter);
    }

    private void setDataZonas() {
        List<String> descProyecto = new ArrayList<>();
        for (Proyecto project : Proyecto.ZONAS) {
            descProyecto.add(project.getDescripcion());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item,
                descProyecto);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerZona.setAdapter(adapter);
    }


    private void requestCrearValla(VallaBody vallaBody) {
        Call<Void> vallaBodyCall = mVallasApi.postCrearValla(vallaBody);
        vallaBodyCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()) {
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        error = apiError.getError();
                        //showError();
                        Log.e("Registrar_visita", " " + apiError.getErrorDescription());
                        Log.e("Registrar_visita", " " + apiError.getErrorDescription());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            //showError();
                            ApiError apiError = ApiError.fromResponseBody(response.errorBody());
                            error = apiError.getError();
                            Log.e("Registrar_visita", " " + response.errorBody().string());
                            Log.e("Registrar_visita", " " + apiError.getErrorDescription());
                            Log.e("Registrar_visita", " " + apiError.getMessage());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }


                    showError();
                    Log.e("Registrar_visita3", " " + error);
                    Log.e("Registrar_visita4", " " + response.message());
                    Log.e("Registrar_visita5", " " + response.code());
                    Log.e("Registrar_visita6", " " + response.errorBody());
                    return;
                }
                mEnviarNuevaValla.showComplete();
                Toast.makeText(NuevaVallaActivity.this, "Visita registrada!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(NuevaVallaActivity.this, ProyectosActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Registrar_visita", " " + t.getMessage());
            }
        });
    }

    private void showError() {
        showSnackbar(mCoordinatorNueva, "Error en la conexión!");
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

}
