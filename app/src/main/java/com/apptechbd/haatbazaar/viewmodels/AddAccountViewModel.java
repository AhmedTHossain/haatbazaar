package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.haatbazaar.models.StaffAccount;
import com.apptechbd.haatbazaar.repositories.AddAccountRepository;

public class AddAccountViewModel extends AndroidViewModel {
    private AddAccountRepository addAccountRepository;
    public LiveData<Boolean> ifStaffAdded;

    public AddAccountViewModel(@NonNull Application application) {
        super(application);
        addAccountRepository = new AddAccountRepository();
    }

    public void addStaff(StaffAccount staffAccount) {
        ifStaffAdded = addAccountRepository.addStaff(staffAccount);
    }
}
