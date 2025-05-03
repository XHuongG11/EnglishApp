package com.example.englishapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.englishapp.R;
import com.example.englishapp.adapter.ListenAndRepeatAdapter;
import com.example.englishapp.model.CardItem;
import com.example.englishapp.model.Word;
import com.example.englishapp.util.PronunciationChecker;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ListenAndRepeatActivity extends AppCompatActivity {
    private PronunciationChecker pronunciationChecker;
    private ListenAndRepeatAdapter adapter;
    private List<CardItem> cardItems;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_and_repeat);

        // Khởi tạo PronunciationChecker trước
        pronunciationChecker = new PronunciationChecker(this, "");

        // Tìm ListView
        ListView listViewCards = findViewById(R.id.listViewCards);

        // Tạo danh sách CardItem với từ thực tế
        cardItems = new ArrayList<>();
        cardItems.add(new CardItem(
                new Word(
                        1L,
                        "ability",
                        "khả năng, tài năng",
                        "əˈbɪl.ə.ti",
                        "He had no doubts about his team's ability to reach the World Cup finals. ...",
                        "https://images.unsplash.com/photo-1506362802973-bd1717de901c?...",
                        null
                ),
                true
        ));
        cardItems.add(new CardItem(
                new Word(
                        2L,
                        "accident",
                        "tai nạn, sự tình cờ",
                        "ˈæk.sɪ.dənt",
                        "I was hardly able to move my arm after the accident. ...",
                        "https://images.unsplash.com/photo-1597328290883-50c5787b7c7e?...",
                        null
                ),
                true
        ));
        cardItems.add(new CardItem(
                new Word(
                        3L,
                        "accurate",
                        "đúng đắn, chính xác",
                        "ˈæk.jə.rət",
                        "We'll need accurate costings before we can agree to fund the scheme. ...",
                        "https://images.unsplash.com/photo-1616337865743-bd29011bc36d?...",
                        null
                ),
                true
        ));
        cardItems.add(new CardItem(
                new Word(
                        4L,
                        "adapt",
                        "sửa lại cho hợp",
                        "əˈdæpt",
                        "To remain competitive the company has to be able to adapt to the changing marketplace. ...",
                        "https://images.unsplash.com/photo-1599723823444-c33ea4336db3?...",
                        null
                ),
                true
        ));

        adapter = new ListenAndRepeatAdapter(this, cardItems);
        listViewCards.setAdapter(adapter);

        // Theo dõi item được chọn
        listViewCards.setOnItemClickListener((parent, view, position, id) -> {
            selectedPosition = position;
            adapter.notifyDataSetChanged(); // Cập nhật giao diện khi chọn từ
            Toast.makeText(this, "Selected: " + cardItems.get(position).getWord().getNoidung(), Toast.LENGTH_SHORT).show();
        });

        // Tìm voiceButton trong layout an_va_noi.xml (action_button)
        View actionButton = findViewById(R.id.action_button);
        LinearLayout voiceButton = actionButton.findViewById(R.id.voiceButton);
        if (voiceButton != null) {
            voiceButton.setOnClickListener(v -> {
                CardItem selectedCard = cardItems.get(selectedPosition);
                String targetText = selectedCard.getWord().getNoidung();
                if (pronunciationChecker != null && pronunciationChecker.hasRecordAudioPermission()) {
                    pronunciationChecker.setTargetText(targetText);
                    pronunciationChecker.setOnPronunciationResultListener(new PronunciationChecker.OnPronunciationResultListener() {
                        @Override
                        public void onPronunciationResult(String targetText, String spokenText, int accuracyPercentage, String formattedFeedback) {
                            findViewById(R.id.gandungButton).setVisibility(View.GONE);
                            findViewById(R.id.hoanhaoButton).setVisibility(View.GONE);
                            findViewById(R.id.voiceButton).setVisibility(View.GONE);

                            if (accuracyPercentage >= 90) {
                                findViewById(R.id.hoanhaoButton).setVisibility(View.VISIBLE);
                                // Chuyển sang từ tiếp theo nếu đạt >= 90%
                                if (selectedPosition < cardItems.size() - 1) {
                                    selectedPosition++;
                                    adapter.notifyDataSetChanged();
                                    listViewCards.smoothScrollToPosition(selectedPosition);
                                    Toast.makeText(ListenAndRepeatActivity.this, "Moving to next word: " + cardItems.get(selectedPosition).getWord().getNoidung(), Toast.LENGTH_SHORT).show();
                                }
                            } else if (accuracyPercentage >= 70) {
                                findViewById(R.id.gandungButton).setVisibility(View.VISIBLE);
                            } else {
                                findViewById(R.id.voiceButton).setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(String errorMessage) {
                            Toast.makeText(ListenAndRepeatActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onListeningStarted() {
                            Toast.makeText(ListenAndRepeatActivity.this, "Listening...", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onListeningFinished() {
                            Toast.makeText(ListenAndRepeatActivity.this, "Finished listening", Toast.LENGTH_SHORT).show();
                        }
                    });
                    pronunciationChecker.startListening();
                } else {
                    Toast.makeText(this, "Permission required or PronunciationChecker is null", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "voiceButton not found in an_va_noi.xml", Toast.LENGTH_SHORT).show();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 100);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pronunciationChecker != null) {
            pronunciationChecker.release();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    // Getter để ListenAndRepeatAdapter lấy selectedPosition
    public int getSelectedPosition() {
        return selectedPosition;
    }
}