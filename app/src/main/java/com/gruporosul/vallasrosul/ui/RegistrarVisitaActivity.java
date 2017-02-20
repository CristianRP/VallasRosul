package com.gruporosul.vallasrosul.ui;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.Problema;
import com.gruporosul.vallasrosul.bean.Supervisor;
import com.gruporosul.vallasrosul.bean.Tarea;
import com.gruporosul.vallasrosul.bean.VisitaBody;
import com.gruporosul.vallasrosul.manager.PrefManager;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;
import com.nihaskalam.progressbuttonlibrary.CircularProgressButton;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.gruporosul.vallasrosul.manager.PrefManager.PREF_USERNAME;

public class RegistrarVisitaActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.txtCodigoBusqueda)
    TextView mCodigoBusqueda;
    @BindView(R.id.txtDescripcion)
    TextView mDescripcion;
    @BindView(R.id.txtGeoPosicion)
    TextView mGeoPosicion;
    @BindView(R.id.txtEstadoContrato)
    TextView mEstado;
    @BindView(R.id.txtProyecto)
    TextView mProyecto;
    @BindView(R.id.imgValla)
    ImageView mFotoValla;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.contentRegistrarVisita)
    CoordinatorLayout mContentRegistrarVisita;
    @BindColor(R.color.colorPrimary)
    int green;
    @BindColor(R.color.status_default)
    int red;
    @BindView(R.id.spinner)
    Spinner mSpinner;
    @BindView(R.id.txtObservaciones)
    TextInputEditText txtObservaciones;
    @BindView(R.id.enviarVisita)
    CircularProgressButton mEnviarVisita;

    private GoogleMap mMap;
    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;
    private ProgressDialog mProgressDialog;
    private static int TAKE_PICTURE = 1;
    private Bitmap bitMap;
    String encoded_string;
    byte[] array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_visita);

        ButterKnife.bind(this);

        setToolbar();


        InputMethodManager imm =
                (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);

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

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Cargando datos...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
        requestGetProblemas();
        requestGetSupervisorLogeado();

        Intent dataPublicidad = getIntent();
        final Tarea publicidad = Tarea.getTareas(dataPublicidad.getStringExtra("codigotarea"));

        assert publicidad != null;

        String codigoPublicidad = String.format(Locale.getDefault(),
                "Código: %1$s", publicidad.getCodigoBusqueda());
        mCodigoBusqueda.setText(codigoPublicidad);
        mDescripcion.setText(publicidad.getDescripcionPublicidad());
        String geoPosicion = String.format(Locale.getDefault(), "Supervisión: %1$s",
                publicidad.getCalendarioPeriodo());
        mGeoPosicion.setText(geoPosicion);
        if (publicidad.getVencimientoTarea().toUpperCase().equals("ACTIVO")) {
            mEstado.setText(publicidad.getVencimientoTarea());
            mEstado.setTextColor(green);
        } else {
            mEstado.setText(publicidad.getVencimientoTarea());
            mEstado.setTextColor(red);
        }
        String codZona = String.format(Locale.getDefault(), "Código de zona: %1$s",
                publicidad.getCodigozona());
        mProyecto.setText(codZona);
        //String foto = new String(publicidad.getImagen());
        byte[] decodeFoto = Base64.decode(publicidad.getImagen(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodeFoto, 0, decodeFoto.length);
        Log.e("TEST_REGISTRAR", ":V " + publicidad.getImagen());
        mFotoValla.setImageBitmap(decodedByte);

        mMapView.onCreate(savedInstanceState);

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            mMapView.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

        mEnviarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEnviarVisita.setIndeterminateProgressMode(true);
                if (encoded_string != null) {
                    int codigoProblema = 0;
                    for (Problema problema: Problema.PROBLEMAS) {
                        if (problema.getDescripcion().equals(mSpinner.getSelectedItem().toString())) {
                            codigoProblema = problema.getCodigoProblema();
                        }
                    }
                    requestRegistrarVisita(publicidad.getCodigoPublicidad(), codigoProblema,
                            txtObservaciones.getText().toString(), Supervisor.SUPERVISORES.get(0).getIdUsuario(),
                            publicidad.getCodigozona(),
                            publicidad.getVencimientoTarea(), encoded_string);
                    mEnviarVisita.showProgress();
                    mEnviarVisita.setIndeterminateProgressMode(true);
                    Log.e("Foto_detalle", ":v" + encoded_string.replace("\n", ""));
                } else {
                    mEnviarVisita.showError();
                    Snackbar.make(mContentRegistrarVisita, "Debes tomar la fotografia", Snackbar.LENGTH_LONG)
                            .setAction("Ok", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            });
                }
            }
        });

    }

    private void requestGetSupervisorLogeado() {
        Call<List<Supervisor>> getSupervisorLogeado = mVallasApi.getSupervisorLogeado(PrefManager.getString(this, PREF_USERNAME));
        getSupervisorLogeado.enqueue(new Callback<List<Supervisor>>() {
            @Override
            public void onResponse(Call<List<Supervisor>> call, Response<List<Supervisor>> response) {
                mProgressDialog.dismiss();
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

                    Toast.makeText(RegistrarVisitaActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }
                Supervisor.SUPERVISORES = response.body();
                for (Supervisor superv : response.body()) {
                    Log.e("usuario", "success" + superv.getCodigoExterno());
                }
                Log.e("usuario", "success");
            }

            @Override
            public void onFailure(Call<List<Supervisor>> call, Throwable t) {
                Log.e("error_user", "error obteniendo el usuario " + t.getMessage());
                requestGetSupervisorLogeado();
            }
        });
    }

    private void requestGetProblemas() {
        Call<List<Problema>> getProblemas = mVallasApi.getProblemas();
        getProblemas.enqueue(new Callback<List<Problema>>() {
            @Override
            public void onResponse(Call<List<Problema>> call, Response<List<Problema>> response) {
                mProgressDialog.dismiss();
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

                    Toast.makeText(RegistrarVisitaActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }

                Problema.PROBLEMAS = response.body();
                List<String> descProblema = new ArrayList<>();
                for (Problema problema : response.body()) {
                    descProblema.add(problema.getDescripcion());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        RegistrarVisitaActivity.this, android.R.layout.simple_spinner_item,
                        descProblema);

                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                mSpinner.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Problema>> call, Throwable t) {
                mProgressDialog.dismiss();
                Log.e("Erro_Detalle", "error problemas" + t.getMessage());
                requestGetProblemas();
            }
        });
    }

    private void requestRegistrarVisita(int codigoPublicidad, int codigoProblema,
                                        String observaciones, int codSupervisor, int codProyecto,
                                        String estado, String imagen) {
        VisitaBody visitaBody = new VisitaBody(codigoPublicidad, codigoProblema, observaciones,
                codSupervisor, codProyecto, estado, imagen);
        Call<Void> postRegistrarVisita = mVallasApi.postRegistrarVisita(visitaBody);
        postRegistrarVisita.enqueue(new Callback<Void>() {
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
                        showError();
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

                    Toast.makeText(RegistrarVisitaActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("Registrar_visita3", " " + response.body());
                    Log.e("Registrar_visita4", " " + response.message());
                    Log.e("Registrar_visita5", " " + response.code());
                    Log.e("Registrar_visita6", " " + response.errorBody());
                    return;
                }
                mEnviarVisita.showComplete();
                Toast.makeText(RegistrarVisitaActivity.this, "Visita registrada!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrarVisitaActivity.this, ProyectosActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Registrar_visita", " " + t.getMessage());
            }
        });
    }

    /**
     * Establece la toolbar como action bar
     */
    private void setToolbar() {
        setSupportActionBar(mToolbar);
        setTitle("Detalle de publicidad");
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Establecer icono del drawer toggle
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void showError() {
        showSnackbar(mContentRegistrarVisita, "Error en la conexión!");
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        Intent data = getIntent();
        LatLng latlng;
        // Add a marker in Sydney and move the camera
        if (data != null) {
            latlng = new LatLng(data.getDoubleExtra("latitud", 0f),
                    data.getDoubleExtra("longitud", 0f));
        } else {
            latlng = new LatLng(14.651306666666668, -90.45510666666665);
        }
        mMap.addMarker(new MarkerOptions().position(latlng).title(data.getStringExtra("descripcion")).icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        float zoomLevel = 16;
        mMap.setMinZoomPreference(zoomLevel);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mMapView != null) {
            mMapView.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        if (mMapView != null) {
            try {
                mMapView.onDestroy();
            } catch (NullPointerException np) {
                Log.e("DetallePublicidad", "Error while attempting onDestroy", np);
            }
        }
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (mMapView != null) {
            mMapView.onLowMemory();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mMapView != null) {
            //mMapView.onSaveInstanceState(outState);
        }
    }

    @OnClick(R.id.tomarFotografia)
    void OnClickTomarFotografia() {
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

    private void registrarVisita() {
        new MaterialDialog.Builder(this)
                .title(R.string.dialog_title_reportar_visita)
                .content(R.string.dialog_content_reportar_visita)
                .positiveText(R.string.dialog_positive)
                .negativeText(R.string.dialog_negative)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        /**
                         * Registrar visita
                         */
                    }
                })
                .show();
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            bitMap = (Bitmap) extras.get("data");
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
            array = outStream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
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

}
