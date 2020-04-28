package com.simba.demomembercenter;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.simba.base.base.BaseApplication;


public class MyApplication extends BaseApplication {

    private static MyApplication mApplication;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance( UserInfoManager.getInstance().SharePreName));
        UserInfoManager.getInstance();
    }

    public static MyApplication getMyApplication(){
        return mApplication;
    }
}
