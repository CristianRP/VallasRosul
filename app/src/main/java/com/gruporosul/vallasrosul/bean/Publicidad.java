package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 24/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Publicidad {
    @SerializedName("CodigoPublicidad")
    private int codigoPublicidad;
    @SerializedName("Descripcion")
    private String descripcion;
    @SerializedName("Latitud")
    private double latitud;
    @SerializedName("Longitud")
    private double longitud;
    @SerializedName("Estado")
    private String estado;
    @SerializedName("CodigoProyecto")
    private int codProyecto;
    @SerializedName("CodigoContrato")
    private int codContrato;
    @SerializedName("Imagen")
    private String imagen;

    public static List<Publicidad> PUBLICIDADES = new ArrayList<>();

    public static Publicidad getPublicidad(int id) {
        for (Publicidad publicidad: PUBLICIDADES) {
            if (publicidad.getCodigoPublicidad() == id) {
                return publicidad;
            }
        }
        return null;
    }

    public Publicidad() {
    }

    public Publicidad(int codigoPublicidad, String descripcion, double latitud,
                      double longitud, String estado, int codProyecto,
                      int codContrato, String imagen) {
        this.codigoPublicidad = codigoPublicidad;
        this.descripcion = descripcion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.codProyecto = codProyecto;
        this.codContrato = codContrato;
        this.imagen = imagen;
    }

    public int getCodigoPublicidad() {
        return codigoPublicidad;
    }

    public void setCodigoPublicidad(int codigoPublicidad) {
        this.codigoPublicidad = codigoPublicidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCodProyecto() {
        return codProyecto;
    }

    public void setCodProyecto(int codProyecto) {
        this.codProyecto = codProyecto;
    }

    public int getCodContrato() {
        return codContrato;
    }

    public void setCodContrato(int codContrato) {
        this.codContrato = codContrato;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
