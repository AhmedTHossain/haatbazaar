package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.views.fragments.cart.SetPriceFragment;
import com.apptechbd.haatbazaar.views.fragments.cart.SetQuantityFragment;

public class CartViewModel extends AndroidViewModel {

    public CartViewModel(@NonNull Application application) {
        super(application);
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_cart, fragment);
        fragmentTransaction.commit();
    }
}
