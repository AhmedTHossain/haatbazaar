package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.Sale;
import com.apptechbd.haatbazaar.databinding.FragmentSetCustomerInfoBinding;
import com.apptechbd.haatbazaar.models.Customer;
import com.apptechbd.haatbazaar.models.Invoice;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.apptechbd.haatbazaar.viewmodels.HomeViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class SetCustomerInfoFragment extends Fragment implements View.OnClickListener {
    private FragmentSetCustomerInfoBinding binding;
    private HomeViewModel viewModel;
    private AlertDialog progressDialog;
    private MaterialAlertDialogBuilder builder;

    public SetCustomerInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSetCustomerInfoBinding.inflate(inflater, container, false);

        initViewModel();

        binding.buttonGetCustomerDetail.setOnClickListener(this);
        binding.inputEditTextCustomerCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0)
                    binding.buttonGetCustomerDetail.setEnabled(true);
                else
                    binding.buttonGetCustomerDetail.setEnabled(false);
            }
        });

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
        binding.buttonGetCustomerDetail.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        Log.d("SetCustomerInfoFragment", "On click called: YES!");
        if (v.getId() == binding.buttonGetCustomerDetail.getId()) {
            // Hide the soft keyboard
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(binding.inputEditTextCustomerCode.getWindowToken(), 0);

            String title = getString(R.string.retrieving_customer_info_title);
            String disclaimer = getString(R.string.retrieving_customer_info_disclaimer);
            progressDialog = new HelperClass().showProgressDialog(builder, title, disclaimer, progressDialog, requireContext());

            viewModel.getCustomer(Objects.requireNonNull(binding.inputEditTextCustomerCode.getText()).toString());
            viewModel.customer.observe(this, customer -> {
                if (customer != null) {
                    Log.d("SetCustomerInfoFragment", "customer name: " + customer.getName());
                    binding.inputEditTextCustomersName.setText(customer.getName());
                    binding.inputEditTextCustomersAddress.setText(customer.getAddress());
                    binding.buttonGetCustomerDetail.setEnabled(false);
                    viewModel.setButtonEnabled(true);
                    createInvoice(customer);
                } else {
                    binding.inputEditTextCustomersName.setText(null);
                    binding.inputEditTextCustomersAddress.setText(null);
                    viewModel.setButtonEnabled(false);
                    new HelperClass().showSnackBar(binding.getRoot(), getString(R.string.customer_not_found_message));
                    Log.d("SetCustomerInfoFragment", "customer not found");
                }
                progressDialog.dismiss();
            });
        }
    }

    private void createInvoice(Customer customer) {
        Invoice invoice = new Invoice();

        ArrayList<Sale> saleArrayList = calculateSaleTotals(viewModel.getSalesList());
        int totalAmount = 0;
        StringBuilder sellerName = new StringBuilder();
        HashMap<String, String> animalsPurchased = new HashMap<>();

        for (int i = 0; i < saleArrayList.size(); i++) {
            totalAmount += saleArrayList.get(i).getPrice() * saleArrayList.get(i).getQuantity();

            if (!sellerName.toString().contains(saleArrayList.get(i).getSeller())) {
                if (i < saleArrayList.size() - 2)
                    sellerName.append(saleArrayList.get(i).getSeller()).append(",");
                else
                    sellerName.append(saleArrayList.get(i).getSeller());
            }
        }
        invoice.setSellerName(sellerName.toString());
        invoice.setTotalAmount(totalAmount);
        invoice.setCustomerName(customer.getName());
        invoice.setCustomerAddress(customer.getAddress());


        viewModel.setInvoice(invoice);
    }

    public static ArrayList<Sale> calculateSaleTotals(ArrayList<Sale> salesList) {
        ArrayList<Sale> resultList = new ArrayList<>();

        int totalPrice = 0;
        int totalQuantity = 0;

        for (Sale sale : salesList) {
            totalPrice += sale.getPrice() * sale.getQuantity();
            totalQuantity += sale.getQuantity();

            // Clone the Sale object to avoid modifying the original list
            Sale newSale = new Sale(sale.getCategory(), sale.getPrice(), sale.getSeller(), sale.getQuantity());
            resultList.add(newSale);
        }

        // Add a new Sale object with calculated totals to the result list
        resultList.add(new Sale("Total", totalPrice, "", totalQuantity));

        return resultList;
    }
}