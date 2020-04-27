package com.simba.themestore;

import com.chad.library.adapter.base.module.LoadMoreModuleConfig;
import com.simba.base.base.BaseApplication;
import com.simba.themestore.database.DaoMaster;
import com.simba.themestore.database.DaoSession;
import com.simba.themestore.view.CustomLoadMoreView;

import org.greenrobot.greendao.database.Database;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class MyApplication extends BaseApplication {
    private static final String DB_NAME = "theme.db";
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        initDB();
        initLoadMoreView();
    }

    private void initDB() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    /**
     * 全局load more view
     */
    private void initLoadMoreView() {
        LoadMoreModuleConfig.setDefLoadMoreView(new CustomLoadMoreView());
    }
}
