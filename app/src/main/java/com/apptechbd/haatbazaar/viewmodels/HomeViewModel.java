package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.models.AllCategories;
import com.apptechbd.haatbazaar.repositories.HomeRepository;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<AllCategories> allCategories;
    private HomeRepository repository;
    private Account account;
    public MutableLiveData<String> buttonText = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isButtonEnabled = new MutableLiveData<>(false);

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository();
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager, String fragmentName) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_home, fragment);
        fragmentTransaction.commit();
        switch (fragmentName){
            case "CategorySelectFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_type));
                break;
            case "SetQuantityFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_quantity));
                break;
            case "SetPriceFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_price));
                break;
        }
    }

    public void getCategories(String admin) {
        allCategories = repository.getCategories(admin);
    }

    public void setAccount(Account account){
        this.account = account;
    }

    public Account getAccount(){
        return account;
    }

    public void setButtonEnabled(boolean enabled){
        isButtonEnabled.setValue(enabled);
    }
}
