package com.apptechbd.haatbazaar.repositories;

import static com.apptechbd.haatbazaar.utils.HelperClass.logErrorMessage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Layout;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.AdminAccount;
import com.apptechbd.haatbazaar.models.StaffAccount;
import com.apptechbd.haatbazaar.utils.HelperClass;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.List;
import java.util.Objects;

public class LoginRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

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

    public MutableLiveData<AdminAccount> ifAdminUser(String uid, View view, Context context) {
        MutableLiveData<AdminAccount> isAdmin = new MutableLiveData<>();
        db.collection("admins").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        if (Objects.equals(d.getString("id"), uid)) {
                            Log.d("LoginRepository", "matched uid: " + d.getString("id") + "sent uid: "+uid);

                            //Storing the retrieved admin account locally
                            AdminAccount adminAccount = new AdminAccount(uid, d.getString("name"), d.getString("owner"), d.getString("email"), d.getString("phone"), d.getString("address"), d.getLong("created_on"));

                            isAdmin.setValue(adminAccount);
                        } else
                            isAdmin.setValue(null);
                    }
                } else {
                    new HelperClass().showSnackBar(view, "No data found in Database");
                    isAdmin.setValue(null);
                }

                Log.d("LoginRepository", "is admin: " + isAdmin.getValue());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new HelperClass().showSnackBar(view, "Failed to get the data!");
                isAdmin.setValue(null);
            }
        });
        return isAdmin;
    }
}
