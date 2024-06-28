package com.apptechbd.haatbazaar.views.fragments.admin;

import static android.content.Context.MODE_PRIVATE;

import static com.apptechbd.haatbazaar.utils.Constants.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.apptechbd.haatbazaar.adapters.AccountsAdapter;
import com.apptechbd.haatbazaar.databinding.FragmentAccountsBinding;
import com.apptechbd.haatbazaar.interfaces.OnAccountRemoveClickListener;
import com.apptechbd.haatbazaar.models.AdminAccount;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.viewmodels.AccountsViewModel;
import com.apptechbd.haatbazaar.views.activities.AddAccountActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AccountsFragment extends Fragment implements OnAccountRemoveClickListener, View.OnClickListener {
    private FragmentAccountsBinding binding;
    private ArrayList<Account> accounts;

    private AccountsAdapter adapter;
    private AccountsViewModel accountsViewModel;
    private SharedPreferences sharedPreferences;
    private  int position = 0;

    public AccountsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountsBinding.inflate(inflater, container, false);
        sharedPreferences = requireActivity().getSharedPreferences("UserPrefs", MODE_PRIVATE);
        initViewModel();

        setupTabListeners();

        binding.buttonAddNewAccount.setOnClickListener(this);

        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (position == 0)
            getStaffAccounts();
        else if (position == 1)
            getSuppliersAccounts();
    }

    private void initViewModel() {
        accountsViewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        getStaffAccounts();
    }

    private void setupTabListeners() {
        binding.tablayoutAccounts.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                if (position == 0) {
                    // Staffs tab selected
                    getStaffAccounts();

                } else if (position == 1) {
                    // Suppliers tab selected
                    getSuppliersAccounts();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

//    private void getDummyAccounts() {
//        accounts = new ArrayList<>();
//
//        Account account1 = new Account("Shahidul Islam Miah", "01696588950", "https://drive.google.com/uc?export=view&id=1JTe00cTweVcoToM2g3wHUvTJuqJSuHDm");
//        Account account2 = new Account("Jahangir Hossain", "01996588950", "https://drive.google.com/uc?export=view&id=1cMES8jZLXCw6iPh8rLPTe6M8SYLbbE_U");
//        Account account3 = new Account("Mohammad Ali", "01896588950", "https://drive.google.com/uc?export=view&id=1EXuGikoCGTrAoaVUs_WpJA3kpYkwQ_Fs");
//        Account account4 = new Account("Jalaluddin Ahmed", "01596588950", "https://drive.google.com/uc?export=view&id=1jrU2Z2SGONe1vhC9BujYE1EfPfJR6a6u");
//        Account account5 = new Account("Muhammad Salahuddin Ahmed", "01696588950", "https://drive.google.com/uc?export=view&id=1OM6phGhQybpfDtqEroweYahi7z8khBHK");
//
//        accounts.add(account1);
//        accounts.add(account2);
//        accounts.add(account3);
//        accounts.add(account4);
//        accounts.add(account5);
//
//        setAccounts(accounts);
//    }

    private void getStaffAccounts() {
        accounts = new ArrayList<>();
        accountsViewModel.getStaffAccounts(getAdminAccount().getId());

        accountsViewModel.staffAccounts.observe(getViewLifecycleOwner(), staffAccounts -> {
            if (staffAccounts != null) {
                accounts.addAll(staffAccounts);
                Log.d(TAG, "getStaffAccounts: " + accounts.size());
                setAccounts(accounts);
            }
        });
    }

    private void getDummySupplierAccounts() {
        accounts = new ArrayList<>();

        Account account6 = new Account("", "Baker Miah", "", "01696588950", "https://drive.google.com/uc?export=view&id=1qNoqy9HtRU6eLXbnHQq8zlflwMBnGUaI","","",true);
        Account account1 = new Account("","Romiz Uddin","","01696588950", "https://media.istockphoto.com/id/1341652074/photo/rural-man-standing-at-home.jpg?s=612x612&w=0&k=20&c=lGOgY856BHCnE3i09U20oVkqssfshwK9hrdJZRWW5Q8=","","",true);
        Account account2 = new Account("","Ruhul Amin", "", "01996588950", "https://drive.google.com/uc?export=view&id=1CAQP53PuH98Kh_e26-f2-qVLZFkX9ZUJ","","",true);
        Account account3 = new Account("","Khalek Miah", "", "01896588950", "https://drive.google.com/uc?export=view&id=19VqxBym6o6OsvGhsUrsa-GxDb0_ALhy5","","",true);
        Account account4 = new Account("","Abbas Ali", "", "01596588950", "https://drive.google.com/uc?export=view&id=13oawA6646PqewwgmaTXlB6gwzLzHBn6L","","",true);
        Account account5 = new Account("","Haripada Bishwas", "", "01696588950", "https://drive.google.com/uc?export=view&id=1w-0GR6gSrJsj4DobF4MuviAKI5ywP-Ta","","",true);

        accounts.add(account6);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        accounts.add(account4);
        accounts.add(account5);

        setAccounts(accounts);
    }

    private void getSuppliersAccounts() {
        accounts = new ArrayList<>();
        accountsViewModel.getSupplierAccounts(getAdminAccount().getId());

        accountsViewModel.supplierAccounts.observe(getViewLifecycleOwner(), supplierAccounts -> {
            if (supplierAccounts != null) {
                accounts.addAll(supplierAccounts);
                Log.d(TAG, "getSuppliersAccounts: " + accounts.size());
                setAccounts(accounts);
            }
        });
    }

    private void setAccounts(ArrayList<Account> accounts) {
        binding.spinKit.setVisibility(View.GONE);
        binding.recyclerviewAccounts.setVisibility(View.VISIBLE);

        binding.recyclerviewAccounts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerviewAccounts.setHasFixedSize(true);

        adapter = new AccountsAdapter(accounts, this, position);
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
        builder.setCancelable(false);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == binding.buttonAddNewAccount.getId()) {
            Intent intent = new Intent(requireContext(),AddAccountActivity.class);
            intent.putExtra("position", position);
            startActivity(intent);
        }
    }

    private AdminAccount getAdminAccount() {
        String adminAccountJson = sharedPreferences.getString("admin_account", "");
        return new Gson().fromJson(adminAccountJson, AdminAccount.class);
    }
}