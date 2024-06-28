package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.repositories.AccountsRepository;

import java.util.ArrayList;

public class AccountsViewModel extends AndroidViewModel {
    private AccountsRepository accountsRepository;
    public LiveData<ArrayList<Account>> staffAccounts;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        accountsRepository = new AccountsRepository();
    }

    public void getStaffAccounts(String admin) {
        staffAccounts = accountsRepository.getStaffAccounts(admin);
    }
}
