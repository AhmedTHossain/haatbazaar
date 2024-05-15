package com.apptechbd.haatbazaar.views.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.adapters.AccountsAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentAccountsBinding;
import com.apptechbd.haatbazaar.models.Account;

import java.util.ArrayList;

public class AccountsFragment extends Fragment {
    private FragmentAccountsBinding binding;
    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);

        getDummyAccounts();

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    private void getDummyAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();

        Account account1 = new Account("Shahidul Islam Miah","01696588950","https://drive.google.com/uc?export=view&id=14gr6Lu-ihzKRtwHLq2fIh-OiINQl_sD6");
        Account account2 = new Account("Jahangir Hossain","01996588950","https://drive.google.com/uc?export=view&id=1cMES8jZLXCw6iPh8rLPTe6M8SYLbbE_U");
        Account account3 = new Account("Mohammad Ali","01896588950","https://drive.google.com/uc?export=view&id=1EXuGikoCGTrAoaVUs_WpJA3kpYkwQ_Fs");
        Account account4 = new Account("Jalaluddin Ahmed","01596588950","https://drive.google.com/uc?export=view&id=1jrU2Z2SGONe1vhC9BujYE1EfPfJR6a6u");
        Account account5 = new Account("Muhammad Salahuddin Ahmed","01696588950","https://drive.google.com/uc?export=view&id=1OM6phGhQybpfDtqEroweYahi7z8khBHK");

        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);

        setAccounts(accounts);
    }

    private void setAccounts(ArrayList<Account> accounts) {
        binding.recyclerviewAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewAccounts.setHasFixedSize(true);
        binding.recyclerviewAccounts.setAdapter(new AccountsAdapter(accounts));
    }
}