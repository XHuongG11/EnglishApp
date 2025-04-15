package com.example.englishapp.activity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishapp.R;

public class AuthActivity extends AppCompatActivity {

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

        LinearLayout btnSignUp = layoutSignIn.findViewById(R.id.signUpLayout); // nút ở layout signin
//        Button btnBack = layoutSignUp.findViewById(R.id.btnBack);     // mũi tên hoặc nút quay về trong signup

        // Khi bấm nút Sign Up
        btnSignUp.setOnClickListener(v -> {
            showSignUpLayout();
        });

        blurBackground.setOnClickListener(v -> {
            hideSignUpLayout();
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

