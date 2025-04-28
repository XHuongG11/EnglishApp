package com.example.englishapp.dao;

import android.util.Log;

import com.example.englishapp.model.EPracticeType;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
import com.example.englishapp.model.Topic;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PracticeDAO{
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    // CREATE
    public void create(Practice practice, OnSaveUpdateListener listener) {
        try {
            db.collection("practices")
                    .document(practice.getId().toString())
                    .set(practice)
                    .addOnSuccessListener(aVoid -> {
                        listener.onComplete(true);
                        Log.d("Firebase", "Success saving practice");
                    })
                    .addOnFailureListener(e -> {
                        listener.onComplete(false);
                        Log.w("Firebase", "Error saving practice", e);
                    });
        } catch (Exception ex) {
            Log.e("CREATE_PRACTICE", ex.getMessage());
        }
    }

    // READ BY ID
    public void getByID(Long id, OnGetByIdListener<Practice> listener) {
        db.collection("practices")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        Practice practice = querySnapshot.getDocuments().get(0).toObject(Practice.class);
                        listener.onGetByID(practice);
                    } else {
                        listener.onGetByID(null);
                    }
                })
                .addOnFailureListener(listener::onGetFailed);
    }

    public void getByNumberTopic(String numberTopic, OnGetAllListener<Practice> listener) {
        db.collection("practices")
                .whereEqualTo("topic.numberTopic", numberTopic)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        Log.d("DEBUG", " not null");
                        List<Practice> practices = new ArrayList<>();
                        for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                            Practice practice = document.toObject(Practice.class);
                            practices.add(practice);
                            Log.d("DEBUG", "adđd");
                        }
                        listener.onGetAll(practices);
                    } else {
                        Log.d("DEBUG", "null");
                        listener.onGetAll(null);
                    }
                })
                .addOnFailureListener(listener::onGetFailed);
    }

    // READ ALL
    public void getAll(OnGetAllListener<Practice> listener) {
        db.collection("practices")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<Practice> practiceList = queryDocumentSnapshots.toObjects(Practice.class);
                        listener.onGetAll(practiceList);
                    } else {
                        listener.onGetAll(Collections.emptyList());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error getting all practices", e);
                    listener.onGetAll(Collections.emptyList());
                });
    }

    // UPDATE
    public void update(Long practiceId, Practice practice, OnSaveUpdateListener listener) {
        db.collection("practices")
                .whereEqualTo("id", practiceId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                            db.collection("practices")
                                    .document(documentSnapshot.getId())
                                    .set(practice)
                                    .addOnSuccessListener(aVoid -> {
                                        Log.d("Firebase", "Practice updated successfully");
                                        listener.onComplete(true);
                                    })
                                    .addOnFailureListener(e -> {
                                        Log.e("Firebase", "Error updating practice", e);
                                        listener.onComplete(false);
                                    });
                            break;
                        }
                    } else {
                        Log.e("Firebase", "Practice not found for update");
                        listener.onComplete(false);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error finding practice to update", e);
                    listener.onComplete(false);
                });
    }


    // DELETE
    public void delete(String id, OnSaveUpdateListener listener) {
        db.collection("practices")
                .document(id)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Practice deleted successfully");
                    listener.onComplete(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Error deleting practice", e);
                    listener.onComplete(false);
                });
    }
}
