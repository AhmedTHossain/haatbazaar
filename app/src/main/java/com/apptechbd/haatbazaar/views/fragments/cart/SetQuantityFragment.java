package com.apptechbd.haatbazaar.views.fragments.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.AccountsAdapter;
import com.apptechbd.haatbazaar.adapters.QuantityAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentSetQuantityBinding;
import com.apptechbd.haatbazaar.models.Account;

import java.util.ArrayList;

public class SetQuantityFragment extends Fragment {
    private FragmentSetQuantityBinding binding;
    private ArrayList<String> categoriesPurchased;
    private QuantityAdapter adapter;
    public SetQuantityFragment(ArrayList<String> categoriesPurchased) {
        this.categoriesPurchased = categoriesPurchased;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetQuantityBinding.inflate(inflater, container, false);

        setAccounts(categoriesPurchased);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setAccounts(ArrayList<String > categoriesPurchased) {
        binding.recyclerviewQuantity.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewQuantity.setHasFixedSize(true);

        adapter = new QuantityAdapter(categoriesPurchased);
        binding.recyclerviewQuantity.setAdapter(adapter);
    }
}