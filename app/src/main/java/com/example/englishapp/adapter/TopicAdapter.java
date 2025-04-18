package com.example.englishapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishapp.R;
import com.example.englishapp.model.Topic;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {
    private List<Topic> danhSachChuDe;
    private OnTopicClickListener nguoiNghe;

    public interface OnTopicClickListener {
        void onTopicClick(Topic chuDe);
    }

    public TopicAdapter(List<Topic> danhSachChuDe, OnTopicClickListener nguoiNghe) {
        this.danhSachChuDe = danhSachChuDe;
        this.nguoiNghe = nguoiNghe;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic chuDe = danhSachChuDe.get(position);
        holder.bind(chuDe);
    }

    @Override
    public int getItemCount() {
        return danhSachChuDe.size();
    }

    public class TopicViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout layoutTopicCard;
        private TextView tvNumberTopic;
        private TextView tvName;
        private ImageView ivStar;
        private Button btnStart;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            layoutTopicCard = itemView.findViewById(R.id.layout_topic_card);
            tvNumberTopic = itemView.findViewById(R.id.tv_number_topic);
            tvName = itemView.findViewById(R.id.tv_name);
        }

        public void bind(Topic chuDe) {
            layoutTopicCard.setBackgroundResource(chuDe.getBackgroundResource());
            tvNumberTopic.setText(chuDe.getNumberTopic());
            tvName.setText(chuDe.getName());
           // btnStart.setOnClickListener(v -> nguoiNghe.onTopicClick(chuDe));
        }
    }
}