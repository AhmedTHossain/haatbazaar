package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAdminMainBinding;
import com.apptechbd.haatbazaar.views.fragments.AccountsFragment;
import com.apptechbd.haatbazaar.views.fragments.AnalyticsFragment;

public class AdminMainViewModel extends AndroidViewModel {
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();
    AccountsFragment accountsFragment = new AccountsFragment();
    public AdminMainViewModel(@NonNull Application application) {
        super(application);
    }

    public void onBottomNavMenuItemSelect(ActivityAdminMainBinding binding, FragmentManager supportFragmentManager) {
        binding.bottomNavigationview.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.analytics) {
                replaceFragment(analyticsFragment, supportFragmentManager);
            } else if (itemId == R.id.accounts) {
                replaceFragment(accountsFragment, supportFragmentManager);
            } else if (itemId == R.id.signout) {
                //ToDo: Signout from firebase
            }

            return true;
        });
    }

    public void replaceFragment(Fragment fragment, FragmentManager supportFragmentManager) {
        FragmentManager fragmentManager = supportFragmentManager;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_main, fragment);
        fragmentTransaction.commit();
    }
}
