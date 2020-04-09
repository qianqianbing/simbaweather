package com.simba.violationenquiry.base;

import com.simba.base.base.BaseActivity;

import io.reactivex.disposables.Disposable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/9
 * @Desc :
 */
public abstract class MyBaseActivity extends BaseActivity {
    protected Disposable mDisposable;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
        //activity管理
        //   ActivityCollector.removeActivity(this);
    }
}
