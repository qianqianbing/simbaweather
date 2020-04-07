package com.simba.violationenquiry.ui.itemdecoration;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc : 分割线
 */
public class CommonDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    private Drawable mDrawable;

    public CommonDecoration(Context context, int drawableId) {
        this.mContext = context;
        this.mDrawable = ContextCompat.getDrawable(this.mContext, drawableId);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.left = mDrawable.getIntrinsicWidth();
        }
    }
}
