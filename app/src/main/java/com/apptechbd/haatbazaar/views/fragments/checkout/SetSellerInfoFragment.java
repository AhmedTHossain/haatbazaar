package com.apptechbd.haatbazaar.views.fragments.checkout;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.Sale;
import com.apptechbd.haatbazaar.adapters.SellerInfoAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentSetSellerInfoBinding;
import com.apptechbd.haatbazaar.interfaces.OnSellerInfoEnteredListener;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;

import java.util.ArrayList;

public class SetSellerInfoFragment extends Fragment implements OnSellerInfoEnteredListener {
    private FragmentSetSellerInfoBinding binding;
    private HomeViewModel viewModel;
    private ArrayList<Sale> salesList = new ArrayList<>();
    private SellerInfoAdapter adapter;
    private MaterialAlertDialogBuilder builder;
    private AlertDialog progressDialog;
    private SharedPreferences sharedPreferences;
    public ArrayList<Account> sellerAccounts = new ArrayList<>();

    public SetSellerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetSellerInfoBinding.inflate(inflater, container, false);

        initViewModel();

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.buttonText.setValue(getString(R.string.set_the_seller_name));
        viewModel.setButtonEnabled(false);
    }

    private void initViewModel() {
        String title = getString(R.string.retrieving_seller_accounts_title);
        String disclaimer = getString(R.string.retrieving_seller_accounts_disclaimer);
        progressDialog = new HelperClass().showProgressDialog(builder, title, disclaimer, progressDialog, requireContext());

        viewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        this.salesList = viewModel.getSalesList();
        Log.d("SetSellerInfoFragment", "number of items in sales list = " + salesList.get(0).getPrice());

        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        viewModel.getSupplierAccounts(getAccount().getAdmin());
        viewModel.supplierAccounts.observe(getViewLifecycleOwner(), supplierAccounts -> {
            if (!supplierAccounts.isEmpty()) {
                sellerAccounts = supplierAccounts;
                setCart(salesList, sellerAccounts);
                progressDialog.dismiss();
            }
        });

        viewModel.isSalesListUpdated.observe(getViewLifecycleOwner(), isSalesListUpdated -> {
            if (isSalesListUpdated) {
//                for (Sale sale : salesList){
//                    if (sale.getSeller().isEmpty())
//                        viewModel.setButtonEnabled(false);
//                }
                viewModel.setButtonEnabled(true);
            }
        });
    }

    private void setCart(ArrayList<Sale> salesList, ArrayList<Account> sellerAccounts) {
        binding.recyclerviewSellerInfo.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewSellerInfo.setHasFixedSize(true);
        Log.d("SetPriceFragment", "number of items in cart = " + salesList.size());
        Log.d("SetPriceFragment", "number of sellers in cart = " + sellerAccounts.size());
        adapter = new SellerInfoAdapter(salesList, sellerAccounts, requireContext(),this);
        binding.recyclerviewSellerInfo.setAdapter(adapter);
    }

    public Account getAccount() {
        String accountJson = sharedPreferences.getString("account", "");
        return new Gson().fromJson(accountJson, Account.class);
    }

    @Override
    public void onSellerInfoEntered(String sellerName, int position) {
        salesList.get(position).setSeller(sellerName);
        viewModel.setSalesList(salesList);

        boolean sellerNameIsEmpty = true;
        for (Sale sale : salesList)
            if (sale.getSeller().isEmpty())
                sellerNameIsEmpty = false;

        viewModel.setButtonEnabled(sellerNameIsEmpty);
    }
}