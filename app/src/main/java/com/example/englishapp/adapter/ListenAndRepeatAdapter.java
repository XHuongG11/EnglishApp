package com.example.englishapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.englishapp.R;
import com.example.englishapp.activity.ListenAndRepeatActivity;
import com.example.englishapp.model.CardItem;
import com.example.englishapp.util.Sound;

import java.util.List;

public class ListenAndRepeatAdapter extends ArrayAdapter<CardItem> {
    private Sound sound;
    private ListenAndRepeatActivity activity;

    public ListenAndRepeatAdapter(Context context, List<CardItem> cardItems) {
        super(context, 0, cardItems);
        this.sound = new Sound(context);
        this.activity = (ListenAndRepeatActivity) context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_listen_and_repeat, parent, false);
        }

        CardItem cardItem = getItem(position);
        if (cardItem == null) {
            return convertView;
        }

        TextView textWord = convertView.findViewById(R.id.txtWord);
        ImageView imgEye = convertView.findViewById(R.id.imgEye);
        ImageView imgVolume = convertView.findViewById(R.id.imgVolume);

        if (textWord == null || imgEye == null || imgVolume == null) {
            return convertView;
        }

        // Hiển thị từ thực tế nếu là từ hiện tại (selectedPosition), nếu không thì hiển thị "......."
        int selectedPosition = activity.getSelectedPosition();
        if (position <= selectedPosition) {
            textWord.setText(cardItem.getWord().getNoidung());
            textWord.setTextColor(getContext().getResources().getColor(R.color.blue_700));
            imgEye.setVisibility(View.GONE);
        } else {
            textWord.setText(".......");
            textWord.setTextColor(getContext().getResources().getColor(R.color.grey_500));
            imgEye.setVisibility(View.VISIBLE);
        }

//        imgEye.setVisibility(cardItem.isShowEye() ? View.VISIBLE : View.GONE);

        imgVolume.setOnClickListener(v -> {
            if (cardItem.getWord() != null) {
                sound.readText(cardItem.getWord().getNoidung());
            }
        });

        return convertView;
    }
}