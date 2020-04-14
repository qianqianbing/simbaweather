package com.simba.base.mvp.presenter;

import android.content.Context;

import androidx.annotation.StringRes;

import com.simba.base.mvp.model.BaseModel;
import com.simba.base.mvp.view.BaseView;

import java.lang.ref.WeakReference;
/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public abstract class BasePresenter<M extends BaseModel, V extends BaseView> {

    private WeakReference<V> mvpView;
    private M mvpModel;

    /**
     * 绑定View
     */
    @SuppressWarnings("unchecked")
    public void attachView(V view) {
        mvpView = new WeakReference<>(view);
        if (mvpModel == null) {
            mvpModel = createModule();
        }
    }

    /**
     * 解绑View
     */
    public void detachView() {
        if (null != mvpView) {
            mvpView.clear();
            mvpView = null;
        }
        this.mvpModel = null;
    }

    /**
     * 是否与View建立连接
     */
    protected boolean isViewAttached() {
        return null != mvpView && null != mvpView.get();
    }

    protected V getView() {
        return isViewAttached() ? mvpView.get() : null;
    }

    protected M getModule() {
        return mvpModel;
    }

    protected Context getContext() {
        return getView().getContext();
    }

    protected void showLoading() {
        getView().showLoading();
    }

    protected void dismissLoading() {
        getView().dismissLoading();
    }

    protected void showToast(@StringRes int s) {
        getView().showToast(s);
    }

    public void onDestroy() {
    }

    /**
     * 通过该方法创建Module
     */
    protected abstract M createModule();

    /**
     * 初始化方法
     */
    public abstract void start();


}