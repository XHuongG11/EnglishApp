package com.example.englishapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.englishapp.R;

public class NoteBookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_note_book);

        ImageView imgSearch = findViewById(R.id.imgSearch);

        // Set sự kiện click cho ImageView
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến DictionaryActivity
                Intent intent = new Intent(NoteBookActivity.this, DictionaryActivity.class);
                startActivity(intent);
            }
        });
        ImageView imgHome = findViewById(R.id.imgHome);

        // Set sự kiện click cho ImageView
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Tạo Intent để chuyển đến HomeActivity
                Intent intent = new Intent(NoteBookActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}