package com.example.englishapp.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishapp.R;
import com.example.englishapp.adapter.AnswerAdapter;
import com.example.englishapp.customUI.SpacesItemDecoration;
import com.example.englishapp.dao.OnGetAllListener;
import com.example.englishapp.dao.OnGetByIdListener;
import com.example.englishapp.dao.OnSaveUpdateListener;
import com.example.englishapp.dao.PracticeDAO;
import com.example.englishapp.dao.QuestionDAO;
import com.example.englishapp.dao.TopicDAO;
import com.example.englishapp.databinding.ActivityListenAndMatchBinding;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.model.EPracticeType;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
import com.example.englishapp.model.Topic;
import com.example.englishapp.model.Word;
import com.example.englishapp.util.Sound;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListenAndMatchActivity extends AppCompatActivity {
    private FlexboxLayout layoutPhraseContainer;
    ActivityListenAndMatchBinding binding;
    QuestionDAO questionDAO;
    PracticeDAO practiceDAO;
    Button btnCheck;
    FrameLayout fragmentFeedback;
    private List<Question> questions = new ArrayList<>();
    private Question currentQuestion;
    private ImageView imgVolume;
    private Sound sound;
    private ProgressBar progressAnswer;
    private int indexCurrentQuestion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityListenAndMatchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(binding.getRoot(), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressAnswer = binding.progressAnswer;
        practiceDAO = new PracticeDAO();
        questionDAO = new QuestionDAO();
        sound = new Sound(this);
        FlexboxLayout fragmentFeedback = binding.fragmentFeedback;
        btnCheck = binding.include.btnCheck;
        imgVolume = findViewById(R.id.imgVolume);
        layoutPhraseContainer = new FlexboxLayout(this);
        layoutPhraseContainer.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        ));

        layoutPhraseContainer.setFlexWrap(FlexWrap.WRAP); // Cho phép xuống dòng
        layoutPhraseContainer.setFlexDirection(FlexDirection.ROW); // Căn từ trái sang phải

        layoutPhraseContainer.setPadding(16, 16, 16, 16);
        fragmentFeedback.addView(layoutPhraseContainer);

        btnCheck.setOnClickListener(v -> {
            updateCheckButtonState();
        });


        progressAnswer.setVisibility(View.VISIBLE);
        TopicDAO topicDAO = new TopicDAO();
        topicDAO.getByNumberTopic("Topic 1", new OnGetByIdListener<Topic>() {
            @Override
            public void onGetByID(Topic topic) {
                if(topic != null){
                    Log.d("DEBUG", "topic");
                    getDatabase(topic);
                }
            }

            @Override
            public void onGetFailed(Exception e) {

            }
        });
    }

    private void loadAnswer(Question currentQuestion) {
        RecyclerView recyclerView = binding.recyclerViewAnwers;

        imgVolume.setOnClickListener(view -> {
//                        playAudio(audioUrl);
            sound.readText(currentQuestion.getContent());
        });

        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(this);
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);

        recyclerView.setLayoutManager(flexboxLayoutManager);

        int spacingInPixels = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        recyclerView.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        AnswerAdapter adapter = new AnswerAdapter(this, this, currentQuestion.getAnswers(), layoutPhraseContainer);
        recyclerView.setAdapter(adapter);
    }

    private void getDatabase(Topic topic) {
        practiceDAO.getByNumberTopic(topic.getNumberTopic(), new OnGetAllListener<Practice>() {
            @Override
            public void onGetAll(List<Practice> t) {
                Practice practice = t.stream().filter(p -> p.getType().equals(EPracticeType.NGHEGHEP))
                        .findFirst().orElse(null);
                if(practice != null){
                    Log.d("DEBUG", "questionnnn");
                    questions = practice.getQuestions();
                    indexCurrentQuestion = 0;
                    currentQuestion = questions.get(indexCurrentQuestion);
                    loadAnswer(currentQuestion);
                    progressAnswer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onGetFailed(Exception e) {

            }
        });
    }
    public void updateCheckButtonState() {
        if (layoutPhraseContainer.getChildCount() > 0) {
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
                Log.d("DEBUG", userAnswer);
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
                    indexCurrentQuestion += 1;
                    currentQuestion = questions.get(indexCurrentQuestion);
                    layoutPhraseContainer.removeAllViews();
                    FragmentManager fragmentManager1 = getSupportFragmentManager();
                    FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                    Fragment fragment = fragmentManager1.findFragmentById(R.id.fragmentFeedback1);
                    if (fragment != null) {
                        transaction1.remove(fragment);
                        transaction1.commit();
                    }
                    loadAnswer(currentQuestion);
                });
            });
        } else {
            btnCheck.setBackgroundColor(Color.parseColor("#D6D6D6"));
            btnCheck.setTextColor(Color.parseColor("#949393"));
            btnCheck.setText("Kiểm tra");
            btnCheck.setOnClickListener(v -> {});
        }
    }

    public void initData() {
        List<Topic> topics = new ArrayList<>();
        Long nextTopicId = 1L;
        Long nextPracticeId = 1L;

        // Chủ đề 1: Thì hiện tại đơn: Câu khẳng định
        Topic topic1 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu khẳng định", null, R.drawable.lesson_card_background);
        topics.add(topic1);

        // Chủ đề 2: Danh từ số nhiều
        Topic topic2 = new Topic("Topic " + nextTopicId++, "Danh từ số nhiều", null, R.drawable.lesson_card_background2);
        topics.add(topic2);

        // Chủ đề 3: Thì hiện tại đơn: Câu nghi vấn
        Topic topic3 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu nghi vấn", null, R.drawable.lesson_card_background3);
        topics.add(topic3);

        // Chủ đề 4: Thì hiện tại đơn: Câu phủ định
        Topic topic4 = new Topic("Topic " + nextTopicId++, "Thì hiện tại đơn: Câu phủ định", null, R.drawable.lesson_card_background4);
        topics.add(topic4);

        TopicDAO topicDAO = new TopicDAO();
        for (Topic q : topics) {
            topicDAO.create(q, success -> {
                if (success) {
                    Log.d("CREATE_QUESTION", "Question saved successfully");
                } else {
                    Log.e("CREATE_QUESTION", "Failed to save question");
                }
            });
        }

        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = new ArrayList<>();

        // Câu 1
        List<Word> answers1 = new ArrayList<>();
        answers1.add(new Word("plays"));
        answers1.add(new Word("play"));
        answers1.add(new Word("He"));
        answers1.add(new Word("football"));
        questions.add(new Question(1L, "He plays football", "Anh ấy chơi bóng đá", null, answers1));

        // Câu 2
        List<Word> answers2 = new ArrayList<>();
        answers2.add(new Word("goes"));
        answers2.add(new Word("go"));
        answers2.add(new Word("She"));
        answers2.add(new Word("to school"));
        questions.add(new Question(2L, "She goes to school", "Cô ấy đi học", null, answers2));

        // Câu 3
        List<Word> answers3 = new ArrayList<>();
        answers3.add(new Word("brushes"));
        answers3.add(new Word("brush"));
        answers3.add(new Word("He"));
        answers3.add(new Word("brushed"));
        questions.add(new Question(3L, "He brushes his teeth", "Anh ấy đánh răng", null, answers3));

        // Câu 4
        List<Word> answers4 = new ArrayList<>();
        answers4.add(new Word("watches"));
        answers4.add(new Word("watch"));
        answers4.add(new Word("I"));
        answers4.add(new Word("TV"));
        questions.add(new Question(4L, "I watch TV", "Cô ấy xem TV", null, answers4));

        // Câu 5
        List<Word> answers5 = new ArrayList<>();
        answers5.add(new Word("studies"));
        answers5.add(new Word("study"));
        answers5.add(new Word("He"));
        answers5.add(new Word("English"));
        questions.add(new Question(5L, "He studies English", "Anh ấy học tiếng Anh", null, answers5));

        for (Question q : questions) {
            questionDAO.create(q, success -> {
                if (success) {
                    Log.d("CREATE_QUESTION", "Question saved successfully");
                } else {
                    Log.e("CREATE_QUESTION", "Failed to save question");
                }
            });
        }

        Practice practice = new Practice(1L, EPracticeType.NGHEGHEP, topic1, questions);
        PracticeDAO practiceDAO = new PracticeDAO();
        practiceDAO.create(practice, isSuccess -> {
            if (isSuccess) {
                Log.d("CREATE_QUESTION", "Question saved successfully");
            } else {
                Log.e("CREATE_QUESTION", "Failed to save question");
            }
        });

    }
}