package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 9/02/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Tarea {
    @SerializedName("IdAlerta")
    @Expose
    public Integer idAlerta;
    @SerializedName("CodigoBusqueda")
    @Expose
    public String codigoBusqueda;
    @SerializedName("Direccion")
    @Expose
    public String direccion;
    @SerializedName("CodigoPublicidad")
    @Expose
    public Integer codigoPublicidad;
    @SerializedName("DescripcionPublicidad")
    @Expose
    public String descripcionPublicidad;
    @SerializedName("Medidas")
    @Expose
    public String medidas;
    @SerializedName("Contrato")
    @Expose
    public String contrato;
    @SerializedName("FechaInicioContrato")
    @Expose
    public String fechaInicioContrato;
    @SerializedName("FechaFinContrato")
    @Expose
    public String fechaFinContrato;
    @SerializedName("CodigoContrato")
    @Expose
    public Integer codigoContrato;
    @SerializedName("Latitud")
    @Expose
    public String latitud;
    @SerializedName("Longitud")
    @Expose
    public String longitud;
    @SerializedName("Imagen")
    @Expose
    public String imagen;
    @SerializedName("CalendarioPeriodo")
    @Expose
    public String calendarioPeriodo;
    @SerializedName("codigozona")
    @Expose
    public Integer codigozona;
    @SerializedName("FechaFinAlerta")
    @Expose
    public String fechaFinAlerta;
    @SerializedName("FechaInicioAlerta")
    @Expose
    public String fechaInicioAlerta;
    @SerializedName("VencimientoTarea")
    @Expose
    public String vencimientoTarea;
    @SerializedName("DiasParaVencer")
    @Expose
    public Integer diasParaVencer;
    @SerializedName("Dias_Contrato")
    @Expose
    public Integer diasContrato;
    @SerializedName("Porcentaje")
    @Expose
    public Integer porcentaje;
    @SerializedName("DiasFaltantesParaVencer")
    @Expose
    public Integer diasFaltantesParaVencer;

    public static List<Tarea> TAREAS = new ArrayList<>();

    public static Tarea getTareas(String id) {
        for (Tarea tarea: TAREAS) {
            if (tarea.getCodigoBusqueda().equals(id)) {
                return tarea;
            }
        }
        return null;
    }

    public Tarea() {
    }

    public Tarea(Integer idAlerta, String codigoBusqueda, String direccion,
                 Integer codigoPublicidad, String descripcionPublicidad, String medidas,
                 String contrato, String fechaInicioContrato, String fechaFinContrato,
                 Integer codigoContrato, String latitud, String longitud, String imagen,
                 String calendarioPeriodo, Integer codigozona, String fechaFinAlerta,
                 String fechaInicioAlerta, String vencimientoTarea, Integer diasParaVencer,
                 Integer diasContrato, Integer porcentaje, Integer diasFaltantesParaVencer) {
        this.idAlerta = idAlerta;
        this.codigoBusqueda = codigoBusqueda;
        this.direccion = direccion;
        this.codigoPublicidad = codigoPublicidad;
        this.descripcionPublicidad = descripcionPublicidad;
        this.medidas = medidas;
        this.contrato = contrato;
        this.fechaInicioContrato = fechaInicioContrato;
        this.fechaFinContrato = fechaFinContrato;
        this.codigoContrato = codigoContrato;
        this.latitud = latitud;
        this.longitud = longitud;
        this.imagen = imagen;
        this.calendarioPeriodo = calendarioPeriodo;
        this.codigozona = codigozona;
        this.fechaFinAlerta = fechaFinAlerta;
        this.fechaInicioAlerta = fechaInicioAlerta;
        this.vencimientoTarea = vencimientoTarea;
        this.diasParaVencer = diasParaVencer;
        this.diasContrato = diasContrato;
        this.porcentaje = porcentaje;
        this.diasFaltantesParaVencer = diasFaltantesParaVencer;
    }

    public Integer getIdAlerta() {

        return idAlerta;
    }

    public void setIdAlerta(Integer idAlerta) {
        this.idAlerta = idAlerta;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCodigoPublicidad() {
        return codigoPublicidad;
    }

    public void setCodigoPublicidad(Integer codigoPublicidad) {
        this.codigoPublicidad = codigoPublicidad;
    }

    public String getDescripcionPublicidad() {
        return descripcionPublicidad;
    }

    public void setDescripcionPublicidad(String descripcionPublicidad) {
        this.descripcionPublicidad = descripcionPublicidad;
    }

    public String getMedidas() {
        return medidas;
    }

    public void setMedidas(String medidas) {
        this.medidas = medidas;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public String getFechaInicioContrato() {
        return fechaInicioContrato;
    }

    public void setFechaInicioContrato(String fechaInicioContrato) {
        this.fechaInicioContrato = fechaInicioContrato;
    }

    public String getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(String fechaFinContrato) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public Integer getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(Integer codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getCalendarioPeriodo() {
        return calendarioPeriodo;
    }

    public void setCalendarioPeriodo(String calendarioPeriodo) {
        this.calendarioPeriodo = calendarioPeriodo;
    }

    public Integer getCodigozona() {
        return codigozona;
    }

    public void setCodigozona(Integer codigozona) {
        this.codigozona = codigozona;
    }

    public String getFechaFinAlerta() {
        return fechaFinAlerta;
    }

    public void setFechaFinAlerta(String fechaFinAlerta) {
        this.fechaFinAlerta = fechaFinAlerta;
    }

    public String getFechaInicioAlerta() {
        return fechaInicioAlerta;
    }

    public void setFechaInicioAlerta(String fechaInicioAlerta) {
        this.fechaInicioAlerta = fechaInicioAlerta;
    }

    public String getVencimientoTarea() {
        return vencimientoTarea;
    }

    public void setVencimientoTarea(String vencimientoTarea) {
        this.vencimientoTarea = vencimientoTarea;
    }

    public Integer getDiasParaVencer() {
        return diasParaVencer;
    }

    public void setDiasParaVencer(Integer diasParaVencer) {
        this.diasParaVencer = diasParaVencer;
    }

    public Integer getDiasContrato() {
        return diasContrato;
    }

    public void setDiasContrato(Integer diasContrato) {
        this.diasContrato = diasContrato;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getDiasFaltantesParaVencer() {
        return diasFaltantesParaVencer;
    }

    public void setDiasFaltantesParaVencer(Integer diasFaltantesParaVencer) {
        this.diasFaltantesParaVencer = diasFaltantesParaVencer;
    }
}
