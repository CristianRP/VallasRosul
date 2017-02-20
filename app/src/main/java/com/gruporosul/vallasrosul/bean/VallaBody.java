package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cristian Ramírez on 9/02/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class VallaBody {
    @SerializedName("Descripcion")
    public String descripcion;
    @SerializedName("Latitud")
    public String latitud;
    @SerializedName("Longitud")
    public String longitud;
    @SerializedName("CodigoProyecto")
    public Integer codigoProyecto;
    @SerializedName("CodigoContrato")
    public Integer codigoContrato;
    @SerializedName("Alto")
    public String alto;
    @SerializedName("Ancho")
    public String ancho;
    @SerializedName("Direccion")
    public String direccion;
    @SerializedName("imagen")
    public String imagen;
    @SerializedName("demo")
    public String demo;
    @SerializedName("periodo")
    public String periodo;
    @SerializedName("codigoBusqueda")
    public String codigoBusqueda;
    @SerializedName("codigoZona")
    public Integer codigoZona;

    public VallaBody() {
    }

    public VallaBody(String descripcion, String latitud, String longitud, Integer codigoProyecto,
                     Integer codigoContrato, String alto, String ancho, String direccion,
                     String imagen, String demo, String periodo, String codigoBusqueda,
                     Integer codigoZona) {
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.codigoProyecto = codigoProyecto;
        this.codigoContrato = codigoContrato;
        this.alto = alto;
        this.ancho = ancho;
        this.direccion = direccion;
        this.imagen = imagen;
        this.demo = demo;
        this.periodo = periodo;
        this.codigoBusqueda = codigoBusqueda;
        this.codigoZona = codigoZona;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(Integer codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public Integer getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(Integer codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getAlto() {
        return alto;
    }

    public void setAlto(String alto) {
        this.alto = alto;
    }

    public String getAncho() {
        return ancho;
    }

    public void setAncho(String ancho) {
        this.ancho = ancho;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDemo() {
        return demo;
    }

    public void setDemo(String demo) {
        this.demo = demo;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCodigoBusqueda() {
        return codigoBusqueda;
    }

    public void setCodigoBusqueda(String codigoBusqueda) {
        this.codigoBusqueda = codigoBusqueda;
    }

    public Integer getCodigoZona() {
        return codigoZona;
    }

    public void setCodigoZona(Integer codigoZona) {
        this.codigoZona = codigoZona;
    }
}
