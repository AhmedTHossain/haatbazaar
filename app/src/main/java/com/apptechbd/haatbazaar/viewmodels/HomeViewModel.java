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
import com.apptechbd.haatbazaar.adapters.Sale;
import com.apptechbd.haatbazaar.databinding.ActivityAdminMainBinding;
import com.apptechbd.haatbazaar.databinding.ActivityHomeBinding;
import com.apptechbd.haatbazaar.models.Account;
import com.apptechbd.haatbazaar.models.AllCategories;
import com.apptechbd.haatbazaar.models.Customer;
import com.apptechbd.haatbazaar.models.Invoice;
import com.apptechbd.haatbazaar.models.Quantity;
import com.apptechbd.haatbazaar.repositories.AccountsRepository;
import com.apptechbd.haatbazaar.repositories.HomeRepository;
import com.apptechbd.haatbazaar.views.fragments.checkout.SetCategoryFragment;

import java.util.ArrayList;

public class HomeViewModel extends AndroidViewModel {
    public LiveData<AllCategories> allCategories;
    private HomeRepository repository;
    private AccountsRepository accountsRepository;
    private Account account;
    public MutableLiveData<String> buttonText = new MutableLiveData<>("");
    public MutableLiveData<Boolean> isButtonEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isSalesListUpdated = new MutableLiveData<>(false);
    private ArrayList<String> categoriesPurchased = new ArrayList<>();
    private ArrayList<Quantity> quantitiesPurchased = new ArrayList<>();
    public ArrayList<Sale> salesList = new ArrayList<>();
    public MutableLiveData<Boolean> signOutClicked  = new MutableLiveData<>(false);
    public LiveData<Customer> customer;
    public MutableLiveData<Boolean> isCustomerFound = new MutableLiveData<>();
    private SetCategoryFragment setCategoryFragment = new SetCategoryFragment();
    public MutableLiveData<ArrayList<Account>> supplierAccounts;
    private Invoice invoice = new Invoice();

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new HomeRepository();
        accountsRepository = new AccountsRepository();
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager, String fragmentName) {
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_home, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        switch (fragmentName) {
            case "SetCategoryFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_type));
                break;
            case "SetQuantityFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_quantity));
                break;
            case "SetPriceFragment":
                buttonText.setValue(getApplication().getString(R.string.set_the_price));
                break;
            case "SetCustomerInfoFragment":
                buttonText.setValue(getApplication().getString(R.string.set_customers_information_disclaimer));
                break;
        }
    }

    public void getCategories(String admin) {
        allCategories = repository.getCategories(admin);
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public void setButtonEnabled(boolean enabled) {
        isButtonEnabled.setValue(enabled);
    }

    public void setCategoriesPurchased(ArrayList<String> categoriesPurchased) {
        this.categoriesPurchased = categoriesPurchased;
    }

    public ArrayList<String> getCategoriesPurchased() {
        return categoriesPurchased;
    }

    public void setQuantitiesPurchased(ArrayList<Quantity> quantitiesPurchased) {
        this.quantitiesPurchased = quantitiesPurchased;
    }
    public ArrayList<Quantity> getQuantitiesPurchased() {
        return quantitiesPurchased;
    }

    public void setSalesList(ArrayList<Sale> salesList) {
        this.salesList = salesList;
        isSalesListUpdated.setValue(true);
    }

    public ArrayList<Sale> getSalesList() {
        return salesList;
    }

    public void onBottomNavMenuItemSelect(ActivityHomeBinding binding, FragmentManager supportFragmentManager) {
        binding.bottomNavigationviewHome.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.checkout) {
                replaceFragment(setCategoryFragment, supportFragmentManager, "SetCategoryFragment");
            } else if (itemId == R.id.sales) {
//                replaceFragment(accountsFragment, supportFragmentManager);
            } else if (itemId == R.id.logout) {
                signOut();
            }

            return true;
        });
    }

    public void signOut() {
        signOutClicked.setValue(true);
    }

    public void getSupplierAccounts(String admin) {
        supplierAccounts = accountsRepository.getSupplierAccounts(admin);
    }

    public void getCustomer(String code){
        customer = repository.getCustomer(code);
        if (customer!=null)
            isCustomerFound.setValue(true);
        else
            isCustomerFound.setValue(false);
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
}
