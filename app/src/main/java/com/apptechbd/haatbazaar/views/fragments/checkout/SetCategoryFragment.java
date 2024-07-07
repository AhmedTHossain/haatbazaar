package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentSetCategoryBinding;

public class SetCategoryFragment extends Fragment {
    private FragmentSetCategoryBinding binding;
    public SetCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetCategoryBinding.inflate(inflater, container, false);



        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}