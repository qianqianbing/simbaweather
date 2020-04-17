package com.simba.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

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
    protected Context mContext;

    //在布局之前处理
    @CallSuper
    public void initWindow() {
        //禁止黑屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    //返回布局id  R.layout.main
    protected abstract int getLayoutId();

    //初始化视图对象
    protected abstract void initView();

    //视图数据初始化
    protected abstract void initData();

    //视图事件监听
    protected void initListener() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
        setContentView(getLayoutId());
        mContext = this;
        ImmersionBar.with(this).init();
        initView();
        initData();
        initListener();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkGo.getInstance().cancelTag(this);//取消未处理的请求
    }
}
