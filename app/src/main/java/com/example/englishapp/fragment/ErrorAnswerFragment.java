package com.example.englishapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.englishapp.R;
import com.example.englishapp.model.Question;
import com.example.englishapp.util.Sound;

public class ErrorAnswerFragment extends Fragment {
    private Question sentence;
    private Sound sound;
    public ErrorAnswerFragment(Question sentence) {
        this.sentence = sentence;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.error_answer_feedback, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        sound = new Sound(view.getContext());
        TextView tvSentence = view.findViewById(R.id.tvSentence);
        ImageButton btnFlag = view.findViewById(R.id.btnFlag);
        ImageButton btnSound = view.findViewById(R.id.btnSound);
        // Thiết lập nội dung
        tvSentence.setText(sentence.getContent());

        btnFlag.setOnClickListener(v ->
                Toast.makeText(getContext(), "Báo lỗi", Toast.LENGTH_SHORT).show()
        );
        btnSound.setOnClickListener(v ->
            sound.readText(sentence.getContent())
        );
    }
}
