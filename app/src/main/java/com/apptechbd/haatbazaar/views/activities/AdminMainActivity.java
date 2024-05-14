package com.apptechbd.haatbazaar.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAdminMainBinding;
import com.apptechbd.haatbazaar.databinding.ActivityLoginBinding;
import com.apptechbd.haatbazaar.viewmodels.AdminMainViewModel;

public class AdminMainActivity extends AppCompatActivity {
    private ActivityAdminMainBinding binding;
    private AdminMainViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AdminMainViewModel.class);
        viewModel.onBottomNavMenuItemSelect(binding,getSupportFragmentManager());
    }
}