package com.simba.membercenter.presenter;

import android.content.Context;
import android.util.Log;

import com.greendao.gen.AccountBeanDao;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;
import com.simba.membercenter.DB.AccountBean;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.bean.LoginResultBean;
import com.simba.membercenter.view.IDeviceActivationView;
import com.simba.membercenter.view.ILoginView;
import com.simba.membercenter.view.IUserInfoView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.simba.base.network.SimbaUrl.ACCOUNT_LOGIN;
import static com.simba.base.network.SimbaUrl.ACCOUNT_USER_INFO;

public class HttpRequest {
    private static String TAG = "LoginActivity";
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

    //获取激活状态
    public void getDeviceActivationState(){
        if(mDeviceActivationViews != null ){
            for(IDeviceActivationView deviceActivationView : mDeviceActivationViews){
                deviceActivationView.onDeviceNotActivation();
            }
        }
    }

    public void getUserInfo(Context mContext){
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
                                    mUserInfoView.onLoadSucceed(accountBeanFromDB);
                                }
                                continue;
                            }

                        }else {
                            if (mUserInfoView != null){
                                mUserInfoView.onLoadFailed();
                            }
                        }
                    }
                });
    }

    //通过账号密码登陆
    public void loginWithPassword(final  String userName, String password){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.USERNAME, userName);
            jsonObject.put(ConstantDefine.PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<LoginResultBean>post(ACCOUNT_LOGIN)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<LoginResultBean>() {
                    @Override
                    public void onSuccess(Response<LoginResultBean> response) {
                        if (isCode200()) {
                            LoginResultBean loginResultBean = response.body();
                            Log.e(TAG, "Token is " + loginResultBean.getToken());

                            token = loginResultBean.getToken();
                            LocalAccountManager.getIntance().refreshLoginInfo(userName);

                            if(mLoginView != null){
                                mLoginView.onLoginSucceed();
                            }
                        }else {
                            mLoginView.onLoginFailed();
                        }
                    }
                });
    }

    private List<IDeviceActivationView> mDeviceActivationViews = new ArrayList<>();
    public void registerDeviceActivationViews(IDeviceActivationView deviceActivationView) {
        if(!mDeviceActivationViews.contains(deviceActivationView)){
            mDeviceActivationViews.add(deviceActivationView);
        }
    }

    public void unRegisterDeviceActivationViews(IDeviceActivationView deviceActivationView) {
        if(mDeviceActivationViews.contains(deviceActivationView)){
            mDeviceActivationViews.remove(deviceActivationView);
        }
    }

    //登陆相关接口
    private ILoginView mLoginView;
    public void registerLoginViews(ILoginView loginView) {
        mLoginView = loginView;
    }

    public void unRegisterLoginViews() {
        mLoginView = null;
    }

    //获取用户信息相关接口
    private IUserInfoView mUserInfoView ;
    public void registerUserInfoView(IUserInfoView userInfoView){
        mUserInfoView = userInfoView;
    }

    public void unRegisterUserInfoView(){
        mUserInfoView = null;
    }
}
