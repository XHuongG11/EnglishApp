package com.example.englishapp.service;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginService {
    public static void login(String email, String password, LoginCallback callback) {
        if (email.isEmpty() || password.isEmpty()) {
            callback.onLoginResult(false, "Email or password is empty");
            return;
        }

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onLoginResult(true, "Login successful");
                    } else {
                        callback.onLoginResult(false, "Login failed: " + task.getException().getMessage());
                    }
                });
    }

    public interface LoginCallback {
        void onLoginResult(boolean success, String message);
    }
}


