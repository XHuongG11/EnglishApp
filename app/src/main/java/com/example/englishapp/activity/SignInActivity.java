package com.example.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishapp.R;

public class SignInActivity extends AppCompatActivity {
    private EditText usernameEditText, passwordEditText;
    private TextView forgotPasswordText;
    private LinearLayout loginButton, signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Log.d("SignInActivity", "onCreate called");

        // Ánh xạ các thành phần
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        loginButton = findViewById(R.id.loginButton);
       //signUpButton = findViewById(R.id.signUpButton);

        // Xử lý sự kiện nút "Login"
        loginButton.setOnClickListener(v -> {
            // Thêm log để kiểm tra
            Log.d("SignInActivity", "Login button clicked");
            Toast.makeText(SignInActivity.this, "Login clicked", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        });

        // Xử lý sự kiện nút "Forgot Password?"
        forgotPasswordText.setOnClickListener(v -> {
            // Chuyển đến màn hình quên mật khẩu (nếu có)
        });
    }
}