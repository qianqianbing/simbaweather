package com.simba.demomembercenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class UserInfoManager {
    private static String TAG = "AccountInfoManager";
    SharedPreferences sp;
    public static String SharePreName = "UserInfoSharedPre";
    private static String UserInfoState = "userInfoState";
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
            sp.edit().remove(UserInfoState).commit();
        }else {
            Gson gson = new Gson();
            //转换成json数据，再保存
            String strJson = gson.toJson(userInfoBean);
            sp.edit().putString(UserInfoState, strJson).commit();
        }
    }

    public  UserInfoBean getUserInfoData() {
        UserInfoBean userInfoBean;
        String strJson = sp.getString(UserInfoState, null);
        if (null == strJson) {
            return null;
        }
        Gson gson = new Gson();
        userInfoBean = gson.fromJson(strJson, new TypeToken<UserInfoBean>() {}.getType());
        return userInfoBean;
    }
}
