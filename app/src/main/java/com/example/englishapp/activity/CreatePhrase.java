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
import com.example.englishapp.dao.QuestionDAO;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.model.Question;
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
    private QuestionDAO questionDAO; // Thay WordDAO bằng QuestionDAO

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_phrase);

        btnWord1 = findViewById(R.id.btn_word_1);
        btnWord2 = findViewById(R.id.btn_word_2);
        btnWord3 = findViewById(R.id.btn_word);
        btnCheck = findViewById(R.id.btnCheck);
        FrameLayout fragmentFeedback = findViewById(R.id.fragmentFeedback);
        FrameLayout fragmentFeedback1 = findViewById(R.id.fragmentFeedback1);
        TextView vanBanPhu = findViewById(R.id.van_ban_phu);

        questionDAO = new QuestionDAO();

        layoutPhraseContainer = new LinearLayout(this);
        layoutPhraseContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));
        layoutPhraseContainer.setOrientation(LinearLayout.HORIZONTAL);
        layoutPhraseContainer.setPadding(16, 16, 16, 16);
        fragmentFeedback.addView(layoutPhraseContainer);

        questions = new ArrayList<>();
        getDatabase();

        loadQuestion(currentQuestionIndex);
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

    private void getDatabase() {
        // Lấy danh sách câu hỏi từ QuestionDAO
//        questions = questionDAO.getAll();
//        if (questions.isEmpty()) {
//            finish();
//            return;
//        }
    }

    private void loadQuestion(int index) {
        if (index >= questions.size()) {
            finish();
            return;
        }

        currentQuestionIndex = index;
        Question question = questions.get(index);
        TextView vanBanPhu = findViewById(R.id.van_ban_phu);

        vanBanPhu.setText(question.getMeaning());

        btnWord1.setTranslationY(0);
        btnWord2.setTranslationY(0);
        btnWord3.setTranslationY(0);
        layoutPhraseContainer.removeAllViews();

        String[] words = question.getContent().split(" ");
        List<String> units = new ArrayList<>();

        if (words.length == 5) {
            // Câu 5 từ: 1 từ, 2 từ, 2 từ
            units.add(words[0]); // Từ 1
            units.add(words[1] + " " + words[2]); // Từ 2-3
            units.add(words[3] + " " + words[4]); // Từ 4-5
        } else if (words.length >= 6) {
            // Câu 6 từ trở lên: 2 từ, 2 từ, 2 từ
            units.add(words[0] + " " + words[1]); // Từ 1-2
            units.add(words[2] + " " + words[3]); // Từ 3-4
            units.add(words[4] + " " + words[5]); // Từ 5-6
        } else {
            // Câu dưới 5 từ: logic cũ
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

        Collections.shuffle(units);

        btnWord1.setText(units.size() > 0 ? units.get(0) : "");
        btnWord2.setText(units.size() > 1 ? units.get(1) : "");
        btnWord3.setText(units.size() > 2 ? units.get(2) : "");

        updateCheckButtonState();

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