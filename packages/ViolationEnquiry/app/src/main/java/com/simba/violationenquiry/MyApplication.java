package com.simba.violationenquiry;

import android.content.Context;

import com.simba.base.base.BaseApplication;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class MyApplication extends BaseApplication {
    public static Context sContext;
    public static final boolean isDebug =true;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }
}
