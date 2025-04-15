package com.example.englishapp.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishapp.R;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_welcome);

        // Các view
        Button learnNowButton = findViewById(R.id.learnNowButton);
        ImageView whaleImage = findViewById(R.id.welcomewhale);

        // Animation cho nút "Learn Now"
        ObjectAnimator buttonFadeIn = ObjectAnimator.ofFloat(learnNowButton, "alpha", 0f, 1f);
        buttonFadeIn.setDuration(2000);
        ObjectAnimator buttonScaleX = ObjectAnimator.ofFloat(learnNowButton, "scaleX", 1f, 1.1f, 1f);
        buttonScaleX.setDuration(1000);
        buttonScaleX.setRepeatCount(ObjectAnimator.INFINITE);
        buttonScaleX.setRepeatMode(ObjectAnimator.REVERSE);
        ObjectAnimator buttonScaleY = ObjectAnimator.ofFloat(learnNowButton, "scaleY", 1f, 1.1f, 1f);
        buttonScaleY.setDuration(1000);
        buttonScaleY.setRepeatCount(ObjectAnimator.INFINITE);
        buttonScaleY.setRepeatMode(ObjectAnimator.REVERSE);

        // Animation cho hình ảnh cá voi
        ObjectAnimator swimUpDown = ObjectAnimator.ofFloat(whaleImage, "translationY", 0f, -20f, 0f, 20f, 0f);
        swimUpDown.setDuration(2000);
        swimUpDown.setRepeatCount(ObjectAnimator.INFINITE);
        swimUpDown.setRepeatMode(ObjectAnimator.RESTART);

        ObjectAnimator rotate = ObjectAnimator.ofFloat(whaleImage, "rotation", -5f, 5f, -5f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(ObjectAnimator.INFINITE);
        rotate.setRepeatMode(ObjectAnimator.REVERSE);

        // Chạy tất cả animation
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(
                swimUpDown, rotate
        );
        animatorSet.play(buttonFadeIn).before(buttonScaleX);
        animatorSet.play(buttonScaleX).with(buttonScaleY);
        animatorSet.setStartDelay(200);
        animatorSet.start();

        // Sự kiện click cho nút "Learn Now"
        learnNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}