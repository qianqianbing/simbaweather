package com.simba.membercenter.presenter;

import com.simba.membercenter.view.IDeviceActivationView;
import com.simba.membercenter.view.ILoginView;

import java.util.ArrayList;
import java.util.List;

public class HttpRequest {

    private static HttpRequest httpRequest;
    public synchronized static HttpRequest getIntance() {
        if (httpRequest == null) {
            httpRequest = new HttpRequest();
        }
        return httpRequest;
    }

    //获取激活状态
    public void getDeviceActivationState(){
        if(mDeviceActivationViews != null ){
            for(IDeviceActivationView deviceActivationView : mDeviceActivationViews){
                deviceActivationView.onDeviceActivation();
            }
        }
    }

    //通过账号密码登陆
    public void login(){
        if(mLoginView != null){
            mLoginView.onLoginSucceed();
        }
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

    private ILoginView mLoginView;
    public void registerLoginViews(ILoginView loginView) {
        mLoginView = loginView;
    }

    public void unRegisterLoginViews() {
        mLoginView = null;
    }
}
