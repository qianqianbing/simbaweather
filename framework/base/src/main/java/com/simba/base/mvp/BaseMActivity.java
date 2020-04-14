package com.simba.base.mvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import com.simba.base.R;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.mvp.presenter.BasePresenter;
import com.simba.base.mvp.view.BaseView;
import com.simba.base.utils.Toasty;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public abstract class BaseMActivity<P extends BasePresenter> extends AppCompatActivity implements BaseView {
    /**
     * 进度dialog
     */
    private DialogUtil publicDialog;
    /**
     *
     */
    protected P mPresenter;
    protected Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mContext = this;
        //初始化mPresenter
        if (mPresenter == null) {
            mPresenter = initPresenter();
        }
        //绑定view
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        //初始化
        initView();
        initData();
    }

    protected P getP() {
        return mPresenter;
    }

    protected abstract void initData();

    /**
     * 初始化mPresenter
     */
    protected abstract P initPresenter();

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter.onDestroy();
            mPresenter = null;
        }

    }

    /**
     * tips
     *
     * @param msg
     */
    public void showToast(String msg) {
        Toasty.info(this, msg);
    }

    /**
     * tips
     *
     * @param msg
     */
    @Override
    public void showToast(@StringRes int msg) {
        Toasty.info(this, msg);
    }

    @Override
    public void showLoading() {
        if (publicDialog == null) {
            publicDialog = DialogUtil.buildProgress(this, R.string.base_is_loading);
        }
        if (!publicDialog.isShowing()) {
            publicDialog.show();
        }
    }

    @Override
    public void dismissLoading() {
        if (publicDialog != null && publicDialog.isShowing()) {
            publicDialog.dismiss();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


}

