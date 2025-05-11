package com.example.englishapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.englishapp.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class WordDetailFragment extends Fragment {

    private static final String ARG_WORD = "word";
    private String word;
    private TextView tvWord, tvPronounce, tvMeanings, tvDefinitions, tvType, vidu, tvExamples;
    private Button btnAddToNotebook;
    private FirebaseFirestore db;
    private Map<String, Object> currentWordData;

    public WordDetailFragment() {
        // Required empty public constructor
    }

    public static WordDetailFragment newInstance(String word) {
        WordDetailFragment fragment = new WordDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            word = getArguments().getString(ARG_WORD);
        }
        db = FirebaseFirestore.getInstance();
        currentWordData = new HashMap<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_detail, container, false);

        // Initialize TextViews and Button
        tvWord = view.findViewById(R.id.tvWord);
        tvPronounce = view.findViewById(R.id.tvPronounce);
        tvMeanings = view.findViewById(R.id.tvMeanings);
        tvDefinitions = view.findViewById(R.id.tvDefinitions);
        tvType = view.findViewById(R.id.tvType);
        vidu = view.findViewById(R.id.vidu);
        tvExamples = view.findViewById(R.id.tvExamples);
        btnAddToNotebook = view.findViewById(R.id.btnAddToNotebook);

        // Fetch data from Firebase
        fetchWordData();

        // Set up the "Add to Notebook" button click listener
        btnAddToNotebook.setOnClickListener(v -> addToNotebook());

        return view;
    }

    private void fetchWordData() {
        db.collection("vocabulary")
                .whereEqualTo("Word", word)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            // Map data to TextViews
                            String wordText = document.getString("Word");
                            String pronounce = document.getString("IPA");
                            String meanings = document.getString("Meanings");
                            String definitions = document.getString("Definitions");
                            String examples = document.getString("Examples");
                            String type = document.getString("Type");

                            // Store data for adding to notebook
                            currentWordData.put("Word", wordText);
                            currentWordData.put("IPA", pronounce);
                            currentWordData.put("Meanings", meanings);
                            currentWordData.put("Definitions", definitions);
                            currentWordData.put("Examples", examples);
                            currentWordData.put("Type", type);

                            // Bind data to TextViews
                            tvWord.setText(wordText != null ? wordText : "No Word");
                            tvPronounce.setText(pronounce != null ? "/" + pronounce + "/" : "");
                            tvMeanings.setText(meanings != null ? meanings : "No meanings available");
                            tvDefinitions.setText(definitions != null ? definitions : "No definition available");
                            tvType.setText(type != null ? type : "No type available");
                            vidu.setText("Example sentences:");
                            tvExamples.setText(examples != null ? examples : "");
                        }
                        if (task.getResult().isEmpty()) {
                            Toast.makeText(getContext(), "Từ không tìm thấy!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addToNotebook() {
        if (currentWordData.isEmpty()) {
            Toast.makeText(getContext(), "Không có dữ liệu để thêm vào sổ tay!", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("notebook")
                .add(currentWordData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(getContext(), "Đã thêm '" + currentWordData.get("Word") + "' vào sổ tay!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Lỗi khi thêm vào sổ tay: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}