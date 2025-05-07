package com.example.englishapp.activity;

import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.englishapp.model.Word;
import com.example.englishapp.util.Sound;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ListenAndWriteActivity extends AppCompatActivity {
    private EditText editWord;
    private TextView prevText;
    private TextView afterText;
    private MaterialButton btnCheck;
    ExoPlayer exoPlayer;
    private Sound sound;
    private Practice practice;
    private List<Question> questions;
    private ImageView imgVolume;
    private int numberQuestion;
    private ProgressBar progressData;
    private PracticeDAO practiceDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_and_write);

        btnCheck = findViewById(R.id.btnCheck);
        exoPlayer = new ExoPlayer.Builder(this).build();
        editWord = findViewById(R.id.editWord);
        prevText = findViewById(R.id.prevText);
        afterText = findViewById(R.id.afterText);
        sound = new Sound(this);
        questions = new ArrayList<>();
        imgVolume = findViewById(R.id.imgVolume);
        progressData = findViewById(R.id.progressData);

        practiceDAO = new PracticeDAO();
        progressData.setVisibility(View.VISIBLE);
        TopicDAO topicDAO = new TopicDAO();
        topicDAO.getByNumberTopic(GlobalData.currentTopic, new OnGetByIdListener<Topic>() {
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
        String audioUrl = "https://dictionary.cambridge.org/media/english/us_pron/h/hit/hit__/hit.mp3";

    }
    private void loadQuestion(int index){
        Question question = questions.get(index);
        loadContent(question);

        imgVolume.setOnClickListener(view -> {
//                        playAudio(audioUrl);
            sound.readText(question.getCorrectAnswer().getNoidung());
        });
        editWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int after) {
                String currentText = charSequence.toString();

                if (!currentText.isEmpty()) {
                    btnCheck.setBackgroundColor(Color.parseColor("#1976D2"));
                    btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                    btnCheck.setText("Kiểm tra");
                    btnCheck.setOnClickListener(v -> {
                        // read text
                        sound.readText(question.getContent());
                        if(currentText.equalsIgnoreCase(question.getCorrectAnswer().getNoidung())){
                            // Gọi Fragment vào FrameLayout correct answer
                            CorrectAnswerFragment fragment = new CorrectAnswerFragment(question);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentFeedback, fragment);
                            transaction.commit();

                            btnCheck.setBackgroundColor(Color.parseColor("#2BB331"));
                            btnCheck.setText("Tiếp theo");
                            btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                            btnCheck.setOnClickListener(view -> {
                                if(index < numberQuestion - 1) {
                                    loadQuestion(index + 1);
                                }
                                // nếu hết câu chuyển tiếp
                                else if(index == numberQuestion - 1){
                                    Intent intentDirect = new Intent(ListenAndWriteActivity.this, ListenAndMatchActivity.class);
                                    startActivity(intentDirect);
                                }
                            });
                        }
                        else{
                            // Gọi Fragment vào FrameLayout correct answer
                            ErrorAnswerFragment fragment = new ErrorAnswerFragment(question);
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.replace(R.id.fragmentFeedback, fragment);
                            transaction.commit();

                            btnCheck.setBackgroundColor(Color.parseColor("#FF3D00"));
                            btnCheck.setText("Tiếp theo");
                            btnCheck.setTextColor(Color.parseColor("#FFFFFF"));
                            btnCheck.setOnClickListener(view -> {
                                if(index < numberQuestion - 1) {
                                    loadQuestion(index + 1);
                                }
                                // nếu hết câu chuyển tiếp
                                else if(index == numberQuestion - 1){
                                    Intent intentDirect = new Intent(ListenAndWriteActivity.this, ListenAndMatchActivity.class);
                                    startActivity(intentDirect);
                                }
                            });
                        }
                    });
                } else {
                    btnCheck.setBackgroundColor(Color.parseColor("#D6D6D6"));
                    btnCheck.setTextColor(Color.parseColor("#949393"));
                    btnCheck.setText("Kiểm tra");
                    btnCheck.setOnClickListener(v -> {
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void loadContent(Question question){
        try {
            String content = question.getContent();
            String correctAnswer = question.getCorrectAnswer().getNoidung();
            String[] parts = content.split(correctAnswer);
            prevText.setText(parts[0].trim());
            afterText.setText(parts[1].trim());
            editWord.setText("");

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentFeedback);
            if (fragment != null) {
                transaction.remove(fragment);
                transaction.commit();
            }
        }
        catch (Exception e){
            Log.d("Error", e.toString());
        }
    }
    private void getDatabase(Topic topic) {
        practiceDAO.getByNumberTopic(topic.getNumberTopic(), new OnGetAllListener<Practice>() {
            @RequiresApi(api = Build.VERSION_CODES.VANILLA_ICE_CREAM)
            @Override
            public void onGetAll(List<Practice> t) {
                Practice practice = t.stream().filter(p -> p.getType().equals(EPracticeType.NGHEVIET))
                        .findFirst().orElse(null);
                if(practice != null){
                    questions = practice.getQuestions();
                    numberQuestion = questions.size();
                    loadQuestion(0);
                    progressData.setVisibility(View.GONE);
                }
            }

            @Override
            public void onGetFailed(Exception e) {

            }
        });
    }

    private void playAudio(String audioUrl) {
        MediaItem mediaItem = MediaItem.fromUri(audioUrl);
        exoPlayer.setMediaItem(mediaItem);
        // Chuẩn bị và phát
        exoPlayer.prepare();
        @OptIn(markerClass = UnstableApi.class) int audioSessionId = exoPlayer.getAudioSessionId();
        exoPlayer.play();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null) {
            exoPlayer.release();
            exoPlayer = null;
        }
    }
    private void initData(){
        QuestionDAO questionDAO = new QuestionDAO();
        List<Question> questions = new ArrayList<>();

        // Câu 1
        questions.add(new Question(6L, "He wants tea", "Anh ấy muốn trà", new Word("wants"), null));

        // Câu 2
        questions.add(new Question(7L, "I like my cat", "Tôi thích con mèo của tôi", new Word("like"), null));

        // Câu 3
        questions.add(new Question(8L, "He plays football", "Anh ấy chơi bóng đá", new Word("plays"), null));

        // Câu 4
        questions.add(new Question(9L, "She studies math", "Cô ấy học toán", new Word("studies"), null));

        // Câu 5
        questions.add(new Question(10L, "They go to the park", "Họ đi đến công viên", new Word("go"), null));

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
                    Practice practice = new Practice(2L, EPracticeType.NGHEVIET, topic, questions);
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

            }
        });

    }

}