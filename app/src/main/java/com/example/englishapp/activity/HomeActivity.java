package com.example.englishapp.activity;

import android.content.Intent;
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
import com.example.englishapp.dao.TopicDAO;
import com.example.englishapp.model.Topic;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TopicDAO topicDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Khởi tạo TopicDAO
        topicDAO = new TopicDAO();

        RecyclerView recyclerView = findViewById(R.id.rv_topics);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // Hiển thị ngang

        // Lấy danh sách Topic từ TopicDAO
        List<Topic> danhSach = topicDAO.getAll();
        if (danhSach.isEmpty()) {
            Toast.makeText(this, "Không có chủ đề nào!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Thiết lập Adapter
        TopicAdapter adapter = new TopicAdapter(danhSach, chuDe -> {
            // Xử lý khi nhấn vào Topic
            Toast.makeText(this, "Đã nhấn: " + chuDe.getName(), Toast.LENGTH_SHORT).show();

            // Chuyển đến CreatePhrase với Topic và Practice đầu tiên
            if (!chuDe.getPractices().isEmpty()) {
                Intent intent = new Intent(HomeActivity.this, CreatePhrase.class);
                intent.putExtra("TOPIC_NUMBER", chuDe.getNumberTopic());
                intent.putExtra("PRACTICE_ID", chuDe.getPractices().get(0).getId());
                startActivity(intent);
            } else {
                Toast.makeText(this, "Chủ đề này không có bài tập!", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }
}