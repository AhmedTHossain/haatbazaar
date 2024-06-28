package com.apptechbd.haatbazaar.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddAccountRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<Boolean> addStaff(Account staff) {
        MutableLiveData<Boolean> ifStaffAdded = new MutableLiveData<>();
        // Add a new document with a generated ID
        db.collection("staffs")
                .add(staff)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        ifStaffAdded.setValue(true);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        ifStaffAdded.setValue(false);
                    }
                });
        return ifStaffAdded;
    }
}
