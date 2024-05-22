package com.apptechbd.haatbazaar.views.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityCartBinding;
import com.apptechbd.haatbazaar.databinding.ActivityMainBinding;
import com.apptechbd.haatbazaar.interfaces.EnableOrDisableSetPriceButtonListener;
import com.apptechbd.haatbazaar.utils.BaseActivity;
import com.apptechbd.haatbazaar.viewmodels.CartViewModel;
import com.apptechbd.haatbazaar.views.fragments.cart.SetQuantityFragment;

import java.util.ArrayList;
import java.util.Locale;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private CartViewModel viewModel;
    private ArrayList<String> categoriesPurchased;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater(), getWindow().getDecorView().findViewById(android.R.id.content), false);

        categoriesPurchased = new ArrayList<>();
        categoriesPurchased = getIntent().getStringArrayListExtra("categoriesPurchased");

        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        saveLocale("bn");
        setLocale(new Locale("bn"));

        initViewModel();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        viewModel.replaceFragment(new SetQuantityFragment(categoriesPurchased), getSupportFragmentManager());
    }
}