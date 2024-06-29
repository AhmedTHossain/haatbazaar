package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.repositories.AccountsRepository;

import java.util.ArrayList;

public class AccountsViewModel extends AndroidViewModel {
    private AccountsRepository accountsRepository;
    public LiveData<ArrayList<Account>> staffAccounts;
    public LiveData<ArrayList<Account>> supplierAccounts;
    public LiveData<Boolean> ifStaffDeleted;

    public AccountsViewModel(@NonNull Application application) {
        super(application);
        accountsRepository = new AccountsRepository();
    }

    public void getStaffAccounts(String admin) {
        staffAccounts = accountsRepository.getStaffAccounts(admin);
    }

    public void getSupplierAccounts(String admin) {
        supplierAccounts = accountsRepository.getSupplierAccounts(admin);
    }

    public void deleteAccount(Account account, String collection){
        ifStaffDeleted = accountsRepository.deleteAccount(account,collection);
    }
}
