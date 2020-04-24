package com.simba.themestore.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.StringRes;

import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.Toasty;

import io.reactivex.disposables.Disposable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/9
 * @Desc :
 */
public abstract class MyBaseActivity extends BaseActivity {
    protected Disposable mDisposable;
    //获取TAG的activity名称
    protected final String TAG = getClass().getSimpleName();

    /**
     * 进度dialog
     */
    private DialogUtil publicDialog;

    /**
     * tips
     *
     * @param msg
     */
    protected void showToast(String msg) {
        Toasty.info(this, msg).show();
    }

    /**
     * tips
     *
     * @param msg
     */
    protected void showToast(@StringRes int msg) {
        Toasty.info(this, msg).show();
    }

    /**
     * 隐藏软键盘
     */
    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    public void showSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null && null != imm) {
            imm.showSoftInputFromInputMethod(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * @param cls
     * @param bundle
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * @param cls
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 显示dialog
     */
    protected void showProgressDialog() {
        if (publicDialog == null) {
            publicDialog = DialogUtil.buildProgress(this, com.simba.base.R.string.base_is_loading);
        }
        publicDialog.show();
    }

    protected void dismissProgressDialog() {
        if (publicDialog != null && publicDialog.isShowing()) {
            publicDialog.dismiss();
        }
    }

    /**
     * 保证同一按钮在1秒内只会响应一次点击事件
     */
    public abstract class OnSingleClickListener implements View.OnClickListener {
        //两次点击按钮之间的间隔，目前为1000ms
        private static final int MIN_CLICK_DELAY_TIME = 1000;
        private long lastClickTime;

        public abstract void onSingleClick(View view);

        @Override
        public void onClick(View view) {
            long curClickTime = System.currentTimeMillis();
            if ((curClickTime - lastClickTime) >= MIN_CLICK_DELAY_TIME) {
                lastClickTime = curClickTime;
                onSingleClick(view);
            }
        }
    }

    /**
     * 同一按钮在短时间内可重复响应点击事件
     */
    public abstract class OnMultiClickListener implements View.OnClickListener {
        public abstract void onMultiClick(View view);

        @Override
        public void onClick(View v) {
            onMultiClick(v);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

}
