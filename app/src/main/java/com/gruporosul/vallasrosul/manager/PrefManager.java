package com.gruporosul.vallasrosul.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * Created by Cristian Ramírez on 19/01/2017.
 * Grupo Rosul
 * cristianramirezgt@gmail.com
 */


public class PrefManager {

    public static final String PREFS_NAME = "VALLAS_PREF";
    public static final String PREF_USERNAME = "PREF_USERNAME";
    public static final String PREF_PASSWORD = "PREF_PASSWORD";
    public static final String PREF_TOKEN = "PREF_TOKEN";

    private final SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static PrefManager INSTANCE;

    public static PrefManager get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new PrefManager(context);
        }
        return INSTANCE;
    }

    private PrefManager(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREF_TOKEN, null));
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveSupervisor(String username, String password, String token) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_USERNAME, username);
        editor.putString(PREF_PASSWORD, password);
        editor.putString(PREF_TOKEN, token);
        editor.apply();

        mIsLoggedIn = true;
    }

    public void logOut(){
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREF_USERNAME, null);
        editor.putString(PREF_PASSWORD, null);
        editor.putString(PREF_TOKEN, null);
        editor.apply();
    }

    public static String getString(Context mContext, String key){
        SharedPreferences pref = mContext.getSharedPreferences(PREFS_NAME, Activity.MODE_PRIVATE);
        return pref.getString(key, null);
    }

}
