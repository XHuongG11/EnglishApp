package com.example.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
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
    private LinearLayout loginButton, btnSignUp;
    View blurBackground;
    View layoutSignIn;
    View layoutSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_auth);

        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignUp = findViewById(R.id.layoutSignUp);
        blurBackground = findViewById(R.id.blurBackground);

        btnSignUp = layoutSignIn.findViewById(R.id.signUpLayout); // nút ở layout signin
//        Button btnBack = layoutSignUp.findViewById(R.id.btnBack);     // mũi tên hoặc nút quay về trong signup

        // Khi bấm nút Sign Up
        btnSignUp.setOnClickListener(v -> {
            showSignUpLayout();
        });

        blurBackground.setOnClickListener(v -> {
            hideSignUpLayout();
        });
        // Ánh xạ các thành phần
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        forgotPasswordText = findViewById(R.id.forgotPasswordText);
        loginButton = layoutSignIn.findViewById(R.id.loginButton);
       //signUpButton = findViewById(R.id.signUpButton);

        // Xử lý sự kiện nút "Login"
        loginButton.setOnClickListener(v -> {
            // Thêm log để kiểm tra
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
    private void showSignUpLayout() {
        layoutSignUp.setVisibility(View.VISIBLE);
        blurBackground.setVisibility(View.VISIBLE);
        blurBackground.setAlpha(0f);
        blurBackground.animate().alpha(1f).setDuration(300).start();

        layoutSignUp.setTranslationY(layoutSignUp.getHeight()); // đảm bảo trượt từ dưới
        layoutSignUp.animate()
                .translationY(0)
                .setDuration(500)
                .setInterpolator(new DecelerateInterpolator())
                .start();
    }

    private void hideSignUpLayout() {
        blurBackground.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            blurBackground.setVisibility(View.GONE);
        }).start();

        layoutSignUp.animate()
                .translationY(layoutSignUp.getHeight())
                .setDuration(500)
                .withEndAction(() -> {
                    layoutSignUp.setVisibility(View.GONE);
                }).start();
    }

}