package com.example.englishapp.activity;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.SpeechRecognizer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.englishapp.R;
import com.example.englishapp.model.Word;
import com.example.englishapp.util.PronunciationChecker;
import com.example.englishapp.util.Sound;

import java.util.Arrays;
import java.util.List;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;


public class LearnWordByImageActivity extends AppCompatActivity {
    private static int index = 0;
    private GestureDetector gestureDetector;
    private Sound sound;
    public static List<Word> getWordList() {
        return Arrays.asList(
                new Word(
                        1L,
                        "ability",
                        "khả năng, tài năng",
                        "əˈbɪl.ə.ti",
                        "He had no doubts about his team's ability to reach the World Cup finals. Computer literacy is now as essential as the ability to drive a car. Organizational ability is essential in a good manager. Man's ability to talk makes him unlike any other animal. All the children are taught together in one class, regardless of their ability.",
                        "https://images.unsplash.com/photo-1506362802973-bd1717de901c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhYmlsaXR5fGVufDB8fHx8MTc0NDk1ODI3NHww&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                ),
                new Word(
                        2L,
                        "accident",
                        "tai nạn, sự tình cờ",
                        "ˈæk.sɪ.dənt",
                        "I was hardly able to move my arm after the accident. She injured her spine in a riding accident. Did you leave his name out by accident or was it intentional? It was an accident - I didn't mean to break it, honestly. The company refused to accept responsibility for theSessions accident.",
                        "https://images.unsplash.com/photo-1597328290883-50c5787b7c7e?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhY2NpZGVudHxlbnwwfHx8fDE3NDQ5NTk4MDh8MA&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                ),
                new Word(
                        3L,
                        "accurate",
                        "đúng đắn, chính xác",
                        "ˈæk.jə.rət",
                        "We'll need accurate costings before we can agree to fund the scheme. The number is accurate to three decimal places. The film makes no attempt to be historically accurate. Her predictions turned out to be uncannily accurate. I hereby certify that the above information is true and accurate.",
                        "https://images.unsplash.com/photo-1616337865743-bd29011bc36d?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhY2N1cmF0ZXxlbnwwfHx8fDE3NDQ5NTk4MTh8MA&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                ),
                new Word(
                        4L,
                        "adapt",
                        "sửa lại cho hợp",
                        "əˈdæpt",
                        "To remain competitive the company has to be able to adapt to the changing marketplace. We live in a changing world and people must learn to adapt. The two cultures were so utterly disparate that she found it hard to adapt from one to the other. The novel has been adapted for screen by a famous Hollywood director. The best-selling computer game has been adapted for a younger audience. His best asset is his ability to adapt quickly. Once in the body, these bacteria adapt to the environment and develop resistance to commonly used antibiotics.",
                        "https://images.unsplash.com/photo-1599723823444-c33ea4336db3?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhZGFwdHxlbnwwfHx8fDE3NDQ5NjEzMjB8MA&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                ),
                new Word(
                        5L,
                        "adjustment",
                        "sự điều chỉnh",
                        "əˈdʒʌst.mənt",
                        "The invitations are almost ready, I just need to make a couple of adjustments. It requires a few minor adjustments. Use the on-screen menu to make adjustments to the colour and brightness of your monitor. The bike needed a few adjustments before it was ready to leave the shop. The software allows the artist to make detailed adjustments to the original drawing.",
                        "https://images.unsplash.com/photo-1614896777839-cdec1a580b0a?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhZGp1c3RtZW50fGVufDB8fHx8MTc0NDk2MTUwMHww&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                ),
                new Word(
                        6L,
                        "advantage",
                        "sự thuận lợi, lợi thế",
                        "ədˈvɑːn.tɪdʒ",
                        "Qualifications are important but practical experience is alwaysishop an advantage. The advantage of the plan is its simplicity. She had a decided advantage over her opponent. You shouldn't be so trusting - people take advantage of you. Internet connection via broadband offers many advantages. They voted against electoral reforms that were viewed as advantaging one party. This system works well because it financially advantages those who are doing the right thing. We want to make certain that criminals are not advantaged by receiving information from official sources.",
                        "https://images.unsplash.com/photo-1621929209241-3df9f6fa1743?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3w3MjA2MzB8MHwxfHNlYXJjaHwxfHxhZHZhbnRhZ2V8ZW58MHx8fHwxNzQ0OTY5NjIzfDA&ixlib=rb-4.0.3&q=80&w=1080",
                        null
                )
        );
    }
    ImageView imageView;
    TextView meaningText;
    ImageView speaker;
    Word currentWord;
    ImageView recorder;
    TextView resultCheckerTextView;
    TextView formattedfeedback;
    Button btnFinish;
    private PronunciationChecker pronunciationChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_learn_word_by_image);
        imageView = findViewById(R.id.illustrationImage);
        meaningText = findViewById(R.id.meaningText);
        meaningText.setVisibility(View.GONE);
        boolean[] isBackVisible = {false};
        sound = new Sound(getApplicationContext());
        speaker = findViewById(R.id.speaker);
        recorder = findViewById(R.id.recorder);
        formattedfeedback = findViewById(R.id.textViewFormattedfeedback);
        btnFinish = findViewById(R.id.btnFinish);
        btnFinish.setVisibility(INVISIBLE);

        TextView swipeHintText = findViewById(R.id.swipeHintText);
        Animation blink = new AlphaAnimation(0.0f, 1.0f);
        blink.setDuration(500);
        blink.setStartOffset(20);
        blink.setRepeatMode(Animation.REVERSE);
        blink.setRepeatCount(Animation.INFINITE);
        swipeHintText.startAnimation(blink);

        imageView.setOnClickListener(v -> {
            if (!isBackVisible[0]) {
                // Lật sang mặt sau
                imageView.animate().rotationY(90).setDuration(200).withEndAction(() -> {
                    imageView.setVisibility(View.GONE);
                    meaningText.setRotationY(-90);
                    meaningText.setVisibility(VISIBLE);
                    meaningText.animate().rotationY(0).setDuration(200).start();
                }).start();
                isBackVisible[0] = true;
            } else {
                // Lật lại mặt trước
                meaningText.animate().rotationY(90).setDuration(200).withEndAction(() -> {
                    meaningText.setVisibility(View.GONE);
                    imageView.setRotationY(-90);
                    imageView.setVisibility(VISIBLE);
                    imageView.animate().rotationY(0).setDuration(200).start();
                }).start();
                isBackVisible[0] = false;
            }
        });
        meaningText.setOnClickListener(v -> {
            if (!isBackVisible[0]) {
                // Lật sang mặt sau
                imageView.animate().rotationY(90).setDuration(200).withEndAction(() -> {
                    imageView.setVisibility(View.GONE);
                    meaningText.setRotationY(-90);
                    meaningText.setVisibility(VISIBLE);
                    meaningText.animate().rotationY(0).setDuration(200).start();
                }).start();
                isBackVisible[0] = true;
            } else {
                // Lật lại mặt trước
                meaningText.animate().rotationY(90).setDuration(200).withEndAction(() -> {
                    meaningText.setVisibility(View.GONE);
                    imageView.setRotationY(-90);
                    imageView.setVisibility(VISIBLE);
                    imageView.animate().rotationY(0).setDuration(200).start();
                }).start();
                isBackVisible[0] = false;
            }
        });
        initGestureDetector();
        //init meta
        currentWord = getWordList().get(0);
        loadContent(currentWord);
        speaker.setOnClickListener(v -> {
            sound.readText(currentWord.getNoidung());
        });
        setUpRecordWord();

        btnFinish.setOnClickListener(v -> {
            Intent intentDirect = new Intent(LearnWordByImageActivity.this, ReviewWordActivity.class);
            startActivity(intentDirect);
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            recorder.performClick();
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
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
    private void loadContent(Word word){
        loadImage(word.getUrlImg());
        loadContentForText(word);
    }
    private void initGestureDetector(){
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            private static final int SWIPE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float diffX = e2.getX() - e1.getX();
                float diffY = e2.getY() - e1.getY();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            showPreviousWord(); // Vuốt phải
                        } else {
                            showNextWord(); // Vuốt trái
                        }
                        return true;
                    }
                }
                return false;
            }
        });

    }
    private void viewButtonFinish(int index){
        if(index == getWordList().size()-1){
            // hiện nút hoàn thành
            btnFinish.setVisibility(VISIBLE);
        }
        else {
            btnFinish.setVisibility(INVISIBLE);
        }
    }
    private void showNextWord() {
        if (index < getWordList().size() - 1) {
            index++;

        } else {
            index = 0;
        }
        currentWord = getWordList().get(index);
        animateTransition(true);
        viewButtonFinish(index);
    }

    private void showPreviousWord() {
        if (index > 0) {
            index--;
        } else {
            index = getWordList().size() - 1;
        }
        currentWord = getWordList().get(index);
        animateTransition(false);
        viewButtonFinish(index);
    }

    private void animateTransition(boolean isNext) {
        // Ẩn các view hiện tại (image và text)
        final ImageView currentView = findViewById(R.id.illustrationImage);

        // Áp dụng hiệu ứng mờ dần (alpha) hoặc dịch chuyển (translation)
        currentView.animate().alpha(0f).setDuration(300).withEndAction(() -> {
            currentView.setVisibility(View.GONE);

            // Sau khi ẩn, load nội dung mới và hiển thị
            loadContent(currentWord);
            setUpReadWord();
            setUpRecordWord();

            // Tạo hiệu ứng xuất hiện (mờ dần)
            currentView.setVisibility(VISIBLE);
            currentView.setAlpha(0f);

            currentView.animate().alpha(1f).setDuration(300).start();
        }).start();
    }
    private void setUpReadWord(){
        speaker.setOnClickListener(v -> {
            sound.readText(currentWord.getNoidung());
        });
    }

    private void initRecognizerChecker(String targetText){
        resultCheckerTextView = findViewById(R.id.resultCheckerTextView);
        pronunciationChecker = new PronunciationChecker(this, targetText);
        // Set result listener
        pronunciationChecker.setOnPronunciationResultListener(new PronunciationChecker.OnPronunciationResultListener() {
            @Override
            public void onPronunciationResult(String targetText, String spokenText, int accuracyPercentage, String formattedFeedback) {
                resultCheckerTextView.setText("Target text: "+targetText+"\nYou said: "
                        + spokenText + "\nAccuracy: " + accuracyPercentage+"%");
                formattedfeedback.setText(Html.fromHtml(formattedFeedback));
            }

            @Override
            public void onError(String errorMessage) {
                resultCheckerTextView.setText("Error: " + errorMessage);
            }

            @Override
            public void onListeningStarted() {
                resultCheckerTextView.setText("Listening...");
            }

            @Override
            public void onListeningFinished() {
                imageView.setEnabled(true);
            }
        });
    }

    private void setUpRecordWord(){
        recorder.setOnClickListener(v -> {
            initRecognizerChecker(currentWord.getNoidung());
            if (pronunciationChecker.hasRecordAudioPermission()) {
                recorder.setEnabled(false);
                pronunciationChecker.startListening();
                recorder.setEnabled(true);
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 100);
                Log.e("RECORD","This app doesn't have permission to record");
            }
        });
    }

    private void loadImage(String imageUrl)
    {
        // Tìm ImageView
        ImageView illustrationImage = findViewById(R.id.illustrationImage);

        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.iillustrationimage)
                .error(R.drawable.close_icon)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.e("Glide", "Load failed: " + e.getMessage());
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        Log.d("Glide", "Load successful");
                        return false;
                    }
                })
                .into(illustrationImage);

    }
    private void loadContentForText(Word word){
        TextView textViewWord = findViewById(R.id.word);
        meaningText.setText(word.getNoidung()+"\n"+word.getNghia()+"\n"+word.getPhatAm());
        textViewWord.setText(word.getNoidung());
    }

}