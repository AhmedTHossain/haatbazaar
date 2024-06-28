package com.apptechbd.haatbazaar.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AccountsRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<ArrayList<Account>> getStaffAccounts(String admin) {
        MutableLiveData<ArrayList<Account>> staffAccounts = new MutableLiveData<>();
        db.collection("staffs").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    ArrayList<Account> accountArrayList = new ArrayList<>();
                    for (DocumentSnapshot d : list) {
                        if (admin.equals(d.getString("admin")))
                            accountArrayList.add(new Account(d.getString("id"), d.getString("name"), d.getString("email"), d.getString("phone"), d.getString("photo"), d.getString("admin"), d.getString("created_on"), d.getBoolean("active")));
                    }
                    staffAccounts.setValue(accountArrayList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                staffAccounts.setValue(null);
            }
        });
        return staffAccounts;
    }

    public MutableLiveData<ArrayList<Account>> getSupplierAccounts(String admin) {
        MutableLiveData<ArrayList<Account>> supplierAccounts = new MutableLiveData<>();
        db.collection("suppliers").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    ArrayList<Account> accountArrayList = new ArrayList<>();
                    for (DocumentSnapshot d : list) {
                        if (admin.equals(d.getString("admin")))
                            accountArrayList.add(new Account(d.getString("id"), d.getString("name"), d.getString("email"), d.getString("phone"), d.getString("photo"), d.getString("admin"), d.getString("created_on"), d.getBoolean("active")));
                    }
                    supplierAccounts.setValue(accountArrayList);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                supplierAccounts.setValue(null);
            }
        });
        return supplierAccounts;
    }
}
