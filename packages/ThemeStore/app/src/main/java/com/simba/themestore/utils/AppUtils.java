package com.simba.themestore.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import androidx.core.app.ActivityCompat;

import com.simba.base.utils.Toasty;
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

    /**
     *
     * @param cxt
     * @param phoneNum
     */
    public static void callPhone(Context cxt, String phoneNum) {
      //  Intent intent = new Intent(Intent.ACTION_CALL);
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
//        if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            Toasty.info(cxt,"没有拨打电话权限");
//            return;
//        }
        cxt.startActivity(intent);
    }

}
