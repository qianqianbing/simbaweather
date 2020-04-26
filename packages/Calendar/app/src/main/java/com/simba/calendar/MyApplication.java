package com.simba.calendar;

import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.SPUtils;
import com.simba.base.base.BaseApplication;

/**
 * @类描述:
 * @创建人: 谢广胜
 * @创建时间: 2020/4/7 16:08
 * @修改人:
 * @修改备注:
 */
public class MyApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SPStaticUtils.setDefaultSPUtils(SPUtils.getInstance("setting"));
    }
}
