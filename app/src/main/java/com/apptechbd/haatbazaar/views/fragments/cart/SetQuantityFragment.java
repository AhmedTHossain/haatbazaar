package com.apptechbd.haatbazaar.views.fragments.cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.QuantityAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentSetQuantityBinding;
import com.apptechbd.haatbazaar.interfaces.OnQuantityAddClickListener;
import com.apptechbd.haatbazaar.interfaces.OnQuantitySubtractClickListener;
import com.apptechbd.haatbazaar.models.Quantity;
import com.apptechbd.haatbazaar.views.activities.MainActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class SetQuantityFragment extends Fragment implements OnQuantityAddClickListener, OnQuantitySubtractClickListener {

    private FragmentSetQuantityBinding binding;
    private ArrayList<String> categoriesPurchased;
    private ArrayList<Quantity> quantitiesPurchased = new ArrayList<>();
    private QuantityAdapter adapter;

    public SetQuantityFragment(ArrayList<String> categoriesPurchased) {
        this.categoriesPurchased = categoriesPurchased;

        for (String category : categoriesPurchased) {
            Quantity quantity = new Quantity(category, 0);
            quantitiesPurchased.add(quantity);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetQuantityBinding.inflate(inflater, container, false);

        setAccounts(categoriesPurchased);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void setAccounts(ArrayList<String> categoriesPurchased) {
        binding.recyclerviewQuantity.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewQuantity.setHasFixedSize(true);

        adapter = new QuantityAdapter(categoriesPurchased, this, this);
        binding.recyclerviewQuantity.setAdapter(adapter);
    }


    @Override
    public void onQuantityAddClick(int position, int quantity) {
        setQuantity(position, quantity);
    }

    @Override
    public void onQuantitySubtractClick(int position, int quantity) {
        setQuantity(position, quantity);
    }

    private void setQuantity(int position, int quantity) {
        if (quantity > 0) {
            for (Quantity quantityPurchased : quantitiesPurchased) {
                if (quantityPurchased.getName().equals(categoriesPurchased.get(position)))
                    quantityPurchased.setQuantity(quantity);
            }
            checkIfAnyQuantityAdded();
        } else
            showCategoryRemoveConfirmationDialog(position);
    }

    private void checkIfAnyQuantityAdded() {
        int isAnyQuantityAdded = 0;
        for (Quantity quantityPurchased : quantitiesPurchased) {
            if (quantityPurchased.getQuantity() > 0)
                isAnyQuantityAdded++;
        }
    }

    private void showCategoryRemoveConfirmationDialog(int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext()); // Use MaterialAlertDialogBuilder for Material Design theme
        builder.setTitle((requireContext().getString(R.string.remove_from_cart_title)));
        builder.setMessage(requireContext().getString(R.string.delete_animal_from_cart_disclaimer));
        // Set up the negative button
        builder.setNegativeButton((requireContext().getString(R.string.cancel)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                dialog.dismiss();
            }
        });
        // Set up the positive button
        builder.setPositiveButton((requireContext().getString(R.string.confirm)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle positive button click

                categoriesPurchased.remove(position);
                adapter.notifyItemRemoved(position);

                if (categoriesPurchased.isEmpty())
                    startActivity(new Intent(requireActivity(), MainActivity.class));

            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}