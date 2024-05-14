package com.apptechbd.haatbazaar.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentAnalyticsBinding;
import com.apptechbd.haatbazaar.utils.DateUtil;

public class AnalyticsFragment extends Fragment {
    private FragmentAnalyticsBinding binding;
    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater,container,false);

        binding.chipgroup.check(R.id.chip_daily);
        binding.textDateRange.setText(DateUtil.getTodayDate());

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}