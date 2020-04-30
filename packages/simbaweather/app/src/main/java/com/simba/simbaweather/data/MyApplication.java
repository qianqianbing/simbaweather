package com.simba.simbaweather.data;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.simba.base.base.BaseApplication;
import com.simba.simbaweather.CityInfoManager;

/**
 * @author wzy
 * @description:
 * @date :2020/4/9 10:27
 */
public class MyApplication extends BaseApplication {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance("mysp"));
        CityInfoManager.getInstance();
    }

    public static MyApplication getMyApplication() {
        return mApplication;
    }
}
