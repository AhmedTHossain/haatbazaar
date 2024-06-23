package com.apptechbd.haatbazaar.views.fragments.admin;

import static android.content.Context.MODE_PRIVATE;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.FragmentMoreBinding;
import com.apptechbd.haatbazaar.interfaces.OnLanguageChangeListener;
import com.apptechbd.haatbazaar.views.activities.AdminMainActivity;
import com.apptechbd.haatbazaar.views.activities.LoginActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.radiobutton.MaterialRadioButton;

public class MoreFragment extends Fragment implements View.OnClickListener {
    private FragmentMoreBinding binding;
    private SharedPreferences sharedPreferences;

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMoreBinding.inflate(inflater, container, false);

        binding.menuItemColorscheme.setOnClickListener(this);
        binding.menuItemLanguage.setOnClickListener(this);
        binding.menuItemLogOut.setOnClickListener(this);

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.menuItemLogOut.getId()) {
            //ToDo: Signout from firebase
            sharedPreferences.edit().putBoolean("sign_in_status", false).apply();
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        } else if (v.getId() == binding.menuItemLanguage.getId()) {
            showLanguageChangeDialog();
        } else if (v.getId() == binding.menuItemColorscheme.getId()) {
            showColorSchemeChangeDialog();
        }
    }

    private void showLanguageChangeDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext()); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(requireActivity());
        View view = li.inflate(R.layout.language_change_dialog, null);

        builder.setView(view);
        builder.setTitle("Choose Language");

        RadioGroup radioGroup = view.findViewById(R.id.radiogroup_language);

        switch (sharedPreferences.getString("language", "")) {
            case "bn":
                MaterialRadioButton radioBangla = radioGroup.findViewById(R.id.radio_bangla);
                radioBangla.setChecked(true);
                break;
            case "en":
                MaterialRadioButton radioEnglish = radioGroup.findViewById(R.id.radio_english);
                radioEnglish.setChecked(true);
                break;
        }

        builder.setCancelable(false)
                .setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_english) {
                            OnLanguageChangeListener listener = (OnLanguageChangeListener) getActivity();
                            listener.onLanguageChange("en");
                        } else if (selectedId == R.id.radio_bangla) {
                            OnLanguageChangeListener listener = (OnLanguageChangeListener) getActivity();
                            listener.onLanguageChange("bn");
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

    private void showColorSchemeChangeDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext()); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(requireActivity());
        View view = li.inflate(R.layout.color_scheme_change_dialog, null);

        builder.setView(view);
        builder.setTitle("Choose Color");

        RadioGroup radioGroup = view.findViewById(R.id.radio_group_color_scheme);

        switch (sharedPreferences.getInt("color_scheme", 0)) {
            case 1:
                MaterialRadioButton radioLight = radioGroup.findViewById(R.id.radio_light);
                radioLight.setChecked(true);
                break;
            case 2:
                MaterialRadioButton radioDark = radioGroup.findViewById(R.id.radio_dark);
                radioDark.setChecked(true);
                break;
            case -1:
                MaterialRadioButton radioSystemDefault = radioGroup.findViewById(R.id.radio_system_default);
                radioSystemDefault.setChecked(true);
                break;
        }

        builder.setCancelable(false)
                .setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selectedId = radioGroup.getCheckedRadioButtonId();
                        if (selectedId == R.id.radio_system_default) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                            sharedPreferences.edit().putInt("color_scheme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM).apply();
                        } else if (selectedId == R.id.radio_light) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                            sharedPreferences.edit().putInt("color_scheme", AppCompatDelegate.MODE_NIGHT_NO).apply();
                        } else if (selectedId == R.id.radio_dark) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                            sharedPreferences.edit().putInt("color_scheme", AppCompatDelegate.MODE_NIGHT_YES).apply();
                        }
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }
}