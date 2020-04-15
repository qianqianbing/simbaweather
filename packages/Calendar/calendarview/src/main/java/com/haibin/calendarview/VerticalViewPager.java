package com.haibin.calendarview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * 支持纵向滑动的viewPage
 * 直接交换触摸xy轴
 * <p>
 * 还有一种方案，视图旋转
 * onInstantiateItem()的PagerAdapter，创建视图并将其旋转-90：
 * view.setrotation(-90f)
 * 如果你正在使用FragmentPagerAdapter，则：
 * objFragment.getView().setRoration(-90)
 * 旋转ViewPager90度视角：
 * objViewPager.setRotation(90)
 */
public class VerticalViewPager extends ViewPager {
    public boolean isVertical = false;

    public VerticalViewPager(@NonNull Context context) {
        super(context);
        init();
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        if (isVertical) {
            // 最重要的设置，将viewpager翻转
            setPageTransformer(true, new PageTransformer() {
                @Override
                public void transformPage(@NonNull View view, float position) {
                    /**
                     * 0 当前界面
                     * -1 前一页
                     * 1 后一页
                     */
                    if (position >= -1 && position <= 1) {
                        view.setTranslationX(view.getWidth() * -position);
                        float yPosition = position * view.getHeight();
                        view.setTranslationY(yPosition);
                    }
                }
            });

            // 设置去掉滑到最左或最右时的滑动边界阴影
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }

    /**
     * 交换触摸事件的X和Y坐标
     */
    private MotionEvent swapXY(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();

        float newX = (ev.getY() / height) * width;
        float newY = (ev.getX() / width) * height;

        ev.setLocation(newX, newY);
        return ev;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (isVertical) {
            boolean intercepted = super.onInterceptTouchEvent(swapXY(ev));
            swapXY(ev);
            return intercepted; //为所有子视图返回触摸的原始坐标
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isVertical) {
            return super.onTouchEvent(swapXY(ev));
        } else {
            return super.onTouchEvent(ev);
        }
    }

}
