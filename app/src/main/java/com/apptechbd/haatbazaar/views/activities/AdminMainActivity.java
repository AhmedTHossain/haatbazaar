package com.apptechbd.haatbazaar.views.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAdminMainBinding;
import com.apptechbd.haatbazaar.interfaces.OnLanguageChangeListener;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.viewmodels.AdminMainViewModel;
import com.apptechbd.haatbazaar.views.fragments.admin.AnalyticsFragment;
import com.apptechbd.haatbazaar.views.fragments.admin.MoreFragment;

import java.util.Locale;

public class AdminMainActivity extends BaseActivity implements OnLanguageChangeListener {
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
        viewModel.replaceFragment(new AnalyticsFragment(), getSupportFragmentManager());
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(AdminMainViewModel.class);
        viewModel.onBottomNavMenuItemSelect(binding, getSupportFragmentManager());
    }

    @Override
    public void onLanguageChange(String language) {
        switch (language) {
            case "bn":
                saveLocale("bn");
                setLocale(new Locale("bn"));
                break;
            case "en":
                saveLocale("en");
                setLocale(Locale.ENGLISH);
                break;
        }
        recreate();
        viewModel.replaceFragment(new MoreFragment(), getSupportFragmentManager());
    }
}