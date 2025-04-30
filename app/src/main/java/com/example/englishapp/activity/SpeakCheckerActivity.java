package com.example.englishapp.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.englishapp.R;
import com.example.englishapp.util.PronunciationChecker;
import com.example.englishapp.util.Sound;

public class SpeakCheckerActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;
    private Sound sound;
    private Button buttonRecord;
    private TextView textViewResult;
    private EditText textInput;
    private PronunciationChecker pronunciationChecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_speakchecker);
        sound = new Sound(getApplicationContext());
        mappingWithId();
        setEvent();

        // Set button click listener
        buttonRecord.setOnClickListener(v -> {
            initRecognizerChecker(textInput.getText().toString());
            if (pronunciationChecker.hasRecordAudioPermission()) {
                buttonRecord.setEnabled(false);
                pronunciationChecker.startListening();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO}, 100);
                Log.e("RECORD","This app doesn't have permission to record");
            }
        });
    }
    private void initRecognizerChecker(String targetText){
        pronunciationChecker = new PronunciationChecker(this, targetText);

        // Set result listener
        pronunciationChecker.setOnPronunciationResultListener(new PronunciationChecker.OnPronunciationResultListener() {
            @Override
            public void onPronunciationResult(String targetText, String spokenText, int accuracyPercentage, String formattedFeedback) {
                textView.setText("Target text: "+targetText+"You said: " + spokenText + "\nAccuracy: " + accuracyPercentage + "%");
                textViewResult.setText(Html.fromHtml(formattedFeedback));
            }

            @Override
            public void onError(String errorMessage) {
                textViewResult.setText("Error: " + errorMessage);
            }

            @Override
            public void onListeningStarted() {
                textViewResult.setText("Listening...");
            }

            @Override
            public void onListeningFinished() {
                buttonRecord.setEnabled(true);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            buttonRecord.performClick();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pronunciationChecker != null) {
            pronunciationChecker.release();
        }
    }
    private void mappingWithId(){
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textViewHelloWorld);
        buttonRecord = findViewById(R.id.buttonStartRecord);
        textViewResult = findViewById(R.id.textViewResult);
        textInput = findViewById(R.id.textInput);
    }
    private void setEvent(){
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String content = textView.getText().toString();
                sound.readText(content);
            }
        });
    }
}