package com.simba.themestore.banner;

import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class NonPageTransformer implements ViewPager2.PageTransformer {
    @Override
    public void transformPage(View page, float position) {
        page.setScaleX(0.999f);//hack
    }

    public static final ViewPager2.PageTransformer INSTANCE = new NonPageTransformer();
}
