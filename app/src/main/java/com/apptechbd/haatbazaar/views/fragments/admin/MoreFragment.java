package com.apptechbd.haatbazaar.views.fragments.admin;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

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

public class MoreFragment extends Fragment implements View.OnClickListener {
    private FragmentMoreBinding binding;
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

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==binding.menuItemLogOut.getId()){
            //ToDo: Signout from firebase
            startActivity(new Intent(requireActivity(), LoginActivity.class));
            requireActivity().finish();
        } else if (v.getId()==binding.menuItemLanguage.getId()){
            showLanguageChangeDialog();
        } else if (v.getId()==binding.menuItemColorscheme.getId()){
            showColorSchemeChangeDialog();
        }
    }

    private void showLanguageChangeDialog() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext()); // Use MaterialAlertDialogBuilder for Material Design theme

        LayoutInflater li = LayoutInflater.from(requireActivity());
        View view = li.inflate(R.layout.language_change_dialog, null);

        builder.setView(view);
        builder.setTitle("Choose Language");

        builder.setCancelable(false)
                .setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        RadioGroup radioGroup = view.findViewById(R.id.radiogroup_language);
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

        builder.setCancelable(false)
                .setPositiveButton("SET", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

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