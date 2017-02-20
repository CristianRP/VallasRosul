package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cristian Ramírez on 20/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class Supervisor {

    @SerializedName("idUsuario")
    private int idUsuario;
    @SerializedName("nombreUsuario")
    private String username;
    @SerializedName("userPassword")
    private String password;
    @SerializedName("idDispositivo")
    private String idDispositivo;
    @SerializedName("primerNombre")
    private String primerNombre;
    @SerializedName("segundoNombre")
    private String segundoNombre;
    @SerializedName("primerApellido")
    private String primerApellido;
    @SerializedName("segundoApellido")
    private String segundoApellido;
    @SerializedName("CodigoExterno")
    private int codigoExterno;
    @SerializedName("Estado")
    private String estado;

    public static List<Supervisor> SUPERVISORES = new ArrayList<>();

    public Supervisor() {
    }

    public Supervisor(int idUsuario, String username, String password, String idDispositivo,
                      String primerNombre, String segundoNombre, String primerApellido,
                      String segundoApellido, int codigoExterno, String estado) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.idDispositivo = idDispositivo;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.codigoExterno = codigoExterno;
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdDispositivo() {
        return idDispositivo;
    }

    public void setIdDispositivo(String idDispositivo) {
        this.idDispositivo = idDispositivo;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public int getCodigoExterno() {
        return codigoExterno;
    }

    public void setCodigoExterno(int codigoExterno) {
        this.codigoExterno = codigoExterno;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
