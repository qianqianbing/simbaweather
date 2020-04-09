package com.simba.base.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/09
 * 描    述：activity 基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity {

    //在布局之前处理
    @CallSuper
    public void initWindow() {
    }

    //返回布局id  R.layout.main
    public abstract int getLayoutId();

    //初始化视图对象
    public abstract void initView();

    //视图数据初始化
    public abstract void initData();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutId());
        ImmersionBar.with(this).init();
        initView();
        initData();
    }
}
