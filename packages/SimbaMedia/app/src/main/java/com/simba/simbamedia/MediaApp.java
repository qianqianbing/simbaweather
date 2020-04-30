package com.simba.simbamedia;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.kuwo.application.App;

public class MediaApp extends App {


    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.openDebug();
        ARouter.init(this);
    }
}
