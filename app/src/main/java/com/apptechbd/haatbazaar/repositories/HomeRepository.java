package com.apptechbd.haatbazaar.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.AdminAccount;
import com.apptechbd.haatbazaar.models.AllCategories;
import com.apptechbd.haatbazaar.models.Customer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Objects;

public class HomeRepository {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MutableLiveData<AllCategories> getCategories(String admin) {
        MutableLiveData<AllCategories> allCategories = new MutableLiveData<>();
        db.collection("categories").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    for (DocumentSnapshot d : list) {
                        if (Objects.equals(d.getString("admin"), admin)) {
                            List<String> categories = (List<String>) d.get("categories");
                            AllCategories allCategoriesFetched = new AllCategories(admin, categories);
                            allCategories.setValue(allCategoriesFetched);
                        }
                    }
                } else
                    allCategories.setValue(null);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                allCategories.setValue(null);
            }
        });
        return allCategories;
    }

    public MutableLiveData<Customer> getCustomer(String code) {
        MutableLiveData<Customer> customer = new MutableLiveData<>();
        db.collection("customers").whereEqualTo("code", code).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    boolean customerFound = false;
                    for (DocumentSnapshot document : task.getResult()) {
                        Customer customerFetched = document.toObject(Customer.class);
                        customer.setValue(customerFetched);
                        customerFound = true;
                    }
                    if (!customerFound) {
                        customer.setValue(null);
                    }
                } else {
                    customer.setValue(null);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                customer.setValue(null);
            }
        });
        return customer;
    }

//    public MutableLiveData<AdminAccount> getAdminAccount(String adminId) {
//        MutableLiveData<AdminAccount> adminAccount = new MutableLiveData<>();
//        db.collection("admins").whereEqualTo("id", adminId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                if (task.isSuccessful()) {
//                    boolean adminAccountFound = false;
//                    for (DocumentSnapshot document : task.getResult()) {
//                        AdminAccount adminAccountFetched = document.toObject(AdminAccount.class);
//                        adminAccount.setValue(adminAccountFetched);
//                        adminAccountFound = true;
//                    }
//                    if (!adminAccountFound) {
//                        adminAccount.setValue(null);
//                    }
//                } else {
//                    adminAccount.setValue(null);
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                adminAccount.setValue(null);
//            }
//        });
//        return adminAccount;
//    }
}
