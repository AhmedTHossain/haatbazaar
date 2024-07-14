package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.PriceAdapter;
import com.apptechbd.haatbazaar.adapters.QuantityAdapter;
import com.apptechbd.haatbazaar.adapters.Sale;
import com.apptechbd.haatbazaar.databinding.FragmentSetPriceBinding;
import com.apptechbd.haatbazaar.interfaces.OnPriceEnteredListener;
import com.apptechbd.haatbazaar.models.Quantity;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;

import java.util.ArrayList;

public class SetPriceFragment extends Fragment implements OnPriceEnteredListener {
    private FragmentSetPriceBinding binding;
    private HomeViewModel viewModel;
    private ArrayList<Quantity> quantitiesPurchased = new ArrayList<>();
    private ArrayList<String> cartList = new ArrayList<>();
    private PriceAdapter adapter;
    private ArrayList<Sale> salesList = new ArrayList<>();

    public SetPriceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetPriceBinding.inflate(inflater, container, false);

        initViewModel();
        setCart(cartList);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.buttonText.setValue(getString(R.string.set_the_price));
        viewModel.setButtonEnabled(false);
    }

    private void setCart(ArrayList<String> cartList) {
        binding.recyclerviewPrice.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewPrice.setHasFixedSize(true);
        Log.d("SetPriceFragment", "number of items in cart = " + cartList.size());
        adapter = new PriceAdapter(cartList, this);
        binding.recyclerviewPrice.setAdapter(adapter);
    }

    private void initViewModel() {
        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.quantitiesPurchased = viewModel.getQuantitiesPurchased();
        Log.d("SetPriceFragment", quantitiesPurchased.get(0).getName() + "s purchased: " + quantitiesPurchased.get(0).getQuantity());

        cartList = new ArrayList<>();
        for (Quantity qty : quantitiesPurchased) {
            for (int i = 0; i < qty.getQuantity(); i++) {
                cartList.add(qty.getName());
                Sale sale = new Sale(qty.getName(), 0, "");
                salesList.add(sale);
            }
        }
        viewModel.setSalesList(salesList);
    }

    @Override
    public void onPriceEntered(int price, int position) {
        salesList.get(position).setPrice(price);
        viewModel.setSalesList(salesList);

        boolean priceIsEmpty = true;
        for (Sale sale : salesList)
            if (sale.getPrice() == 0)
                priceIsEmpty = false;

        viewModel.setButtonEnabled(priceIsEmpty);
    }
}