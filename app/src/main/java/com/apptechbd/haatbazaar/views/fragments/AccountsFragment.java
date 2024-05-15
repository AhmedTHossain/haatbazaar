package com.apptechbd.haatbazaar.views.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.haatbazaar.adapters.AccountsAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentAccountsBinding;
import com.apptechbd.haatbazaar.interfaces.OnAccountRemoveClickListener;
import com.apptechbd.haatbazaar.models.Account;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AccountsFragment extends Fragment implements OnAccountRemoveClickListener {
    private FragmentAccountsBinding binding;
    private ArrayList<Account> accounts;
    private AccountsAdapter adapter;

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

    private void getDummyAccounts() {
        accounts = new ArrayList<>();

        Account account1 = new Account("Shahidul Islam Miah", "01696588950", "https://drive.google.com/uc?export=view&id=14gr6Lu-ihzKRtwHLq2fIh-OiINQl_sD6");
        Account account2 = new Account("Jahangir Hossain", "01996588950", "https://drive.google.com/uc?export=view&id=1cMES8jZLXCw6iPh8rLPTe6M8SYLbbE_U");
        Account account3 = new Account("Mohammad Ali", "01896588950", "https://drive.google.com/uc?export=view&id=1EXuGikoCGTrAoaVUs_WpJA3kpYkwQ_Fs");
        Account account4 = new Account("Jalaluddin Ahmed", "01596588950", "https://drive.google.com/uc?export=view&id=1jrU2Z2SGONe1vhC9BujYE1EfPfJR6a6u");
        Account account5 = new Account("Muhammad Salahuddin Ahmed", "01696588950", "https://drive.google.com/uc?export=view&id=1OM6phGhQybpfDtqEroweYahi7z8khBHK");

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

        adapter = new AccountsAdapter(accounts, this);
        binding.recyclerviewAccounts.setAdapter(adapter);
    }

    @Override
    public void onAccountRemoveClick(int position) {
        showAccountRemoveConfirmationDialog(position);
    }

    private void showAccountRemoveConfirmationDialog(int position) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireContext()); // Use MaterialAlertDialogBuilder for Material Design theme
        builder.setTitle("Delete User");
        builder.setMessage("Deleting this user account is permanent. Once deleted, the user won't be able to log in again.");
        // Set up the negative button
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle negative button click
                dialog.dismiss();
            }
        });
        // Set up the positive button
        builder.setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Handle positive button click
                accounts.remove(position);
                adapter.notifyItemRemoved(position);
            }
        });
        builder.show();
    }
}