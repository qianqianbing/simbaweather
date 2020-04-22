package com.simba.themestore.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.simba.themestore.MyApplication;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class AppUtils {
    /**
     * 返回当前程序版本号
     */
    public static String getAppVersionCode() {
        int versioncode = 0;
        try {
            PackageManager pm = MyApplication.sContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MyApplication.sContext.getPackageName(), 0);
            versioncode = pi.versionCode;
        } catch (Exception e) {

        }
        return versioncode + "";
    }

    /**
     * 返回当前程序版本名
     */
    public static String getAppVersionName() {
        String versionName = "beta 1.0.0";
        try {
            PackageManager pm = MyApplication.sContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(MyApplication.sContext.getPackageName(), 0);
            versionName = pi.versionName;
        } catch (Exception e) {

        }
        return versionName;
    }

}
