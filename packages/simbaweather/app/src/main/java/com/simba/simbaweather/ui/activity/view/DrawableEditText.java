package com.simba.simbaweather.ui.activity.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/22 19:00
 * 描    述：添加drawable监听的EditText
 * 修订历史：
 * ================================================
 */
public class DrawableEditText extends AppCompatEditText {

    private OnDrawableLeftListener mLeftListener;
    private OnDrawableRightListener mRightListener;
    private OnDrawableListener drawableListener;
    final int DRAWABLE_LEFT = 0;
    final int DRAWABLE_TOP = 1;
    final int DRAWABLE_RIGHT = 2;
    final int DRAWABLE_BOTTOM = 3;

    // 构造方法
    public DrawableEditText(Context context) {
        super(context);
    }

    public DrawableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnDrawableLeftListener(OnDrawableLeftListener listener) {
        this.mLeftListener = listener;
    }

    public void setOnDrawableRightListener(OnDrawableRightListener listener) {
        this.mRightListener = listener;
    }

    public void setOnDrawableClickListener(OnDrawableListener listener) {
        this.drawableListener = listener;
    }

    public interface OnDrawableListener {
        void onDrawableLeftClick(View view);

        void onDrawableRightClick(View view);
    }

    public interface OnDrawableLeftListener {
        void onDrawableLeftClick(View view);
    }

    public interface OnDrawableRightListener {
        void onDrawableRightClick(View view);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mRightListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        mRightListener.onDrawableRightClick(this);
                        return true;
                    }
                }
                if (drawableListener != null) {
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT];
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {
                        drawableListener.onDrawableRightClick(this);
                        return true;
                    }
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        drawableListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                if (mLeftListener != null) {
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT];
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {
                        mLeftListener.onDrawableLeftClick(this);
                        return true;
                    }
                }
                break;
        }

        return super.onTouchEvent(event);
    }
}
