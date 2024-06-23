package com.apptechbd.haatbazaar.repositories;

import static com.apptechbd.haatbazaar.utils.HelperClass.logErrorMessage;

import android.text.Layout;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.utils.HelperClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class LoginRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public MutableLiveData<Boolean> ifAdminUser(String uid, View view) {
        MutableLiveData<Boolean> isAdmin = new MutableLiveData<>();
        db.collection("admins").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        if (Objects.equals(d.getString("id"), uid)) {
                            Log.d("LoginRepository", "matched uid: " + d.getString("id") + "sent uid: "+uid);
                            isAdmin.setValue(true);
                        } else
                            isAdmin.setValue(false);
                    }
                } else {
                    new HelperClass().showSnackBar(view, "No data found in Database");
                    isAdmin.setValue(false);
                }

                Log.d("LoginRepository", "is admin: " + isAdmin.getValue());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new HelperClass().showSnackBar(view, "Failed to get the data!");
                isAdmin.setValue(false);
            }
        });
        return isAdmin;
    }

}
