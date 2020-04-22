package com.simba.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.CallSuper;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/09
 * 描    述：activity 基类
 * 修订历史：
 * 添加永不息屏
 * 添加OkGo退出取消请求
 * 添加ButterKnife注册，解绑
 * ================================================
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    public Unbinder unbinder;

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
        mContext = this;
        initWindow();
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);//布局绑定
        ImmersionBar.with(this).init();//沉浸式布局
        initView();
        initData();
        initListener();
    }


    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        OkGo.getInstance().cancelTag(this);//取消未处理的请求
    }
}
