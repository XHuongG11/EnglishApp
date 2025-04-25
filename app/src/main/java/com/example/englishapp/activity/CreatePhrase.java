package com.example.englishapp.activity;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.englishapp.model.Word;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CreatePhrase extends AppCompatActivity {

    private LinearLayout layoutPhraseContainer;
    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private MaterialButton btnCheck;
    private Button btnWord1, btnWord2, btnWord3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phrase);

        btnWord1 = findViewById(R.id.btn_word_1);
        btnWord2 = findViewById(R.id.btn_word_2);
        btnWord3 = findViewById(R.id.btn_word_3);
        btnCheck = findViewById(R.id.btnCheck); // Giả định trong @layout/bottom_excercise
        FrameLayout fragmentFeedback = findViewById(R.id.fragmentFeedback);
        FrameLayout fragmentFeedback1 = findViewById(R.id.fragmentFeedback1);
        TextView vanBanPhu = findViewById(R.id.van_ban_phu);

        // Khởi tạo container chứa cụm từ trong fragmentFeedback
        layoutPhraseContainer = new LinearLayout(this);
        layoutPhraseContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));
        layoutPhraseContainer.setOrientation(LinearLayout.HORIZONTAL);
        layoutPhraseContainer.setPadding(16, 16, 16, 16);
        fragmentFeedback.addView(layoutPhraseContainer);

        // Tạo dữ liệu mẫu
        questions = new ArrayList<>();
        getDatabase();

        // Load câu hỏi đầu tiên
        loadQuestion(currentQuestionIndex);

        // Khởi tạo trạng thái ban đầu cho nút Kiểm tra
        updateCheckButtonState();

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
    }

    private void loadQuestion(int index) {
        if (index >= questions.size()) {
            finish(); // Kết thúc nếu hết câu hỏi
            return;
        }

        currentQuestionIndex = index;
        Question question = questions.get(index);
        TextView vanBanPhu = findViewById(R.id.van_ban_phu);

        vanBanPhu.setText(question.getMeaning());

        // Reset các nút và container
        btnWord1.setTranslationY(0);
        btnWord2.setTranslationY(0);
        btnWord3.setTranslationY(0);
        layoutPhraseContainer.removeAllViews();

        // Gán từ cho các nút
        String[] words = question.getContent().split(" ");
        // Tạo danh sách các đơn vị để xáo trộn
        List<String> units = new ArrayList<>();
        if (words.length > 0) {
            units.add(words[0]); // Từ đầu tiên
        }
        if (words.length > 1) {
            units.add(words[1]); // Từ thứ hai
        }
        if (words.length > 2) {
            // Gộp các từ từ thứ 3 trở đi
            StringBuilder remainingWords = new StringBuilder();
            for (int i = 2; i < words.length; i++) {
                remainingWords.append(words[i]);
                if (i < words.length - 1) {
                    remainingWords.append(" ");
                }
            }
            units.add(remainingWords.toString());
        }

        // Xáo trộn các đơn vị
        Collections.shuffle(units);

        // Gán các đơn vị đã xáo trộn vào nút
        btnWord1.setText(units.size() > 0 ? units.get(0) : "");
        btnWord2.setText(units.size() > 1 ? units.get(1) : "");
        btnWord3.setText(units.size() > 2 ? units.get(2) : "");

        // Reset nút Kiểm tra
        updateCheckButtonState();

        // Xóa fragment hiện tại trong fragmentFeedback1
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
                    // Đáp án đúng
                    CorrectAnswerFragment fragment = new CorrectAnswerFragment(currentQuestion);
                    transaction.replace(R.id.fragmentFeedback1, fragment);
                    btnCheck.setBackgroundColor(Color.parseColor("#2BB331"));
                    btnCheck.setText("Tiếp theo");
                    btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                } else {
                    // Đáp án sai
                    ErrorAnswerFragment fragment = new ErrorAnswerFragment(currentQuestion);
                    transaction.replace(R.id.fragmentFeedback1, fragment);
                    btnCheck.setBackgroundColor(Color.parseColor("#FF3D00"));
                    btnCheck.setText("Tiếp theo");
                    btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                }

                transaction.commit();

                // Cập nhật nút để sang câu hỏi tiếp theo
                btnCheck.setOnClickListener(view -> {
                    loadQuestion(currentQuestionIndex + 1);
                });
            });
        } else {
            btnCheck.setBackgroundColor(Color.parseColor("#D6D6D6"));
            btnCheck.setTextColor(Color.parseColor("#949393"));
            btnCheck.setText("Kiểm tra");
            btnCheck.setOnClickListener(v -> {
                // Không làm gì nếu chưa đủ 3 nút
            });
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

    private void getDatabase() {
        // Dữ liệu mẫu
        Word correctAnswer1 = new Word(1L, "wants", "muốn (số nhiều)", "/wɒnts/", "She wants a book.", null, null);
        List<Word> answers1 = new ArrayList<>();
        answers1.add(correctAnswer1);
        questions.add(new Question(1L, "He wants tea", "Anh ấy muốn uống trà", correctAnswer1, answers1));

        Word correctAnswer2 = new Word(4L, "like", "thích", "/laɪk/", "I like my cat.", null, null);
        List<Word> answers2 = new ArrayList<>();
        answers2.add(correctAnswer2);
        questions.add(new Question(2L, "I like my cat", "Tôi thích con mèo của tôi", correctAnswer2, answers2));

        Word correctAnswer3 = new Word(7L, "eats", "ăn (số nhiều)", "/iːts/", "She eats an apple.", null, null);
        List<Word> answers3 = new ArrayList<>();
        answers3.add(correctAnswer3);
        questions.add(new Question(3L, "She eats rice", "Cô ấy ăn cơm", correctAnswer3, answers3));
    }
}