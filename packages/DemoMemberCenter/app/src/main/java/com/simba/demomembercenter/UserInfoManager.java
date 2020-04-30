package com.simba.demomembercenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Random;


public class UserInfoManager {
    private static String TAG = "AccountInfoManager";
    SharedPreferences sp;
    private String deviceId ;

    public static String SharePreName = "UserInfoSharedPre";

    private static String SP_UserInfoState = "userInfoState";
    private static String SP_DeviceId = "deviceId";

    private static UserInfoManager userInfoManager;

    public static UserInfoManager getInstance(){
        if (userInfoManager == null){
            userInfoManager = new UserInfoManager();
        }
        return userInfoManager;
    }

    public UserInfoManager() {
        sp = MyApplication.getMyApplication().getSharedPreferences(SharePreName, Context.MODE_PRIVATE);
    }

    public  void setUserInfoData(UserInfoBean userInfoBean) {
        if(userInfoBean == null){
            sp.edit().remove(SP_UserInfoState).commit();
        }else {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(userInfoBean);
            sp.edit().putString(SP_UserInfoState, strJson).commit();
        }
    }

    public  UserInfoBean getUserInfoData() {
        UserInfoBean userInfoBean;
        String strJson = sp.getString(SP_UserInfoState, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
        userInfoBean = gson.fromJson(strJson, new TypeToken<UserInfoBean>() {}.getType());
        return userInfoBean;
    }

    public String getDeviceId(){
         deviceId = sp.getString(SP_DeviceId, null);
         if(deviceId == null){
             Random random = new Random();//指定种子数字2147483647
             deviceId = "" + random.nextInt(  1000000000);
             sp.edit().putString(SP_DeviceId, deviceId).commit();
         }
         return deviceId;
    }
}
