package com.pixel.web.metegol.dao;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.pixel.web.metegol.model.UserClass;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static String getId(String email){
        final String[] id = {"miid"};

        FirebaseFirestore.getInstance().collection("users").whereEqualTo("email",email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot doc : task.getResult()) {
                                    id[0] =doc.getId();
                                    Log.e("idxx: ",""+doc.getId());
                                }
                            } else {
                              //  Log.d("Data", "Error getting documents: ", task.getException());
                            }
                        }
                    });
        return id[0];
    }
}
