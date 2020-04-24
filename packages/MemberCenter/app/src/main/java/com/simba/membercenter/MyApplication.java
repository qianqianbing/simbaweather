package com.simba.membercenter;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.greendao.gen.DaoMaster;
import com.greendao.gen.DaoSession;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.base.base.BaseApplication;
import com.simba.base.network.OkGoUtil;
import com.simba.message.MessageManager;
import com.simba.message.bean.MemeberInfoData;
import com.simba.message.bean.MemeberMsgData;
import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.callbacks.OnInitListener;
import com.simba.service.data.DataWrapper;

public class MyApplication extends BaseApplication {

    private static String TAG = "MyApplication";
    private static  MyApplication mApplication;

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    MessageManager mManager;
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("timeTAG" , "MyApplication oncreate");
        mApplication = this;
        OkGoUtil.init(this, true);
        setDatabase();
        DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext());


        mManager = MessageManager.getInstance(this);
        mManager.setOnInitListener(mListener);
        mManager.bindService();
    }

    public static MyApplication getMyApplication(){
        return mApplication;
    }

    /**
     * 设置greenDAO
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "membercenter-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }



    private OnInitListener mListener = new OnInitListener() {
        @Override
        public void onServiceConnected() {
            Log.i(TAG, "Msg Service Connected");
            mManager.registerCallback(mCallback);

            MemeberMsgData data = mManager.getMemberMsg();
            if(data != null){
                // TODO member msg
                data.getMessage();
            }
        }

        @Override
        public void onServiceDisconnected() {
            Log.i(TAG, "TBox Service Disconnected");
        }
    };

    private IServiceDataCallback mCallback = new IServiceDataCallback.Stub() {
        @Override
        public void onChange(DataWrapper dataWrapper) {
            Log.i(TAG, dataWrapper.toString());
            switch (dataWrapper.getDataCode()) {
                case MemeberMsgData.CODE:
                    {
                    MemeberMsgData data = (MemeberMsgData) dataWrapper.getData();
                    // TODO member msg
                    data.getMessage();
                }
                break;

                case MemeberInfoData.CODE: {
                    MemeberInfoData data = (MemeberInfoData) dataWrapper.getData();
                    // TODO member msg
                    data.getToken();
                }   break;
            }
        }

        @Override
        public boolean accept(int i)  {
            return i == MemeberMsgData.CODE
                    || i == MemeberInfoData.CODE;
//            return true;
        }
    };
}
