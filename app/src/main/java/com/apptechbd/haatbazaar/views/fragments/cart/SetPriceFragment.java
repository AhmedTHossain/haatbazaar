package com.apptechbd.haatbazaar.views.fragments.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentSetPriceBinding;

public class SetPriceFragment extends Fragment {
    private FragmentSetPriceBinding binding;

    public SetPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetPriceBinding.inflate(inflater, container, false);
        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}