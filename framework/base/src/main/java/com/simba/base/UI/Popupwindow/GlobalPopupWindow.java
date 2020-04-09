package com.simba.base.UI.Popupwindow;


import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

/**
 * 弹窗辅助类,可以全局弹框，不需要在activity中弹框,继承该类并实现下面三个函数便可以
 *
 * @ClassName GlobalPopupWindow
 *     @Override
 *     public int getWidth() {
 *         return 300;
 *     }
 *
 *     @Override
 *     public int getHeight() {
 *         return 300;
 *     }
 *
 *     @Override
 *     public View setUpView(Context context) {
 *         Log.i(TAG, "setUp view");
 *         View view = LayoutInflater.from(context).inflate(R.layout.dialog_deviceactivation, null);
 *         return view;
 *     }
 */
public abstract  class GlobalPopupWindow {
    private static final String LOG_TAG = "GlobalPopupWindow";
    private  View mView = null;
    private  WindowManager mWindowManager = null;
    private  Context mContext = null;
    public  Boolean isShown = false;

    public abstract int getWidth();
    public abstract int getHeight();
    public abstract View setUpView(Context context);

    /**
     * 显示弹出框
     *
     * @param context
     * @param
     */
    public void showPopupWindow(final Context context) {
        if (isShown) {
            Log.i(LOG_TAG, "return cause already shown");
            return;
        }
        isShown = true;
        Log.i(LOG_TAG, "showPopupWindow");
        // 获取应用的Context
        mContext = context.getApplicationContext();
        // 获取WindowManager
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mView = setUpView(context);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
        // | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        //params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.width = getWidth();
        params.height = getHeight();
        params.gravity = Gravity.CENTER;
        mWindowManager.addView(mView, params);
        Log.i(LOG_TAG, "add view");
    }
    /**
     * 隐藏弹出框
     */
    public  void hidePopupWindow() {
        Log.i(LOG_TAG, "hide " + isShown + ", " + mView);
        if (isShown && null != mView) {
            Log.i(LOG_TAG, "hidePopupWindow");
            mWindowManager.removeView(mView);
            isShown = false;
        }
    }
}