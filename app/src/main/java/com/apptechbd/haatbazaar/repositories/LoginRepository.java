package com.apptechbd.haatbazaar.repositories;

import static com.apptechbd.haatbazaar.utils.HelperClass.logErrorMessage;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public MutableLiveData<FirebaseUser> firebaseSignInWithGoogle(AuthCredential googleAuthCredential) {
        MutableLiveData<FirebaseUser> authenticatedUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(googleAuthCredential).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    authenticatedUserMutableLiveData.setValue(firebaseUser);
                }
            } else logErrorMessage(Objects.requireNonNull(authTask.getException()).getMessage());
        });
        return authenticatedUserMutableLiveData;
    }
}
