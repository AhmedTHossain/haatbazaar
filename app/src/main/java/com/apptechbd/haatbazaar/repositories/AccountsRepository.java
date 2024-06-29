package com.apptechbd.haatbazaar.repositories;

import static com.apptechbd.haatbazaar.utils.Constants.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.Account;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AccountsRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // Get Storage reference
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();

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
                            accountArrayList.add(new Account(d.getString("id"), d.getString("name"), d.getString("email"), d.getString("phone"), d.getString("photo"), d.getString("admin"), d.getString("created"), d.getBoolean("active")));
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
                            accountArrayList.add(new Account(d.getString("id"), d.getString("name"), d.getString("email"), d.getString("phone"), d.getString("photo"), d.getString("admin"), d.getString("created"), d.getBoolean("active")));
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

    public MutableLiveData<Boolean> deleteAccount(Account account, String collection) {
        MutableLiveData<Boolean> ifStaffDeleted = new MutableLiveData<>();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        // Replace with your field name and value
        String fieldName = "id";
        String fieldValue = account.getId();
        String imagePath = account.getPhoto();

        Log.d(TAG,"collection name = "+collection);

        CollectionReference collectionRef = db.collection(collection);

        Query query = collectionRef.whereEqualTo(fieldName, fieldValue);

        query.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!imagePath.isEmpty()){
                                    StorageReference imageRef = storageRef.child(imagePath);
                                    imageRef.delete();
                                }
                                document.getReference().delete();
                            }
                            ifStaffDeleted.setValue(true);
                        } else {
                            ifStaffDeleted.setValue(false);
                            Log.w("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ifStaffDeleted.setValue(false);
                    }
                });
        return ifStaffDeleted;
    }
}
