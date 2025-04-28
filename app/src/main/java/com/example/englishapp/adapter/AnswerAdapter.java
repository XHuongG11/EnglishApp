package com.example.englishapp.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.englishapp.R;
import com.example.englishapp.activity.ListenAndMatchActivity;
import com.example.englishapp.databinding.ViewholderAnswerMatchBinding;
import com.example.englishapp.fragment.CorrectAnswerFragment;
import com.example.englishapp.fragment.ErrorAnswerFragment;
import com.example.englishapp.model.Word;
import com.google.android.flexbox.FlexboxLayout;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    private Context context;
    private List<Word> itemList;
    FlexboxLayout layoutPhraseContainer;
    private ListenAndMatchActivity activity;
    public AnswerAdapter(ListenAndMatchActivity activity, Context context, List<Word> itemList, FlexboxLayout layoutPhraseContainer) {
        this.activity = activity;
        this.context = context;
        this.itemList = itemList;
        this.layoutPhraseContainer = layoutPhraseContainer;
    }
    public AnswerAdapter(List<Word> itemList) {
        this.itemList = itemList;
    }
    // Dùng để tạo ViewHolder cho mỗi item trong RecyclerView.
    // Được gọi khi RecyclerView cần tạo một ViewHolder mới.
    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Gắn ViewBinding vào parent
        ViewholderAnswerMatchBinding binding = ViewholderAnswerMatchBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    // Dùng để gán dữ liệu vào ViewHolder.
    // Được gọi khi RecyclerView cần hiển thị dữ liệu tại vị trí cụ thể.
    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        Word item = itemList.get(position);
        holder.binding.btnWord.setText(item.getNoidung());
        holder.binding.btnWord.setOnClickListener(v -> {
            animateButtonUp(holder.binding.btnWord);
            addWordToPhraseFrom(holder.binding.btnWord, holder);
            activity.updateCheckButtonState();
//            updateCheckButtonState();
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    // đại diện cho một phần tử (item) trong danh sách của RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderAnswerMatchBinding binding;
        public ViewHolder(ViewholderAnswerMatchBinding binding) {
            super(binding.getRoot()); //  sử dụng View gốc của ViewholderCategoryBinding làm item view
            this.binding = binding;
        }
    }
    private void animateButtonUp(View button) {
        int screenHeight = context.getResources().getDisplayMetrics().heightPixels;
        float translationY = -screenHeight / 4f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", translationY);
        animator.setDuration(300);
        animator.start();
    }

    private void animateButtonDown(View button) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(button, "translationY", 0f);
        animator.setDuration(300);
        animator.start();
    }
    private void addWordToPhraseFrom(Button originalButton, AnswerAdapter.ViewHolder holder) {
        FlexboxLayout.LayoutParams params = new FlexboxLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(20, 0, 20, 0);

        Button clone = new Button(context);
        clone.setLayoutParams(params);
        clone.setText(originalButton.getText());
        clone.setAllCaps(false);
        clone.setTextSize(originalButton.getTextSize() / context.getResources().getDisplayMetrics().scaledDensity);
        clone.setTextColor(originalButton.getTextColors());
        clone.setBackground(originalButton.getBackground());
        clone.setTypeface(originalButton.getTypeface());
        clone.setPadding(
                originalButton.getPaddingLeft(),
                originalButton.getPaddingTop(),
                originalButton.getPaddingRight(),
                originalButton.getPaddingBottom()
        );

        clone.setTag(originalButton);

        clone.setOnClickListener(v -> {
            Button original = (Button) v.getTag();
            removeWordFromPhrase(v);
            animateButtonDown(original);
            activity.updateCheckButtonState();

        });

        layoutPhraseContainer.addView(clone);

    }
    private void removeWordFromPhrase(View view) {
        layoutPhraseContainer.removeView(view);
    }

}
