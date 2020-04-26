package com.simba.membercenter.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.greendao.gen.AccountBeanDao;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;
import com.simba.membercenter.bean.AccountBean;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.bean.LoginResultBean;
import com.simba.membercenter.bean.UserCertificationBean;
import com.simba.membercenter.bean.VehicleInfoBean;
import com.simba.membercenter.bean.WeCharUrlBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.simba.base.network.ConstantDefine.QRTYPE_ACTIVATION;
import static com.simba.base.network.ConstantDefine.QRTYPE_LOGIN;
import static com.simba.base.network.SimbaUrl.ACCOUNT_GET_QRCODE;
import static com.simba.base.network.SimbaUrl.ACCOUNT_GET_VEHICLEINFO;
import static com.simba.base.network.SimbaUrl.ACCOUNT_LOGIN;
import static com.simba.base.network.SimbaUrl.ACCOUNT_USER_INFO;
import static com.simba.base.network.SimbaUrl.VEHICLE_CERTIFICATION;

public class HttpRequest {
    private static String TAG = "HttpRequest";
    private static HttpRequest httpRequest;
    AccountBeanDao accountBeanDao;
    public synchronized static HttpRequest getIntance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    public HttpRequest() {
        accountBeanDao = MyApplication.getMyApplication().getDaoSession().getAccountBeanDao();
    }

    private  String token = "";

    public  void setToken(String token) {
        this.token = token;
    }

    public  String getToken() {
        return token;
    }

    public void getUserInfo(Context mContext, UserInfoCallback mUserInfoView){
        OkGo.<AccountBean>post(ACCOUNT_USER_INFO)
                .tag(mContext)
                .headers("token",token)
                .execute(new JsonCallback<AccountBean>() {
                    @Override
                    public void onSuccess(Response<AccountBean> response) {
                        if (isCode200()) {
                            AccountBean accountBeanFromNet = response.body();
                            List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.IsLogined.eq(1)).list();
                            for (AccountBean accountBeanFromDB : accountBeans){

                                accountBeanFromDB.setPhone(accountBeanFromNet.getPhone());
                                accountBeanFromDB.setOpenid(accountBeanFromNet.getOpenid());
                                accountBeanFromDB.setHeadimgurl(accountBeanFromNet.getHeadimgurl());
                                accountBeanFromDB.setNickname(accountBeanFromNet.getNickname());
                                accountBeanFromDB.setSex(accountBeanFromNet.getSex());
                                accountBeanFromDB.setOwned(accountBeanFromNet.getOwned());
                                accountBeanDao.update(accountBeanFromDB);
                                if(mUserInfoView != null){
                                    mUserInfoView.onUserInfoResult(true,accountBeanFromDB);
                                }
                                continue;
                            }

                        }else {
                            if (mUserInfoView != null){
                                mUserInfoView.onUserInfoResult(false,null);
                            }
                        }
                    }
                });
    }

    //获取实名认证信息
    public void getUserCertificatedInfo(Context mContext,UserCertificatedInfoCallback userCertificatedInfoCallback){
        OkGo.<UserCertificationBean>post(VEHICLE_CERTIFICATION)
                .tag(mContext)
                .headers("token",token)
                .execute(new JsonCallback<UserCertificationBean>() {
                    @Override
                    public void onSuccess(Response<UserCertificationBean> response) {

                        if(isCode200()){
                            UserCertificationBean userCertificationBean = response.body();
                            //实名认证：0否 1是
                            if(userCertificationBean.getCertificated() == 0){
                                userCertificatedInfoCallback.onUserCertificatedInfoResult(false);
                            }else {
                                userCertificatedInfoCallback.onUserCertificatedInfoResult(true);
                            }

                        }else {
                            userCertificatedInfoCallback.onUserCertificatedInfoResult(false);
                        }
                    }

                    @Override
                    public void onError(Response<UserCertificationBean> response) {
                        Log.e(TAG, "certificated code is " + response.getRawResponse().code());
                        super.onError(response);

                    }
                });
    }

    //通过账号密码登陆
    public void loginWithPassword(Activity mContext, final  String userName, String password, LoginCallback loginHander){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.USERNAME, userName);
            jsonObject.put(ConstantDefine.PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<LoginResultBean>post(ACCOUNT_LOGIN)
                .tag(mContext)
                .upJson(jsonObject)
                .execute(new JsonCallback<LoginResultBean>() {
                    @Override
                    public void onSuccess(Response<LoginResultBean> response) {

                        if (isCode200()) {
                            LoginResultBean loginResultBean = response.body();
                            Log.e(TAG, "Token is " + loginResultBean.getToken());

                            token = loginResultBean.getToken();
                            if(token == null){
                                loginHander.onLoginResult(false, response.getRawResponse().code(),getResponseMessage());
                            }else {
                                LocalAccountManager.getIntance().refreshLoginInfo(userName);
                                if(loginHander != null){
                                    loginHander.onLoginResult(true,0,getResponseMessage());
                                }
                            }

                        }else {
                            Log.e(TAG, "login failed");
                            loginHander.onLoginResult(false, getResponseCode(), getResponseMessage());
                        }
                    }

                    @Override
                    public int getHttpCode() {
                        return super.getHttpCode();
                    }

                    @Override
                     public void onError(Response<LoginResultBean> response) {
                        Log.e(TAG, "login error " + getResponseCode() + " httpcode " + response.getRawResponse().code());
                        loginHander.onLoginResult(false, response.getRawResponse().code(), getResponseMessage());
                         super.onError(response);
                     }
                });
    }
    //获取激活二维码
    public void requestActivationQRCode(Context context, int vehicleLoginId, QRCodeCallback qrCodeCallback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.ACTION, QRTYPE_ACTIVATION);
            jsonObject.put(ConstantDefine.CALLBACKURL, ConstantDefine.WeChatURL);
            jsonObject.put(ConstantDefine.DEVICEID, DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getDeviceId());
            jsonObject.put(ConstantDefine.VEHICLELOGINID, vehicleLoginId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<WeCharUrlBean>post(ACCOUNT_GET_QRCODE)
                .tag(context)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeCharUrlBean>() {
                    @Override
                    public void onSuccess(Response<WeCharUrlBean> response) {
                        if (isCode200()) {
                            WeCharUrlBean weCharUrl = response.body();
                            Log.e(TAG, "activation QRCode is : " + weCharUrl.getUrl());
                            qrCodeCallback.onQRCodeResult(true,weCharUrl.getUrl());
                        }
                    }
                });
    }

    //获取二维码登陆的二维码
    public void requestLoginQRCode(Context context, int vehicleLoginId, QRCodeCallback qrCodeCallback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.ACTION, QRTYPE_LOGIN);
            jsonObject.put(ConstantDefine.CALLBACKURL, ConstantDefine.WeChatURL);
            jsonObject.put(ConstantDefine.DEVICEID, DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getDeviceId());
            jsonObject.put(ConstantDefine.VEHICLELOGINID, vehicleLoginId);
            Log.e(TAG, "vehicleLoginId " + vehicleLoginId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<WeCharUrlBean>post(ACCOUNT_GET_QRCODE)
                .tag(context)
                .upJson(jsonObject)
                .execute(new JsonCallback<WeCharUrlBean>() {
                    @Override
                    public void onSuccess(Response<WeCharUrlBean> response) {
                        if (isCode200()) {
                            WeCharUrlBean weCharUrl = response.body();
                            Log.e(TAG, "Login weCharUrl " + weCharUrl.getUrl());
                            qrCodeCallback.onQRCodeResult(true,weCharUrl.getUrl());
                        }
                    }
                });
    }
    //获取车辆类型
    public void requestVehicleInfo(Context context,UserVehicleInfoCallback userVehicleInfoCallback) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.DEVICEID, DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getDeviceId());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<VehicleInfoBean>post(ACCOUNT_GET_VEHICLEINFO)
                .tag(context)
                .upJson(jsonObject)
                .execute(new JsonCallback<VehicleInfoBean>() {
                    @Override
                    public void onSuccess(Response<VehicleInfoBean> response) {
                        if (isCode200()) {
                            VehicleInfoBean vehicleInfoBean = response.body();
                            Log.e(TAG, "plate no: " + vehicleInfoBean.getPlateno() + " vehicle type " + vehicleInfoBean.getVehicleType());
                            userVehicleInfoCallback.onUserVehicleInfoResult(vehicleInfoBean);
                        }
                    }
                });
    }

    // 获取二维码的回调
    public interface QRCodeCallback {
        void onQRCodeResult(boolean isSucceed, String  QRCodeURI);
    }
    //登陆成功失败的回调
    public interface LoginCallback {
        void onLoginResult(Boolean isSucceed, int failCode, String message);
    }
    //获取用户信息
    public interface UserInfoCallback {
        void onUserInfoResult(boolean isSucceed, AccountBean accountBean);
    }

    //获取用户信息
    public interface UserCertificatedInfoCallback {
        void onUserCertificatedInfoResult(boolean isCertificated);
    }

    //获取车型信息
    public interface UserVehicleInfoCallback {
        void onUserVehicleInfoResult(VehicleInfoBean vehicleInfoBean);
    }
}
