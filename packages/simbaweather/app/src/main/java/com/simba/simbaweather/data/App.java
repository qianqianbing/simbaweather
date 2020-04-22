package com.simba.simbaweather.data;

import com.simba.base.base.BaseApplication;
import com.simba.base.network.OkGoUtil;
import com.simba.base.utils.SpStaticUtils;
import com.simba.base.utils.SpUtils;

/**
 * @author wzy
 * @description:
 * @date :2020/4/9 10:27
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SpStaticUtils.setDefaultSpUtils(SpUtils.getInstance(this, "mysp"));
    }
}
