package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;

import com.apptechbd.haatbazaar.R;
import com.apptechbd.haatbazaar.databinding.ActivityAdminMainBinding;
import com.apptechbd.haatbazaar.views.fragments.admin.AccountsFragment;
import com.apptechbd.haatbazaar.views.fragments.admin.AnalyticsFragment;
import com.apptechbd.haatbazaar.views.fragments.admin.MoreFragment;

public class AdminMainViewModel extends AndroidViewModel {
    AnalyticsFragment analyticsFragment = new AnalyticsFragment();
    AccountsFragment accountsFragment = new AccountsFragment();
    MoreFragment moreFragment = new MoreFragment();

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
            } else if (itemId == R.id.more) {
                replaceFragment(moreFragment, supportFragmentManager);
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
