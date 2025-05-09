package com.example.englishapp.customUI;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space / 2;
        outRect.right = space / 2;

//        // Nếu là item đầu tiên thì padding bên trái đầy đủ
//        if (parent.getChildAdapterPosition(view) == 0) {
//            outRect.left = space;
//        }
//
//        // Nếu là item cuối cùng thì padding bên phải đầy đủ
//        if (parent.getChildAdapterPosition(view) == parent.getAdapter().getItemCount() - 1) {
//            outRect.right = space;
//        }
    }
}
