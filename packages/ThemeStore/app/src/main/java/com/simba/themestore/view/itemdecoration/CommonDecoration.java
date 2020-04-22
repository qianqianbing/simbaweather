package com.simba.themestore.view.itemdecoration;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc : 分割线 左间距 首个没有间距
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public CommonDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = space;
        }
    }
}
