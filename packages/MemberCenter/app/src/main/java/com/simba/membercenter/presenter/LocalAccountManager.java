package com.simba.membercenter.presenter;

import com.greendao.gen.AccountBeanDao;
import com.greendao.gen.DeviceStateBeanDao;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.DB.AccountBean;
import com.simba.membercenter.DB.DeviceStateBean;
import com.simba.membercenter.view.IDeviceActivationView;
import com.simba.membercenter.view.ILoginView;

import java.util.ArrayList;
import java.util.List;

import static com.simba.base.DeviceAccountManager.DeviceAccountManager.DEVICE_STATE_URI;

public class LocalAccountManager {

    private static LocalAccountManager localAccountManager;
    DeviceStateBeanDao deviceStateBeanDao;
    AccountBeanDao accountBeanDao;
    private int loginId = -1;

    public synchronized static LocalAccountManager getIntance() {
        if (localAccountManager == null) {
            localAccountManager = new LocalAccountManager();
        }
        return localAccountManager;
    }

    private LocalAccountManager() {
        deviceStateBeanDao = MyApplication.getMyApplication().getDaoSession().getDeviceStateBeanDao();
        accountBeanDao = MyApplication.getMyApplication().getDaoSession().getAccountBeanDao();
        loginId = DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getLoginedAccount();
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

    public void quitLogin(){
        //只会查找出一个结果
        loginId = -1;
        List<DeviceStateBean> deviceStateBeans = deviceStateBeanDao.loadAll();
        for(DeviceStateBean deviceStateBean: deviceStateBeans){
            deviceStateBean.setLoginState(false);
            deviceStateBean.setLoginId(loginId);
            deviceStateBeanDao.update(deviceStateBean);
        }

        //只会查找出一个结果
        List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.IsLogined.eq(1)).list();
        for(AccountBean accountBean : accountBeans){
            accountBean.setIsLogined(false);
            accountBeanDao.update(accountBean);
        }
    }

    //登陆成功后，更新登陆账号的数据库信息
    public void refreshLoginInfo(int loginId){

        if(loginId != 0){
            //只会查找出一个结果
            List<DeviceStateBean> deviceStateBeans = deviceStateBeanDao.loadAll();
            for(DeviceStateBean deviceStateBean: deviceStateBeans){
                deviceStateBean.setLoginState(true);
                deviceStateBean.setLoginId(loginId);
                deviceStateBeanDao.update(deviceStateBean);
            }


            List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.UserId.eq(loginId)).list();
            for(AccountBean accountBean : accountBeans){
                accountBean.setIsLogined(true);
                accountBeanDao.update(accountBean);
            }
            MyApplication.getMyApplication().getApplicationContext().getContentResolver().notifyChange(DEVICE_STATE_URI, null);
            this.loginId = loginId;
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

    public int getLoginId() {
        return loginId;
    }
    private ILoginView mLoginView;
    public void registerLoginViews(ILoginView loginView) {
        mLoginView = loginView;
    }

    public void unRegisterLoginViews() {
        mLoginView = null;
    }
}
