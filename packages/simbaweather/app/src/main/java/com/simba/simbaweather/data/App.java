package com.simba.simbaweather.data;

import com.simba.base.base.BaseApplication;
import com.simba.base.network.OkGoUtil;

/**
 * @author wzy
 * @description:
 * @date :2020/4/9 10:27
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        OkGoUtil.init(this,true);
    }
}
