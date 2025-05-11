package com.example.englishapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.englishapp.R;
import com.example.englishapp.fragment.WordDetailFragment;

public class DictionaryActivity extends AppCompatActivity {

    private EditText edtSearch;
    private ImageView imgSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dictionary);

        edtSearch = findViewById(R.id.edtSearch);
        imgSearch = findViewById(R.id.imgSearch);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String word = edtSearch.getText().toString().trim().toLowerCase();
                if (!word.isEmpty()) {
                    openFragment(WordDetailFragment.newInstance(word));
                } else {
                    Toast.makeText(DictionaryActivity.this, "Vui lòng nhập từ!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }
}