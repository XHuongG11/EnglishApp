package com.example.englishapp.dao;

import android.util.Log;

import com.example.englishapp.model.User;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;


public class UserDAO{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void create(User user,OnSaveUpdateListener listener)
    {
        try{
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference  -> {
                        listener.onComplete(true);
                        Log.w("Firebase", "Success saving user");
                    })
                    .addOnFailureListener(
                            e ->
                            {
                                listener.onComplete(false);
                                Log.w("Firebase", "Error saving user", e);
                            }
                    );
        }catch (Exception ex){
            Log.d("CREATE_USER",ex.getMessage());
        }
    }


    public void getByID(String userID, OnGetByIdListener<User> listener)
    {
        db.collection("users")
                .document(userID)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        User user = documentSnapshot.toObject(User.class);
                        listener.onGetByID(user);
                    } else {
                        listener.onGetByID(null);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error getting user by ID", e);
                    listener.onGetByID(null);
                });
    }

    public void getAll(OnGetAllListener<User> listener) {
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<User> userList = queryDocumentSnapshots.toObjects(User.class);
                        listener.onGetAll(userList);
                    } else {
                        listener.onGetAll(Collections.emptyList());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error getting all users", e);
                    listener.onGetAll(Collections.emptyList());
                });
    }

    public void update(String id,User user,OnSaveUpdateListener listener) {
        db.collection("users")
                .document(id)
                .set(user)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "User updated successfully");
                    listener.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error updating user", e);
                    listener.onComplete(false);
                });
    }

    public void delete(String id,OnSaveUpdateListener listener) {
        db.collection("users")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "User deleted successfully");
                    listener.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error deleting user", e);
                    listener.onComplete(false);
                });

    }
}
