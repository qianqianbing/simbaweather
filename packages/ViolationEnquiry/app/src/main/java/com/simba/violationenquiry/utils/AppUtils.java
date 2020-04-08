package com.simba.violationenquiry.utils;

import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class AppUtils {
    /**
     * loading加载
     * @param imageView
     */
    public static void startLoadingAnimation(ImageView imageView) {
        RotateAnimation animation;
        int magnify = 10000;
        int toDegrees = 360;
        int duration = 2000;
        toDegrees *= magnify;
        duration *= magnify;
        animation = new RotateAnimation(0, toDegrees,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(duration);
        LinearInterpolator lir = new LinearInterpolator();
        animation.setInterpolator(lir);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        imageView.startAnimation(animation);
    }

    public static void endLoadingAnimation(ImageView imageView) {
        imageView.clearAnimation();
    }
}
