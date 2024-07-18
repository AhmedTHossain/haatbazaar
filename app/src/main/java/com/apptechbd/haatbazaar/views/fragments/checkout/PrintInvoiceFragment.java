package com.apptechbd.haatbazaar.views.fragments.checkout;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;

public class PrintInvoiceFragment extends Fragment {

    public PrintInvoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_print_invoice, container, false);



        // Inflate the layout for this fragment
        return view.getRootView();
    }
}