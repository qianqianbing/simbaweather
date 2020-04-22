package com.simba.base.base;

import android.app.Application;
import android.content.Context;

import androidx.annotation.CallSuper;

import com.simba.base.network.OkGoUtil;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/9 16:42
 * 描    述：Application 基类,网络库，日志库，数据库初始化等等
 * 修订历史：
 * ================================================
 */
public class BaseApplication extends Application {

    public static Context sContext;

    @Override
    @CallSuper
    public void onCreate() {
        super.onCreate();
        sContext = this;
        OkGoUtil.init(this, true);
    }
}
