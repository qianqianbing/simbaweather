package com.simba.simbaweather.data;

import com.simba.base.base.BaseApplication;
import com.simba.base.utils.SpStaticUtils;
import com.simba.base.utils.SpUtils;
import com.simba.simbaweather.CityManager;

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
        SpStaticUtils.setDefaultSpUtils(SpUtils.getInstance(this, "mysp"));
        CityManager.getInstance();
    }


    public static MyApplication getMyApplication(){
        return mApplication;
    }
}
