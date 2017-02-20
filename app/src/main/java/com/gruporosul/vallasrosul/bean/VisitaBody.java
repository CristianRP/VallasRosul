package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cristian Ramírez on 30/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class VisitaBody {
    @SerializedName("codigoPublicidad")
    private int codigoPublicidad;
    @SerializedName("codigoProblema")
    private int codigoProblema;
    @SerializedName("observaciones")
    private String observaciones;
    @SerializedName("codSupervisor")
    private int codSupervisor;
    @SerializedName("codProyecto")
    private int codProyecto;
    @SerializedName("estado")
    private String estado;
    @SerializedName("imagen")
    private String imagen;

    public VisitaBody() {
    }

    public VisitaBody(int codigoPublicidad, int codigoProblema, String observaciones,
                      int codSupervisor, int codProyecto, String estado, String imagen) {
        this.codigoPublicidad = codigoPublicidad;
        this.codigoProblema = codigoProblema;
        this.observaciones = observaciones;
        this.codSupervisor = codSupervisor;
        this.codProyecto = codProyecto;
        this.estado = estado;
        this.imagen = imagen;
    }

    public int getCodigoPublicidad() {
        return codigoPublicidad;
    }

    public void setCodigoPublicidad(int codigoPublicidad) {
        this.codigoPublicidad = codigoPublicidad;
    }

    public int getCodigoProblema() {
        return codigoProblema;
    }

    public void setCodigoProblema(int codigoProblema) {
        this.codigoProblema = codigoProblema;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getCodSupervisor() {
        return codSupervisor;
    }

    public void setCodSupervisor(int codSupervisor) {
        this.codSupervisor = codSupervisor;
    }

    public int getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(int codProyecto) {
        this.codProyecto = codProyecto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
