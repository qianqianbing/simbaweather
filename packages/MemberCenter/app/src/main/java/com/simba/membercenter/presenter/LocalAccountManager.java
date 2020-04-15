package com.simba.membercenter.presenter;

import com.simba.membercenter.view.IDeviceActivationView;
import com.simba.membercenter.view.ILoginView;

import java.util.ArrayList;
import java.util.List;

public class LocalAccountManager {

    private static LocalAccountManager localAccountManager;
    public synchronized static LocalAccountManager getIntance() {
        if (localAccountManager == null) {
            localAccountManager = new LocalAccountManager();
        }
        return localAccountManager;
    }

    //获取激活状态
    public void getDeviceActivationState(){
        if(mDeviceActivationViews != null ){
            for(IDeviceActivationView deviceActivationView : mDeviceActivationViews){
                deviceActivationView.onDeviceNotActivation();
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
