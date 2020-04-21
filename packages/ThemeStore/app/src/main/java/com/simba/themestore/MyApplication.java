package com.simba.themestore;

import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.simba.base.base.BaseApplication;
import com.simba.themestore.view.CustomLoadMoreView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class MyApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        initLoadMoreView();
    }

    /**
     * 全局load more view
     */
    private void initLoadMoreView() {
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
    }
}
