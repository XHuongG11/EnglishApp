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
import com.example.englishapp.dao.OnGetAllListener;
import com.example.englishapp.dao.OnGetByIdListener;
import com.example.englishapp.dao.PracticeDAO;
import com.example.englishapp.dao.QuestionDAO;
import com.example.englishapp.dao.TopicDAO;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.global.GlobalData;
import com.example.englishapp.model.EPracticeType;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
import com.example.englishapp.model.Topic;
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
    private TextView vanBanPhu;
    private PracticeDAO practiceDAO;

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

        // Khởi tạo PracticeDAO
        practiceDAO = new PracticeDAO();

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

        // Tải dữ liệu
        TopicDAO topicDAO = new TopicDAO();
        topicDAO.getByNumberTopic(GlobalData.currentTopic, new OnGetByIdListener<Topic>() {
            @Override
            public void onGetByID(Topic topic) {
                if (topic != null) {
                    Log.d("DEBUG", "topic");
                    getDatabase(topic);
                } else {
                    Log.w("FIREBASE", "Topic is null for currentTopic: " + GlobalData.currentTopic);
                    finish();
                }
            }

            @Override
            public void onGetFailed(Exception e) {
                Log.e("FIREBASE", "Lỗi khi lấy dữ liệu topic", e);
                finish();
            }
        });

        // Khởi tạo dữ liệu câu hỏi
        // initData();
    }

    private void initData() {
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = new ArrayList<>();

        // Câu 1
        questions.add(new Question(11L, "I am happy", "Tôi đang hạnh phúc", null, null));

        // Câu 2
        questions.add(new Question(12L, "She is singing", "Cô ấy đang hát", null, null));

        // Câu 3
        questions.add(new Question(13L, "He runs fast", "Anh ấy chạy nhanh", null, null));

        // Câu 4
        questions.add(new Question(14L, "They are friends", "Họ là bạn bè", null, null));

        // Câu 5
        questions.add(new Question(15L, "We play games", "Chúng tôi chơi trò chơi", null, null));

        // Lưu các câu hỏi vào Firestore
        for (Question q : questions) {
            questionDAO.create(q, success -> {
                if (success) {
                    Log.d("CREATE_QUESTION", "Question saved successfully");
                } else {
                    Log.e("CREATE_QUESTION", "Failed to save question");
                }
            });
        }

        // Sau khi lưu các câu hỏi, lưu Practice
        TopicDAO topicDAO = new TopicDAO();
        topicDAO.getByNumberTopic("Topic 1", new OnGetByIdListener<Topic>() {
            @Override
            public void onGetByID(Topic topic) {
                if (topic != null) {
                    Practice practice = new Practice(3L, EPracticeType.GHEPTU, topic, questions);
                    PracticeDAO practiceDAO = new PracticeDAO();
                    practiceDAO.create(practice, isSuccess -> {
                        if (isSuccess) {
                            Log.d("CREATE_PRACTICE", "Practice saved successfully");
                        } else {
                            Log.e("CREATE_PRACTICE", "Failed to save practice");
                        }
                    });
                }
            }

            @Override
            public void onGetFailed(Exception e) {
                Log.e("CREATE_PRACTICE", "Failed to get topic", e);
            }
        });
    }

    private void getDatabase(Topic topic) {
        Log.d("FIREBASE", "Fetching practices for topic: " + topic.getNumberTopic());
        practiceDAO.getByNumberTopic(topic.getNumberTopic(), new OnGetAllListener<Practice>() {
            @Override
            public void onGetAll(List<Practice> practices) {
                Log.d("FIREBASE", "Retrieved " + practices.size() + " practices");
                Practice practice = practices.stream()
                        .filter(p -> p.getType().equals(EPracticeType.GHEPTU))
                        .findFirst()
                        .orElse(null);
                if (practice != null) {
                    questions.clear();
                    questions.addAll(practice.getQuestions());
                    Log.d("FIREBASE", "Lấy dữ liệu thành công. Số câu hỏi: " + questions.size());
                    if (questions.isEmpty()) {
                        Log.w("FIREBASE", "Danh sách câu hỏi trống. Kết thúc activity.");
                        finish();
                        return;
                    }
                    loadQuestion(currentQuestionIndex);
                    updateCheckButtonState();
                } else {
                    Log.w("FIREBASE", "Không tìm thấy practice phù hợp.");
                    finish();
                }
            }

            @Override
            public void onGetFailed(Exception e) {
                Log.e("FIREBASE", "Lỗi khi lấy dữ liệu practice", e);
                finish();
            }
        });
    }

    private void loadQuestion(int index) {
        if (index >= questions.size()) {
            Log.w("FIREBASE", "Index vượt quá kích thước danh sách: " + index);
            Intent intentDirect = new Intent(CreatePhrase.this, ListenAndWriteActivity.class);
            startActivity(intentDirect);
            finish();
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