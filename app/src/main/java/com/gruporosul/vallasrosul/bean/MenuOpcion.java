package com.gruporosul.vallasrosul.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 9/02/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class MenuOpcion {

    private int idOpcion;
    private String title;


    public static List<MenuOpcion> OPCIONES = new ArrayList<>();

    public MenuOpcion() {
    }

    public MenuOpcion(int idOpcion, String title) {
        this.idOpcion = idOpcion;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }
}
