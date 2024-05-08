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
import com.google.android.material.materialswitch.MaterialSwitch;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        setLocale(false);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        MaterialSwitch languageSwitchButton = findViewById(R.id.view_language_toggle);
        languageSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                setLocale(isChecked);
                Log.d("LoginActivity","is checked: "+isChecked);
            }
        });
    }

    private void setLocale(boolean isBengali) {
        Locale newLocale = isBengali ? new Locale("bn") : Locale.US;
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(newLocale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        // Update UI text elements based on the locale (optional)
        binding.buttonLogin.setText(getString(R.string.enter_button_text_english));
        binding.buttonForgotPassword.setText(getString(R.string.forgot_password_button_text_english));
        binding.inputEditTextPhone.setHint(getString(R.string.phone_input_field_hint_english));
        binding.inputEditTextPassword.setHint(getString(R.string.password_input_field_hint_english));
        // Repeat for other UI elements
    }

}