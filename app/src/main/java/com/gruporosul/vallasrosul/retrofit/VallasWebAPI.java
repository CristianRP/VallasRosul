package com.gruporosul.vallasrosul.retrofit;

import com.gruporosul.vallasrosul.bean.Contrato;
import com.gruporosul.vallasrosul.bean.Problema;
import com.gruporosul.vallasrosul.bean.Proyecto;
import com.gruporosul.vallasrosul.bean.Publicidad;
import com.gruporosul.vallasrosul.bean.ResponseLogin;
import com.gruporosul.vallasrosul.bean.Supervisor;
import com.gruporosul.vallasrosul.bean.Tarea;
import com.gruporosul.vallasrosul.bean.VallaBody;
import com.gruporosul.vallasrosul.bean.VisitaBody;
import com.gruporosul.vallasrosul.bean.Visitas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Cristian Ramírez on 19/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public interface VallasWebAPI {
    // TODO: Cambiar host por "10.0.0.2" para Genymotion.
    // TODO: Cambiar host por "10.0.0.3" para AVD.
    // TODO: Cambiar host por IP de tu PC para dispositivo real.

    public static final String BASE_URL = "http://200.6.245.76:8095/";

    @POST("token")
    @FormUrlEncoded
    Call<ResponseLogin> login(@Field("grant_type") String grantType,
                              @Field("username") String username,
                              @Field("password") String password);

    @GET("api/VistaVisitas")
    Call<Visitas> getVisitas(@Query("fecha1") String fecha1, @Query("fecha2") String fecha2);

    @GET("api/VistasProyectos/Proyectos")
    Call<List<Proyecto>> getProyectos();

    @GET("api/VistasProyectos/Publicidad")
    Call<List<Publicidad>> getPublicidad(@Query("codigoContrato") int codigoContrato,
                                         @Query("codigoProyecto") int codigoProyecto);

    @POST("api/Visitas/IngresoVisitas")
    Call<Void> postRegistrarVisita(@Body VisitaBody visitaBody);

    @GET("api/VistaProblemaVisitas")
    Call<List<Problema>> getProblemas();

    @GET("api/Supervisores/GetSupervisorLogeado")
    Call<List<Supervisor>> getSupervisorLogeado(@Query("username") String username);

    @GET("api/Contratos/ContratoProyecto")
    Call<List<Contrato>> getContratos(@Query("codigoProyecto") int codigoProyecto);

    @GET("api/Visita/Alertas")
    Call<List<Tarea>> getTareas(@Query("codigoZona") int zona);

    @GET("api/Proyectos/Proyectos")
    Call<List<Proyecto>> getListadoProyectos();

    @POST("api/Publicidad/Publicidad")
    Call<Void> postCrearValla(@Body VallaBody vallaBody);

}
