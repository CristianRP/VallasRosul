package com.gruporosul.vallasrosul.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Cristian Ramírez on 23/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class LoginBody {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;

    public LoginBody() {
    }

    public LoginBody(String username, String password) {
        this.username = username;
        this.password = password;
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

}
