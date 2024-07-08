package com.apptechbd.haatbazaar.repositories;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.apptechbd.haatbazaar.models.AllCategories;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
}
