package com.simba.violationenquiry;

import android.app.Application;
import android.content.Context;

import com.simba.base.network.OkGoUtil;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class MyApplication extends Application {
    public static Context sContext;
    public static final boolean isDebug = false;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        initOKGo();
    }

    private void initOKGo() {
        OkGoUtil.init(this, isDebug);
    }
}
