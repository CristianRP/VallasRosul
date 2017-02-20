package com.gruporosul.vallasrosul.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 23/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Visitas {
    private int noVisita;
    private String fechaVisita;
    private String proyecto;
    private String descripcion;
    private String supervisor;
    private int codigoPublicidad;
    private String valla;
    private String problemaPublicidad;
    private String observaciones;
    private int noContrato;
    private String proveedor;
    private String fechaInicio;
    private String fechaFin;
    private float latitud;
    private float longitud;
    private String resultado;

    public static List<Visitas> VISITAS = new ArrayList<>();

    public Visitas() {
    }

    public Visitas(int noVisita, String fechaVisita, String proyecto, String descripcion,
                   String supervisor, int codigoPublicidad, String valla,
                   String problemaPublicidad, String observaciones, int noContrato,
                   String proveedor, String fechaInicio, String fechaFin, float latitud,
                   float longitud, String resultado) {
        this.noVisita = noVisita;
        this.fechaVisita = fechaVisita;
        this.proyecto = proyecto;
        this.descripcion = descripcion;
        this.supervisor = supervisor;
        this.codigoPublicidad = codigoPublicidad;
        this.valla = valla;
        this.problemaPublicidad = problemaPublicidad;
        this.observaciones = observaciones;
        this.noContrato = noContrato;
        this.proveedor = proveedor;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.latitud = latitud;
        this.longitud = longitud;
        this.resultado = resultado;
    }

    public int getNoVisita() {
        return noVisita;
    }

    public void setNoVisita(int noVisita) {
        this.noVisita = noVisita;
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public String getProyecto() {
        return proyecto;
    }

    public void setProyecto(String proyecto) {
        this.proyecto = proyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public int getCodigoPublicidad() {
        return codigoPublicidad;
    }

    public void setCodigoPublicidad(int codigoPublicidad) {
        this.codigoPublicidad = codigoPublicidad;
    }

    public String getValla() {
        return valla;
    }

    public void setValla(String valla) {
        this.valla = valla;
    }

    public String getProblemaPublicidad() {
        return problemaPublicidad;
    }

    public void setProblemaPublicidad(String problemaPublicidad) {
        this.problemaPublicidad = problemaPublicidad;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getNoContrato() {
        return noContrato;
    }

    public void setNoContrato(int noContrato) {
        this.noContrato = noContrato;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
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

    public float getLatitud() {
        return latitud;
    }

    public void setLatitud(float latitud) {
        this.latitud = latitud;
    }

    public float getLongitud() {
        return longitud;
    }

    public void setLongitud(float longitud) {
        this.longitud = longitud;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
