package com.example.englishapp.activity;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.englishapp.R;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.model.Practice;
import com.example.englishapp.model.Question;
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

        getDatabase();
        numberQuestion = questions.size();
        loadQuestion(0);
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
                                if(index < numberQuestion) {
                                    loadQuestion(index + 1);
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
                                if(index < numberQuestion) {
                                    loadQuestion(index + 1);
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
            Log.d("DEBUG", "load content");
            String content = question.getContent();
            String correctAnswer = question.getCorrectAnswer().getNoidung();
            Log.d("DEBUG", "correct answer: " + correctAnswer + ", content: " + content);
            String[] parts = content.split(correctAnswer);
            Log.d("DEBUG", parts[0] + ", " + parts[1]);
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
    private void getDatabase() {
        Word word = new Word();
        word.setNoidung("wants");
        questions.add(new Question(1L, "He wants tea", "Anh ấy muốn uống  trà", word, null));

        Word word2 = new Word();
        word2.setNoidung("like");
        questions.add(new Question(2L, "I like my cat", "Tôi thích con mèo của tôi", word2, null));
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
}