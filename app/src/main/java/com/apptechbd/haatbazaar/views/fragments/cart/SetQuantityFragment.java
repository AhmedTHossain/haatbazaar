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
import com.apptechbd.haatbazaar.interfaces.EnableOrDisableSetPriceButtonListener;
import com.apptechbd.haatbazaar.interfaces.OnQuantityAddClickListener;
import com.apptechbd.haatbazaar.interfaces.OnQuantitySubtractClickListener;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.models.Quantity;

import java.util.ArrayList;

public class SetQuantityFragment extends Fragment implements OnQuantityAddClickListener, OnQuantitySubtractClickListener {

    private FragmentSetQuantityBinding binding;
    private ArrayList<String> categoriesPurchased;
    private ArrayList<Quantity> quantitiesPurchased = new ArrayList<>();
    private QuantityAdapter adapter;
    private EnableOrDisableSetPriceButtonListener enableOrDisableSetPriceButtonListener;

    public SetQuantityFragment(ArrayList<String> categoriesPurchased, EnableOrDisableSetPriceButtonListener enableOrDisableSetPriceButtonListener) {
        this.categoriesPurchased = categoriesPurchased;
        this.enableOrDisableSetPriceButtonListener = enableOrDisableSetPriceButtonListener;

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

    private void setAccounts(ArrayList<String > categoriesPurchased) {
        binding.recyclerviewQuantity.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewQuantity.setHasFixedSize(true);

        adapter = new QuantityAdapter(categoriesPurchased,this,this);
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

    private void setQuantity(int position, int quantity){
        for (Quantity quantityPurchased : quantitiesPurchased){
            if (quantityPurchased.getName().equals(categoriesPurchased.get(position)))
                quantityPurchased.setQuantity(quantity);
        }
        checkIfAnyQuantityAdded();
    }

    private void checkIfAnyQuantityAdded(){
        int isAnyQuantityAdded = 0;
        for (Quantity quantityPurchased : quantitiesPurchased){
            if (quantityPurchased.getQuantity() > 0)
                isAnyQuantityAdded++;
        }
        if (isAnyQuantityAdded > 0)
            enableOrDisableSetPriceButtonListener.onEnableOrDisableSetPriceButton(true);
        else
            enableOrDisableSetPriceButtonListener.onEnableOrDisableSetPriceButton(false);
    }
}