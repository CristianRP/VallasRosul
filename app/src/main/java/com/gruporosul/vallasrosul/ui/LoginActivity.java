package com.gruporosul.vallasrosul.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gruporosul.vallasrosul.R;
import com.gruporosul.vallasrosul.bean.ResponseLogin;
import com.gruporosul.vallasrosul.manager.PrefManager;
import com.gruporosul.vallasrosul.retrofit.ApiError;
import com.gruporosul.vallasrosul.retrofit.VallasWebAPI;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog mProgressDialog;

    private Retrofit mRestAdapter;
    private VallasWebAPI mVallasApi;

    /**
     * Bindings de view's (Libreria ButterKnife)
     */
    @BindView(R.id.txtLogin)
    TextView tituloLogin;
    @BindView(R.id.textUser)
    EditText textUser;
    @BindView(R.id.textPassword)
    EditText textPassword;
    @BindView(R.id.fabLogin)
    FloatingActionButton mFabLogin;
    @BindView(R.id.coordinatorLogin)
    CoordinatorLayout mCoodinatorLogin;
    @BindView(R.id.imgLogo)
    ImageView mImgLogo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        //mPrefManager = new PrefManager(getApplicationContext());

        /*if (mPrefManager.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }*/

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

        getDeviceResolution();

        tituloLogin.setText(Html.fromHtml(getString(R.string.content_login)));

        mFabLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnline()) {
                    showSnackbar(mCoodinatorLogin, "Sin accesso a internet!");
                    return;
                }
                attemptLogin();
            }
        });
    }

    /**
     * Verifica la densidad de pixeles de pantalla y setea una imagen
     * diferente
     * @return
     */
    private String getDeviceResolution()
    {
        int density = getApplicationContext().getResources().getDisplayMetrics().densityDpi;
        switch (density)
        {
            case DisplayMetrics.DENSITY_MEDIUM:
                tituloLogin.setText("");
                mImgLogo.setImageResource(0);
                mImgLogo.setImageResource(R.drawable.logo_grupo_rosul_hdpi);
                return "MDPI";
            case DisplayMetrics.DENSITY_HIGH:
                return "HDPI";
            case DisplayMetrics.DENSITY_LOW:
                mImgLogo.setImageResource(0);
                return "LDPI";
            case DisplayMetrics.DENSITY_XHIGH:
                return "XHDPI";
            case DisplayMetrics.DENSITY_TV:
                return "TV";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "XXHDPI";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "XXXHDPI";
            default:
                return "Unknown";
        }
    }

    /**
     * Verifica que el formulario este lleno y realiza la petición
     */
    private void attemptLogin() {

        textUser.setError(null);
        textPassword.setError(null);

        String username = textUser.getText().toString();
        String password = textPassword.getText().toString();
        String idDispositivo =
                Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            textPassword.setError(getString(R.string.error_password_field_required));
            focusView = textPassword;
            cancel = true;
        }

        if (TextUtils.isEmpty(username)) {
            textUser.setError(getString(R.string.error_user_field_required));
            focusView = textUser;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgressDialog();
            LoginRequest(username, password, idDispositivo);
        }

        Log.e("ID-DEVICE", idDispositivo);

    }

    @OnClick(R.id.imgLogo)
    void showIdDevice() {
        String idDispositivo =
                Settings.Secure.getString(LoginActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Snackbar.make(mCoodinatorLogin, idDispositivo, Snackbar.LENGTH_INDEFINITE).show();
    }

    /**
     * ProgresDialog prefabricado para su uso posterior
     */
    private void showProgressDialog() {
        mProgressDialog = new ProgressDialog(LoginActivity.this);
        mProgressDialog.setMessage(getString(R.string.action_loading));
        mProgressDialog.show();
    }


    /**
     * Realiza petición a travez de volley, para consultar el metodo LoginUsuario
     * del web-service SOAP, el cual espera los siguientes parametros:
     * @param username
     * @param password
     * @param idDispositivo
     */
    public void LoginRequest(final String username, final String password, final String idDispositivo) {
        Log.e("call", "ingreso1login");
        Call<ResponseLogin> loginCall = mVallasApi.login("password", username, password);
        loginCall.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                // Mostrar progreso
                mProgressDialog.dismiss();
                Log.e("call", "ingreso login");
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

                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                    showError();
                    Log.e("LoginAcitivity", " " + error);
                    return;
                }
                
                // Guardar afiliado en preferencias
                Log.e("token", "" +response.body().getAccess_token());
                PrefManager.get(LoginActivity.this).saveSupervisor(username, password, response.body().getAccess_token());

                startActivity(new Intent(LoginActivity.this, ProyectosActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    private void showError() {
        showSnackbar(mCoodinatorLogin, getString(R.string.login_error));
        textUser.setText("");
        textPassword.setText("");
        textUser.requestFocus();
    }
    /*public void LoginRequest(final String username, final String password, final String idDispositivo) {

        StringRequest strReq = new StringRequest(
                Request.Method.POST,
                url_login,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, response);
                        mProgressDialog.dismiss();
                        if (!TextUtils.isEmpty(response) && !response.contains("error")) {
                            mPrefManager.createLoginSession(username, password, idDispositivo);
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        } else {
                            showSnackbar(mCoodinatorLogin, getString(R.string.login_error));
                            textUser.setText("");
                            textPassword.setText("");
                            textUser.requestFocus();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                mProgressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", username);
                params.put("password", password);
                params.put("id", idDispositivo);
                return params;
            }

        };

        // Se añade la petición a la cola de peticiones de volley
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }*/

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

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

}
