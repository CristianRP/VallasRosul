package com.gruporosul.vallasrosul.retrofit;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by Cristian Ramírez on 23/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class ApiError {
    private String error;
    private String errorDescription;
    @SerializedName("Message")
    private String message;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApiError(String error, String errorDescription, String message) {
        this.error = error;
        this.errorDescription = errorDescription;
        this.message = message;
    }

    public static ApiError fromResponseBody(ResponseBody responseBody) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseBody.string(), ApiError.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}