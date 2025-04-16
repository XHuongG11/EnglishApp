package com.example.englishapp.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.englishapp.R;

public class LoadingDialog extends Dialog {

    private AppCompatActivity activity;

    public LoadingDialog(@NonNull AppCompatActivity activity) {
        super(activity, android.R.style.Theme_Translucent_NoTitleBar);
        this.activity = activity;
        setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_dialog);

        ImageView fish1 = findViewById(R.id.fish1);
        ImageView fish2 = findViewById(R.id.fish2);
        ImageView fish3 = findViewById(R.id.fish3);
        ImageView fish4 = findViewById(R.id.fish4);
        ImageView bubble1 = findViewById(R.id.bubble1);
        ImageView bubble2 = findViewById(R.id.bubble2);
        ImageView bubble3 = findViewById(R.id.bubble3);
        ImageView bubble4 = findViewById(R.id.bubble4);
        ImageView bubble5 = findViewById(R.id.bubble5);

        animateFish(fish1, 0);
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish2, 0), 400);
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish3, 0), 800);
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish4, 0), 1200);


        animateBubble(bubble1, 1200, 0);
        animateBubble(bubble2, 1300, 300);
        animateBubble(bubble3, 1100, 600);
        animateBubble(bubble4, 1250, 900);
        animateBubble(bubble5, 1200, 1200);

    }

    private void animateFish(ImageView fish, long delay) {
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", -200f, 2000f);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 500f, 300f, 100f, 200f);
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0f, 10f, -10f, 0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(fish, translationX, translationY, rotation);
        animator.setDuration(2000);
        animator.setStartDelay(delay);
        animator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.start();
    }

    private void animateBubble(ImageView bubble, long duration, long delay) {
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0f, -2000f);
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0f, 50f, -50f, 0f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                bubble,
                translationY, translationX, scaleX, scaleY, alpha
        );

        animator.setDuration(duration);
        animator.setStartDelay(delay);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new android.view.animation.LinearInterpolator());
        animator.start();
    }
}

