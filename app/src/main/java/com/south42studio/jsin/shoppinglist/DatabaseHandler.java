package com.south42studio.jsin.shoppinglist;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class DatabaseHandler {


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DatabaseHandler";
    private FirebaseAuth mAuth;
    //public FirebaseUser currentUser = mAuth.getCurrentUser();

    public DatabaseHandler() {
        //currentUser = mAuth.getCurrentUser();
    }

    public void AddUsertoDB(String user_id, String group_id){

        // temp item creation delete once connected to the add button
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("user_id", user_id );
        userInfo.put("group_id", group_id);


        db.collection("user")
                .add(userInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d (TAG, "Item added with ID " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "Error adding item");
                    }
                });

    }


}

