package com.simba.base.utils;

import android.util.Log;


/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：打印日志类
 * 修订历史：
 * ================================================
 */
public class LogUtil {
    private static String TAG = "SimBa";
    // 开关
    private static boolean debug = true;

    private LogUtil() {
    }

    public static void setTAG(String TAG) {
        LogUtil.TAG = TAG;
    }

    public static void setDebug(boolean debug) {
        LogUtil.debug = debug;
    }

    public static void v(String msg) {
        v(TAG, msg);
    }

    public static void v(String tag, String msg) {
        if (debug)
            Log.v(tag, msg);
    }

    public static void d(String msg) {
        d(TAG, msg);
    }

    public static void d(String tag, String msg) {
        if (debug)
            Log.d(tag, msg);
    }

    public static void i(String msg) {
        i(TAG, msg);
    }

    public static void i(String tag, String msg) {
        if (debug)
            Log.i(tag, msg);
    }

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        if (debug)
            Log.w(tag, msg);
    }

    public static void e(String msg) {
        e(TAG, msg);
    }

    public static void e(String tag, String msg) {
        if (debug)
            Log.e(tag, msg);
    }

    public static void e(Throwable msg) {
        e(TAG, msg);
    }

    public static void e(String tag, Throwable msg) {
        if (debug)
            Log.e(tag, "", msg);
    }
}
