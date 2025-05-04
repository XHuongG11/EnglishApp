package com.example.englishapp.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.englishapp.R;

public class ReviewWordActivity extends AppCompatActivity {

    private Button btnXaoTron;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_review_word);
        GridLayout gridLayout = findViewById(R.id.gridWords);
        gridLayout.removeAllViews(); // Xoá các nút cũ nếu có

        btnXaoTron = findViewById(R.id.btnXoaTron);

        String[][] wordPairs = {
                {"work", "làm việc"},
                {"want", "muốn"},
                {"here", "đây, ở đây"},
                {"like", "thích"},
                {"coffee", "cà phê"},
                {"coffee", "cà phê"},
                {"coffee", "cà phê"},
                {"coffee", "cà phê"},
                {"coffee", "cà phê"},
                {"coffee", "cà phê"}
        };

        for (String[] pair : wordPairs) {
            Button englishBtn = createWordButton(this, pair[0]);
            Button vietnameseBtn = createWordButton(this, pair[1]);

            gridLayout.addView(englishBtn);
            gridLayout.addView(vietnameseBtn);
        }

        btnXaoTron.setOnClickListener(v -> {
            Intent intentDirect = new Intent(ReviewWordActivity.this, CreatePhrase.class);
            startActivity(intentDirect);
        });
    }
    private Button createWordButton(Context context, String text) {
        Button button = new Button(context);
        button.setText(text);
        button.setTextSize(18); // to hơn một chút
        button.setTextColor(Color.BLACK);
        button.setAlpha(0.9f);

        // Tạo drawable bo góc và màu nền
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#E0F7FA")); // màu nền
        drawable.setCornerRadius(dpToPx(12)); // bo góc 12dp
        drawable.setStroke(4,Color.BLACK);

        button.setBackground(drawable);
        // Layout params cho GridLayout
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = dpToPx(160);
        params.height = dpToPx(80);
        params.setMargins(dpToPx(6), dpToPx(6), dpToPx(6), dpToPx(10));
        params.setGravity(Gravity.CENTER); // Căn giữa mỗi nút trong ô của GridLayout
        button.setLayoutParams(params);

        return button;
    }

    private int dpToPx(int dp) {
        return Math.round(dp * getResources().getDisplayMetrics().density);
    }
}