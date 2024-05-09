package com.apptechbd.haatbazaar.utils;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("LanguagePrefs", MODE_PRIVATE);
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
}

