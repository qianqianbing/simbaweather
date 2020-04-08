package com.simba.base.log;

import android.util.Log;


/**
 * @类描述:
 * @创建人: 谢广胜
 * @创建时间: 2020/4/7 13:36
 * @修改人:
 * @修改备注:
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
        if (debug)
            Log.v(TAG, msg);
    }

    public static void d(String msg) {
        if (debug)
            Log.d(TAG, msg);
    }

    public static void i(String msg) {
        if (debug)
            Log.i(TAG, msg);
    }

    public static void w(String msg) {
        if (debug)
            Log.w(TAG, msg);
    }

    public static void e(String msg) {
        if (debug)
            Log.e(TAG, msg);
    }

    public static void e(Throwable msg) {
        if (debug)
            Log.e(TAG, "",msg);
    }
}
