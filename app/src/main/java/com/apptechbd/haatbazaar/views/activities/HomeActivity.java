package com.apptechbd.haatbazaar.views.activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityHomeBinding;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;
import com.apptechbd.haatbazaar.views.fragments.checkout.SetPriceFragment;
import com.apptechbd.haatbazaar.views.fragments.checkout.SetQuantityFragment;
import com.apptechbd.haatbazaar.views.fragments.checkout.SetCategoryFragment;

public class HomeActivity extends BaseActivity implements View.OnClickListener {
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
        binding.buttonProceed.setOnClickListener(this);

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish(); // or super.onBackPressed() if you want default behavior
                }
            }
        });
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
        viewModel.replaceFragment(new SetCategoryFragment(), getSupportFragmentManager(), "SetCategoryFragment");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonProceed.getId()) {
            switch (binding.buttonProceed.getText().toString().toLowerCase()) {
                case "set the type":
                    viewModel.replaceFragment(new SetQuantityFragment(), getSupportFragmentManager(), "SetQuantityFragment");
                    break;
                case "set the quantity":
                    viewModel.replaceFragment(new SetPriceFragment(), getSupportFragmentManager(), "SetPriceFragment");
                    break;
                case "set the price":
                    break;
            }
        }
    }
}