package com.example.englishapp.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.example.englishapp.R;
import com.google.firebase.FirebaseApp;

public class SplashActivity extends AppCompatActivity {
    private ImageView fish1, fish2, fish3,fish4;
    private ImageView bubble1, bubble2, bubble3, bubble4, bubble5;
    private int completedAnimations = 0; // Đếm số animation hoàn tất

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);//ẩn thanh staus bar :)))))
        setContentView(R.layout.activity_splash);

        // ✅ Khởi tạo FirebaseApp nếu chưa khởi tạo
        FirebaseApp.initializeApp(this);
        // Ánh xạ các con cá
        fish1 = findViewById(R.id.fish1);
        fish2 = findViewById(R.id.fish2);
        fish3 = findViewById(R.id.fish3);
        fish4 = findViewById(R.id.fish4);

        // Ánh xạ các bọt biển
        bubble1 = findViewById(R.id.bubble1);
        bubble2 = findViewById(R.id.bubble2);
        bubble3 = findViewById(R.id.bubble3);
        bubble4 = findViewById(R.id.bubble4);
        bubble5 = findViewById(R.id.bubble5);

        // Tạo animation cho các con cá (bơi lần lượt)
        animateFish(fish1, 0);    // Cá voi bắt đầu ngay lập tức
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish2, 0), 700);  // Rùa bắt đầu sau 0.5 giây
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish3, 0), 1200); // Cá nóc bắt đầu sau 1 giây
        new Handler(Looper.getMainLooper()).postDelayed(() -> animateFish(fish4, 0), 1700);
        // Tạo animation cho các bọt biển (nổi lên liên tục)
        animateBubble(bubble1, 2000, 0);    // Bọt 1: 2 giây
        animateBubble(bubble2, 2500, 300);  // Bọt 2: 2.5 giây, trễ 0.3 giây
        animateBubble(bubble3, 1800, 600);  // Bọt 3: 1.8 giây, trễ 0.6 giây
        animateBubble(bubble4, 2200, 900);  // Bọt 4: 2.2 giây, trễ 0.9 giây
        animateBubble(bubble5, 2000, 1200); // Bọt 5: 2 giây, trễ 1.2 giây
    }

    private void animateFish(ImageView fish, long delay) {
        // Bơi từ trái sang phải và từ dưới lên
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", -200f, 2000f); // Từ ngoài trái sang phải
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 500f, 300f, 100f, 200f); // Đi lên từ dưới
        PropertyValuesHolder rotation = PropertyValuesHolder.ofFloat("rotation", 0f, 10f, -10f, 0f); // Lắc nhẹ

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                fish,
                translationX,
                translationY,
                rotation
        );

        animator.setDuration(3000); // Mỗi cá bơi 3 giây
        animator.setStartDelay(delay);
        animator.setInterpolator(new android.view.animation.AccelerateDecelerateInterpolator());

        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                completedAnimations++;
                if (completedAnimations == 4) {
                    Intent intent = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        });

        animator.start();
    }


    private void animateBubble(ImageView bubble, long duration, long delay) {
        // Tạo animation cho bọt biển nổi lên
        PropertyValuesHolder translationY = PropertyValuesHolder.ofFloat("translationY", 0f, -2000f); // Nổi lên trên ra ngoài màn hình
        PropertyValuesHolder translationX = PropertyValuesHolder.ofFloat("translationX", 0f, 50f, -50f, 0f); // Lắc nhẹ sang trái phải
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.2f, 1f); // Phóng to/thu nhỏ nhẹ
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.2f, 1f); // Phóng to/thu nhỏ nhẹ
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 1f, 0f); // Mờ dần khi nổi lên

        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(
                bubble,
                translationY,
                translationX,
                scaleX,
                scaleY,
                alpha
        );

        // Thiết lập animation
        animator.setDuration(duration); // Thời gian nổi lên
        animator.setStartDelay(delay); // Độ trễ trước khi bắt đầu
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Lặp lại vô hạn
        animator.setInterpolator(new android.view.animation.LinearInterpolator());

        // Bắt đầu animation
        animator.start();
    }
}