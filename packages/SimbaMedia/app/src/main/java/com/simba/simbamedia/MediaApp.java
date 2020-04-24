package com.simba.simbamedia;

import android.app.Application;

import cn.kuwo.application.App;

public class MediaApp extends Application {

    App app;

    public MediaApp(){
        app = new App(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        app.onCreate();
    }


}
