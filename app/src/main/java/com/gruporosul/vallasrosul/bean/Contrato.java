package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 31/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Contrato {

    @SerializedName("codigocontrato")
    private int codigoContrato;
    @SerializedName("descripcion")
    private String descripcion;
    @SerializedName("Precio")
    private float precio;
    @SerializedName("FechaInicio")
    private String fechaInicio;
    @SerializedName("FechaFIn")
    private String fechaFin;
    @SerializedName("CodigoProyecto")
    private int codigoProyecto;


    public static List<Contrato> CONTRATOS = new ArrayList<>();

    public Contrato() {
    }

    public Contrato(int codigoContrato, String descripcion, float precio,
                    String fechaInicio, String fechaFin, int codigoProyecto) {
        this.codigoContrato = codigoContrato;
        this.descripcion = descripcion;
        this.precio = precio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.codigoProyecto = codigoProyecto;
    }

    public int getCodigoContrato() {
        return codigoContrato;
    }

    public void setCodigoContrato(int codigoContrato) {
        this.codigoContrato = codigoContrato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(int codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }
}
