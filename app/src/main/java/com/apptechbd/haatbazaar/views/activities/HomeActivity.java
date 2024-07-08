package com.apptechbd.haatbazaar.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityHomeBinding;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;
import com.apptechbd.haatbazaar.views.fragments.staff.CategorySelectFragment;

public class HomeActivity extends BaseActivity {
    private ActivityHomeBinding binding;
    private SharedPreferences sharedPreferences;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSavedColorScheme();
        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.buttonText.observe(this, buttonText -> {
            binding.buttonProceed.setText(buttonText);
        });

        viewModel.isButtonEnabled.observe(this, isButtonEnabled -> {
            Log.d("HomeActivity", "isButtonEnabled: " + isButtonEnabled);
            binding.buttonProceed.setEnabled(isButtonEnabled);
        });

        viewModel.setAccount(getAccount());
        viewModel.replaceFragment(new CategorySelectFragment(), getSupportFragmentManager(), "CategorySelectFragment");
    }
}