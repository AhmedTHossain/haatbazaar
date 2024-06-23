package com.apptechbd.haatbazaar.viewmodels;

import android.app.Application;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.apptechbd.haatbazaar.repositories.LoginRepository;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {
    private LoginRepository loginRepository;
    public LiveData<FirebaseUser> authenticatedUserLiveData;
    public LiveData<Boolean> isAdminUser;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        loginRepository = new LoginRepository();
    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = loginRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    public void checkIfAdminUser(String uid, View view) {
        isAdminUser = loginRepository.ifAdminUser(uid, view);
    }
}
