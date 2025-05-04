package com.example.englishapp.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.englishapp.R;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.model.Question;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreatePhrase extends AppCompatActivity {

    private LinearLayout layoutPhraseContainer;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private MaterialButton btnCheck;
    private Button btnWord1, btnWord2, btnWord3;
    private TextView vanBanPhu;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phrase);

        // Khởi tạo các thành phần giao diện
        btnWord1 = findViewById(R.id.btn_word_1);
        btnWord2 = findViewById(R.id.btn_word_2);
        btnWord3 = findViewById(R.id.btn_word_3);
        btnCheck = findViewById(R.id.btnCheck);
        FrameLayout fragmentFeedback = findViewById(R.id.fragmentFeedback);
        FrameLayout fragmentFeedback1 = findViewById(R.id.fragmentFeedback1);
        vanBanPhu = findViewById(R.id.van_ban_phu);

        // Khởi tạo Firestore
        db = FirebaseFirestore.getInstance();

        // Khởi tạo layout chứa các từ được chọn
        layoutPhraseContainer = new LinearLayout(this);
        layoutPhraseContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));
        layoutPhraseContainer.setOrientation(LinearLayout.HORIZONTAL);
        layoutPhraseContainer.setPadding(16, 16, 16, 16);
        fragmentFeedback.addView(layoutPhraseContainer);

        // Khởi tạo danh sách câu hỏi
        questions = new ArrayList<>();

        // Thiết lập sự kiện click cho các nút
        btnWord1.setOnClickListener(v -> {
            animateButtonUp(btnWord1);
            addWordToPhraseFrom(btnWord1);
            updateCheckButtonState();
        });

        btnWord2.setOnClickListener(v -> {
            animateButtonUp(btnWord2);
            addWordToPhraseFrom(btnWord2);
            updateCheckButtonState();
        });

        btnWord3.setOnClickListener(v -> {
            animateButtonUp(btnWord3);
            addWordToPhraseFrom(btnWord3);
            updateCheckButtonState();
        });

        // Tải dữ liệu từ Firestore
        getDatabase();
    }

    private void getDatabase() {
        // Lấy dữ liệu từ Firestore
        db.collection("questions")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        questions.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Long id = document.getLong("id");
                            String content = document.getString("content");
                            String meaning = document.getString("meaning");

                            // Log dữ liệu của từng document để debug
                            Log.d("FIREBASE", "Document: id=" + id + ", content=" + content + ", meaning=" + meaning);

                            if (content != null && meaning != null) {
                                Question question = new Question(id, content, meaning, null, null);
                                questions.add(question);
                            } else {
                                Log.w("FIREBASE", "Bỏ qua document vì content hoặc meaning là null");
                            }
                        }

                        // Log số lượng câu hỏi sau khi xử lý
                        Log.d("FIREBASE", "Lấy dữ liệu thành công. Số câu hỏi: " + questions.size());

                        if (questions.isEmpty()) {
                            Log.w("FIREBASE", "Danh sách câu hỏi trống. Kết thúc activity.");
                            finish();
                            return;
                        }

                        // Tải câu hỏi đầu tiên và cập nhật trạng thái nút kiểm tra sau khi lấy dữ liệu
                        loadQuestion(currentQuestionIndex);
                        updateCheckButtonState();
                    } else {
                        // Xử lý lỗi
                        Log.e("FIREBASE", "Lỗi khi lấy dữ liệu từ Firestore", task.getException());
                        finish();
                    }
                });
    }

    private void loadQuestion(int index) {
        if (index >= questions.size()) {
            Log.w("FIREBASE", "Index vượt quá kích thước danh sách: " + index);
            finish();
            // hiện trang hoàn thành bài học
            Intent intentDirect = new Intent(CreatePhrase.this, ListenAndWriteActivity.class);
            startActivity(intentDirect);
            return;
        }

        currentQuestionIndex = index;
        Question question = questions.get(index);

        // Log dữ liệu câu hỏi để debug
        Log.d("FIREBASE", "Tải câu hỏi: " + question.getContent() + ", meaning: " + question.getMeaning());

        // Hiển thị nghĩa của câu hỏi
        vanBanPhu.setText(question.getMeaning());

        // Reset vị trí các nút và xóa các từ đã chọn trước đó
        btnWord1.setTranslationY(0);
        btnWord2.setTranslationY(0);
        btnWord3.setTranslationY(0);
        layoutPhraseContainer.removeAllViews();

        // Chia câu hỏi thành các phần (units)
        String[] words = question.getContent().split(" ");
        List<String> units = new ArrayList<>();

        if (words.length == 5) {
            units.add(words[0]);
            units.add(words[1] + " " + words[2]);
            units.add(words[3] + " " + words[4]);
        } else if (words.length >= 6) {
            units.add(words[0] + " " + words[1]);
            units.add(words[2] + " " + words[3]);
            units.add(words[4] + " " + words[5]);
        } else {
            if (words.length > 0) units.add(words[0]);
            if (words.length > 1) units.add(words[1]);
            if (words.length > 2) {
                StringBuilder remainingWords = new StringBuilder();
                for (int i = 2; i < words.length; i++) {
                    remainingWords.append(words[i]);
                    if (i < words.length - 1) remainingWords.append(" ");
                }
                units.add(remainingWords.toString());
            }
        }

        // Xáo trộn các phần để hiển thị ngẫu nhiên
        Collections.shuffle(units);

        // Hiển thị các phần lên các nút và log để debug
        String word1 = units.size() > 0 ? units.get(0) : "";
        String word2 = units.size() > 1 ? units.get(1) : "";
        String word3 = units.size() > 2 ? units.get(2) : "";
        Log.d("FIREBASE", "Hiển thị các từ: word1=" + word1 + ", word2=" + word2 + ", word3=" + word3);

        btnWord1.setText(word1);
        btnWord2.setText(word2);
        btnWord3.setText(word3);

        // Cập nhật trạng thái nút kiểm tra
        updateCheckButtonState();

        // Xóa fragment phản hồi nếu có
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragmentManager.findFragmentById(R.id.fragmentFeedback1) != null) {
            transaction.remove(fragmentManager.findFragmentById(R.id.fragmentFeedback1));
            transaction.commit();
        }
    }

    private void addWordToPhraseFrom(Button originalButton) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 20, 0);
        Button clone = new Button(this);
        clone.setLayoutParams(params);
        clone.setText(originalButton.getText());
        clone.setAllCaps(false);
        clone.setTextSize(originalButton.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
        clone.setTextColor(originalButton.getTextColors());
        clone.setBackground(originalButton.getBackground());
        clone.setTypeface(originalButton.getTypeface());
        clone.setPadding(
                originalButton.getPaddingLeft(),
                originalButton.getPaddingTop(),
                originalButton.getPaddingRight(),
                originalButton.getPaddingBottom()
        );
        clone.setTag(originalButton.getId());

        clone.setOnClickListener(v -> {
            int originalId = (int) v.getTag();
            removeWordFromPhrase(originalId);
            Button original = findViewById(originalId);
            animateButtonDown(original);
            updateCheckButtonState();
        });
        layoutPhraseContainer.addView(clone);
    }

    private void removeWordFromPhrase(int viewId) {
        for (int i = 0; i < layoutPhraseContainer.getChildCount(); i++) {
            View child = layoutPhraseContainer.getChildAt(i);
            if ((int) child.getTag() == viewId) {
                layoutPhraseContainer.removeViewAt(i);
                break;
            }
        }
    }

    private void updateCheckButtonState() {
        if (layoutPhraseContainer.getChildCount() == 3) {
            btnCheck.setBackgroundColor(Color.parseColor("#1976D2"));
            btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
            btnCheck.setText("Kiểm tra");
            btnCheck.setOnClickListener(v -> {
                StringBuilder constructedPhrase = new StringBuilder();
                for (int i = 0; i < layoutPhraseContainer.getChildCount(); i++) {
                    Button wordButton = (Button) layoutPhraseContainer.getChildAt(i);
                    constructedPhrase.append(wordButton.getText()).append(" ");
                }
                String userAnswer = constructedPhrase.toString().trim();
                Question currentQuestion = questions.get(currentQuestionIndex);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();

                if (userAnswer.equalsIgnoreCase(currentQuestion.getContent())) {
                    CorrectAnswerFragment fragment = new CorrectAnswerFragment(currentQuestion);
                    transaction.replace(R.id.fragmentFeedback1, fragment);
                    btnCheck.setBackgroundColor(Color.parseColor("#2BB331"));
                    btnCheck.setText("Tiếp theo");
                    btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    ErrorAnswerFragment fragment = new ErrorAnswerFragment(currentQuestion);
                    transaction.replace(R.id.fragmentFeedback1, fragment);
                    btnCheck.setBackgroundColor(Color.parseColor("#FF3D00"));
                    btnCheck.setText("Tiếp theo");
                    btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                }

                transaction.commit();

                btnCheck.setOnClickListener(view -> {
                    loadQuestion(currentQuestionIndex + 1);
                });
            });
        } else {
            btnCheck.setBackgroundColor(Color.parseColor("#D6D6D6"));
            btnCheck.setTextColor(Color.parseColor("#949393"));
            btnCheck.setText("Kiểm tra");
            btnCheck.setOnClickListener(v -> {});
        }
    }

    private void animateButtonUp(View button) {
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        float translationY = -screenHeight / 4f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", translationY);
        animator.setDuration(300);
        animator.start();
    }

    private void animateButtonDown(View button) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", 0f);
        animator.setDuration(300);
        animator.start();
    }
}