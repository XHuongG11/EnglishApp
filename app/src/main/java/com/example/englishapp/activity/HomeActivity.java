package com.example.englishapp.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishapp.R;
import com.example.englishapp.adapter.TopicAdapter;
import com.example.englishapp.model.Topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
       // getLayoutInflater().inflate(R.layout.activity_home, findViewById(R.id.content_frame));

        RecyclerView recyclerView = findViewById(R.id.rv_topics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Hiển thị ngang
        List<Topic> danhSach = Arrays.asList(
                new Topic("Bài Học 1", "Thì hiện tại: câu khẳng định", new ArrayList<>(), R.drawable.lesson_card_background),
                new Topic("Bài Học 2", "Danh từ số nhiều", new ArrayList<>(), R.drawable.lesson_card_background2),
                new Topic("Bài Học 3", "Thì hiện tại đơn: Câu nghi vấn", new ArrayList<>(), R.drawable.lesson_card_background3),
                new Topic("Bài Học 4", "Thì hiện tại đơn: Câu phủ định", new ArrayList<>(), R.drawable.lesson_card_background4)
        );
        TopicAdapter adapter = new TopicAdapter(danhSach, chuDe -> {
            Toast.makeText(this, "Đã nhấn: " + chuDe.getName(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
    }
}