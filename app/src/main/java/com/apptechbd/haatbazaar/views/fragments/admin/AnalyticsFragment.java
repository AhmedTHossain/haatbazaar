package com.apptechbd.haatbazaar.views.fragments.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentAnalyticsBinding;
import com.apptechbd.haatbazaar.utils.DateUtil;

import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class AnalyticsFragment extends Fragment {
    private FragmentAnalyticsBinding binding;

    public AnalyticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAnalyticsBinding.inflate(inflater, container, false);

        binding.chipgroup.check(R.id.chip_daily);
        binding.textDateRange.setText(DateUtil.getTodayDate());

        ChipGroup chipGroup = binding.chipgroup;
        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup chipGroup, @NonNull List<Integer> list) {
                String dateRange = "";
                if (chipGroup.getCheckedChipId() == R.id.chip_weekly) {
                    dateRange = DateUtil.getLastPeriod("week");
                } else if (chipGroup.getCheckedChipId() == R.id.chip_monthly) {
                    dateRange = DateUtil.getLastPeriod("month");
                } else if (chipGroup.getCheckedChipId() == R.id.chip_custom) {
                    DateUtil.showMaterialDateRangePicker(getParentFragmentManager(), binding.textDateRange, binding.chipCustom);
                } else {
                    dateRange = DateUtil.getTodayDate();
                }

                if (chipGroup.getCheckedChipId() != R.id.chip_custom)
                    binding.textDateRange.setText(dateRange);
            }
        });

        // Inflate the layout for this fragment
        return binding.getRoot();
    }
}