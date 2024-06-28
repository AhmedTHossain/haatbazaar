package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.repositories.AddAccountRepository;

public class AddAccountViewModel extends AndroidViewModel {
    private AddAccountRepository addAccountRepository;
    public LiveData<Boolean> ifStaffAdded;
    public LiveData<Boolean> ifSupplierAdded;

    public AddAccountViewModel(@NonNull Application application) {
        super(application);
        addAccountRepository = new AddAccountRepository();
    }

    public void addStaff(Account account) {
        ifStaffAdded = addAccountRepository.addStaff(account);
    }

    public void addSupplier(Account account) {
        ifSupplierAdded = addAccountRepository.addSupplier(account);
    }
}
