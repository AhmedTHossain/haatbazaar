package com.apptechbd.haatbazaar.utils;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        setLocale(getSavedLocale());
    }

    protected void setLocale(Locale locale) {
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    protected Locale getSavedLocale() {
        String lang = sharedPreferences.getString("language", "");
        return lang.equals("bn") ? new Locale("bn") : Locale.ENGLISH;
    }

    protected void saveLocale(String lang) {
        sharedPreferences.edit().putString("language", lang).apply();
    }

    protected void saveStartFragmentForAdmin(String fragment){
        sharedPreferences.edit().putString("start_fragment", fragment).apply();
    }

    protected String getStartFragmentForAdmin(){
        return sharedPreferences.getString("start_fragment", "");
    }

    protected void getSavedColorScheme(){
        int colorScheme = sharedPreferences.getInt("color_scheme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(colorScheme);
    }

    protected void saveSignInStatus(Boolean status){
        sharedPreferences.edit().putBoolean("sign_in_status", status).apply();
    }

    protected Boolean getSignInStatus(){
        return sharedPreferences.getBoolean("sign_in_status", false);
    }

    protected void saveSignedInUserType(String type){
        sharedPreferences.edit().putString("user_type", type).apply();
    }

    protected String getSignedInUserType(){
        return sharedPreferences.getString("user_type", "");
    }
}

