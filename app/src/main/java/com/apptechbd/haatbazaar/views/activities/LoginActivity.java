package com.apptechbd.haatbazaar.views.activities;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityLoginBinding;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Locale;

public class LoginActivity extends BaseActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialSwitch languageSwitchButton = findViewById(R.id.view_language_toggle);
        languageSwitchButton.setChecked(getSavedLocale().getLanguage().equals("bn"));
        languageSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    saveLocale("bn");
                    setLocale(new Locale("bn"));
                } else {
                    saveLocale("en");
                    setLocale(Locale.ENGLISH);
                }
                recreate(); // Reload the activity to apply the language change
            }
        });
    }
}