package com.apptechbd.haatbazaar.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAddAccountBinding;
import com.apptechbd.haatbazaar.utils.PhoneNumberFormatter;

public class AddAccountActivity extends AppCompatActivity {
    private ActivityAddAccountBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityAddAccountBinding.inflate(getLayoutInflater(), getWindow().getDecorView().findViewById(android.R.id.content), false);

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}