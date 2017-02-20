package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 24/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Proyecto {
    @SerializedName("CodigoProyecto")
    private int codigoProyecto;
    @SerializedName("Descripcion")
    private String descripcion;
    @SerializedName("UrlImagen")
    private String urlImagen;

    public static List<Proyecto> PROYECTOS = new ArrayList<>();


    public static List<Proyecto> ZONAS = new ArrayList<>();

    public Proyecto() {
    }

    public Proyecto(int codigoProyecto, String descripcion, String urlImagen) {
        this.codigoProyecto = codigoProyecto;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }

    public int getCodigoProyecto() {
        return codigoProyecto;
    }

    public void setCodigoProyecto(int codigoProyecto) {
        this.codigoProyecto = codigoProyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
