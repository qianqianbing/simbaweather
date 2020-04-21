package com.simba.base.DeviceAccountManager;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

/*账号登陆管理类，实现在MemberCenter中使用content provider实现，各个app使用AccountManager调用便可
    AccountManager.getInstance().getLoginedAccount()
 */
public class DeviceAccountManager {

    private static String TAG = "DeviceAccountManager";
    private static Context mContext ;
    private AccountStateHander accountStateHander;

    //注册账号切换的回调函数
    public static void registerAccountManager(DeviceAccountManager accountManager) {
        DeviceAccountManager.accountManager = accountManager;
    }

    // 获取设备deviceid
    public int getDeviceId(){
        return 1;
    }
    //设备是否激活
    public  boolean getDeviceActivation( ){

        Cursor cursor = mContext.getContentResolver().query(DEVICE_STATE_URI,null,null,null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();

            int activationState = cursor.getInt(cursor.getColumnIndex(ACTIVATION_STATE));
            cursor.close();
            return activationState > 0;
        }
        return false;
    }

    //获取账号登陆状态
    public  boolean getLoginState( ){
        Cursor cursor = mContext.getContentResolver().query(DEVICE_STATE_URI,null,null,null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            int loginState = cursor.getInt(cursor.getColumnIndex(LODIN_STATE));
            cursor.close();
            return loginState > 0;
        }
        return true;
    }

    //获取已登陆的账号
    public String getLoginedAccount( ){
        Cursor cursor = mContext.getContentResolver().query(DEVICE_STATE_URI,null,null,null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();

            String userName = cursor.getString(cursor.getColumnIndex(USER_NAME));

            cursor.close();
            return userName;
        }
        return "";
    }

    //获取已登陆账号的token
    public  String getAccountToken( ){
        Cursor cursor = mContext.getContentResolver().query(DEVICE_STATE_URI,null,null,null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            int deviceId = cursor.getInt(cursor.getColumnIndex(DEVICE_ID));
            int activationState = cursor.getInt(cursor.getColumnIndex(ACTIVATION_STATE));
            int loginState = cursor.getInt(cursor.getColumnIndex(LODIN_STATE));
            String userName = cursor.getString(cursor.getColumnIndex(USER_NAME));
            int realNameState = cursor.getInt(cursor.getColumnIndex(REAL_NAME_STATE));

            Log.e(TAG, "deviceId is " + deviceId + "\n activationState is " + activationState + "\n loginState is " + loginState + " \n userName is " + userName + "\n realNameState is " + realNameState );
            cursor.close();
        }
        return "1234566778";
    }

    public static boolean isRealNameAuthentication( ){
        Cursor cursor = mContext.getContentResolver().query(DEVICE_STATE_URI,null,null,null,null,null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToLast();
            int realNameState = cursor.getInt(cursor.getColumnIndex(REAL_NAME_STATE));
            cursor.close();
            return realNameState > 0;
        }
        return false;
    }


    public final static String DEVICE_AUTHORITY = "com.simba.membercenter.provider";
    public final static String DEVICE_STATE_Str = "device_state";
    public final static int DEVICE_STATE_TYPE = 0;
    public static final Uri DEVICE_STATE_URI = Uri.parse("content://" + DEVICE_AUTHORITY + "/" + DEVICE_STATE_Str);


    //deivce id
    public final static String DEVICE_ID = "DEVICE_ID";
    //激活状态
    public final static String ACTIVATION_STATE = "ACTIVATION_STATE";
    //登陆状态
    public final static String LODIN_STATE = "LODIN_STATE";
    //登陆的username
    public final static String USER_NAME = "USER_NAME";
    //实名认证状态
    public final static String REAL_NAME_STATE = "REAL_NAME_STATE";

    static private DeviceAccountManager accountManager;

    public static DeviceAccountManager getInstance (Context context){
        if(accountManager == null ){
            accountManager = new DeviceAccountManager( context);
        }
        return accountManager;
    }

    private DeviceAccountManager(Context context) {
        mContext = context;
        mContext.getContentResolver().registerContentObserver(DEVICE_STATE_URI, true,new DeviceStateContentObserver(new Handler()));
    }

    private class DeviceStateContentObserver extends ContentObserver {

       public DeviceStateContentObserver(Handler handler){
        super(handler);
        }

        @Override
        public void onChange(boolean selfChange){
            Log.e(TAG, "DEVICE_STATE_URI change " + selfChange);
            if(accountStateHander != null){
                if(getLoginState()){
                    String userName = getLoginedAccount();
                    accountStateHander.onAccountLogin(userName);
                }else {
                    accountStateHander.onAccountLogout();
                }
            }
       }
    }

    public interface AccountStateHander {
        void onAccountLogout();
        void onAccountLogin(String  userName);
    }
}
