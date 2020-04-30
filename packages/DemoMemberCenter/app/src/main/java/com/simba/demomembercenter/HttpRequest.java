package com.simba.demomembercenter;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;



public class HttpRequest {

    public static String DEMO_BASE_HOST_ACCOUNT = "http://demo.simbalink.cn/backend";

    public final static String DEMO_ACCOUNT_GET_QRCODE = DEMO_BASE_HOST_ACCOUNT + "/demo/getQRCode";
    public final static String DEMO_ACCOUNT_GET_USERINFO = DEMO_BASE_HOST_ACCOUNT + "/demo/getUserInfo";

    private static String TAG = "HttpRequest";
    private static HttpRequest httpRequest;
    public synchronized static HttpRequest getIntance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public HttpRequest() {
    }

    public static String getMacDefault(Context context) {
        String mac = null;
        if (context == null) {
            return mac;
        }

        WifiManager wifi = (WifiManager) context.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (wifi == null) {
            return mac;
        }
        WifiInfo info = null;
        try {
            info = wifi.getConnectionInfo();
        } catch (Exception e) {

        }
        if (info == null) {
            return null;
        }
        mac = info.getMacAddress();
        if (!TextUtils.isEmpty(mac)) {
            mac = mac.toUpperCase(Locale.ENGLISH);
        }
        return mac;
    }

    public void requestLoginQRCode(Context context,  QRCodeCallback qrCodeCallback) {
        JSONObject jsonObject = new JSONObject();
        try {
            String deviceId = UserInfoManager.getInstance().getDeviceId();
            Log.e(TAG, "deviceId is " + deviceId);
            jsonObject.put(ConstantDefine.DEVICEID, deviceId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<WeCharUrlBean>post(DEMO_ACCOUNT_GET_QRCODE)
                .tag(context)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeCharUrlBean>() {
                    @Override
                    public void onSuccess(Response<WeCharUrlBean> response) {
                        if (isCode200()) {
                            WeCharUrlBean weCharUrl = response.body();
                            Log.e(TAG, "Login weCharUrl " + weCharUrl.getUrl());
                            qrCodeCallback.onQRCodeResult(true, weCharUrl.getUrl());
                        }
                    }
                });
    }

    public void requestUserInfo(Context context,  UserInfoCallback userInfoCallback) {
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.put(ConstantDefine.DEVICEID,  UserInfoManager.getInstance().getDeviceId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<UserInfoBean>post(DEMO_ACCOUNT_GET_USERINFO)
                .tag(context)
                .upJson(jsonObject)
                .execute(new JsonCallback<UserInfoBean>() {
                    @Override
                    public void onSuccess(Response<UserInfoBean> response) {
                        if (isCode200()) {
                            UserInfoBean userInfoBean = response.body();
                            userInfoCallback.onUserInfoResult(userInfoBean);
                        }
                    }
                });
    }

    // 获取二维码的回调
    public interface QRCodeCallback {
        void onQRCodeResult(boolean isSucceed, String QRCodeURI);
    }

    // 获取用户信息的回调
    public interface UserInfoCallback {
        void onUserInfoResult( UserInfoBean userInfoBean);
    }
}
