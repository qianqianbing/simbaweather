package com.simba.base.utils;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.simba.base.base.BaseApplication;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :资源工具类
 */
public class ResourceUtils {

    public static String getString(Integer resID) {

        String string = BaseApplication.sContext.getString(resID);
        return string;
    }

    public static int getColor(Integer resID) {

        int color = BaseApplication.sContext.getResources().getColor(resID);
        return color;
    }

    public static View getLayoutView(Integer resID) {

        View view = LayoutInflater.from(BaseApplication.sContext).inflate(resID, null);
        return view;
    }

    public static View getViewFollowParent(Integer resID, ViewGroup parent) {

        View view = LayoutInflater.from(BaseApplication.sContext).inflate(resID, parent, false);
        return view;
    }

    public static int getDimen(Integer resID) {

        int dimen = BaseApplication.sContext.getResources().getDimensionPixelOffset(resID);

        return dimen;
    }

    public static Drawable getDrawable(Integer resID) {

        Drawable drawable = BaseApplication.sContext.getResources().getDrawable(resID);

        return drawable;
    }

    /**
     * 获取activity的根view
     */
    public static View getActivityRoot(Activity activity) {
        return ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
    }
}
