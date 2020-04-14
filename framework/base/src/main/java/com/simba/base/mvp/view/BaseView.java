package com.simba.base.mvp.view;

import android.content.Context;
/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public interface BaseView {

    /**
     * 显示加载框
     */
    void showLoading();

    /**
     * 隐藏加载框
     */
    void dismissLoading();


    void showToast(int msg);

    /**
     * 上下文
     *
     * @return context
     */
    Context getContext();
}
