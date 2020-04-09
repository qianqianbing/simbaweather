package com.simba.violationenquiry.ui.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int space;  //位移间距

    public SpaceItemDecoration(Context mContext, int space) {
        this.mContext = mContext;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.top = space;
    }

}