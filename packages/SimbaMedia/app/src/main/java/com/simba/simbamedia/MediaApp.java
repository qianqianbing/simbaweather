package com.simba.simbamedia;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class MediaApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
