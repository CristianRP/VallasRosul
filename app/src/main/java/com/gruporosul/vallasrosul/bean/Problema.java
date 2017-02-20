package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 27/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Problema {
    @SerializedName("CodigoProblema")
    private int codigoProblema;
    @SerializedName("Descripcion")
    private String descripcion;
    @SerializedName("Estado")
    private String estado;

    public static List<Problema> PROBLEMAS = new ArrayList<>();

    public Problema() {
    }

    public Problema(int codigoProblema, String descripcion, String estado) {
        this.codigoProblema = codigoProblema;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getCodigoProblema() {
        return codigoProblema;
    }

    public void setCodigoProblema(int codigoProblema) {
        this.codigoProblema = codigoProblema;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
