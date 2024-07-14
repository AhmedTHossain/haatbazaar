package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentSetCustomerInfoBinding;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;

public class SetCustomerInfoFragment extends Fragment {
    private FragmentSetCustomerInfoBinding binding;
    private HomeViewModel viewModel;

    public SetCustomerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetCustomerInfoBinding.inflate(inflater, container, false);

        initViewModel();

        return binding.getRoot();
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.buttonText.setValue(getString(R.string.set_customers_information_disclaimer));
        viewModel.setButtonEnabled(false);
    }
}