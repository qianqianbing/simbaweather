package com.simba.themestore.view.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc : 分割线 左间距 首个没有间距
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDrawable;
    private int space;

    public CommonDecoration(Context context, int space) {
        this.mContext = context;
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = space;
        }
    }
}
