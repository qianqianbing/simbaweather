package com.simba.musicmudle.test;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.kuwo.application.App;


public class MusicApplication extends Application {
    App kwApp;

    public MusicApplication(){
        kwApp =  new App();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        kwApp.onCreate();
    }
}
