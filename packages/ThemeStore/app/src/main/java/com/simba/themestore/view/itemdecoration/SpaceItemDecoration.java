package com.simba.themestore.view.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :上下间距
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int space;  //位移间距


    public SpaceItemDecoration(int space) {

        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
        LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
        if (layoutManager.getItemCount() % 2 == 0) {
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 2 || parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = space;
            }
        } else {
            if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                outRect.right = space;
            }
        }


    }

}