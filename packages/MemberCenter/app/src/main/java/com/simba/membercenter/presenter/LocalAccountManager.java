package com.simba.membercenter.presenter;

import com.greendao.gen.AccountBeanDao;
import com.greendao.gen.DeviceStateBeanDao;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.bean.AccountBean;

import java.util.List;

import static com.simba.base.DeviceAccountManager.DeviceAccountManager.DEVICE_STATE_URI;

public class LocalAccountManager {

    private static LocalAccountManager localAccountManager;
    DeviceStateBeanDao deviceStateBeanDao;
    AccountBeanDao accountBeanDao;
    private String userName = "-1";

    public synchronized static LocalAccountManager getIntance() {
        if (localAccountManager == null) {
            localAccountManager = new LocalAccountManager();
        }
        return localAccountManager;
    }

    private LocalAccountManager() {
        deviceStateBeanDao = MyApplication.getMyApplication().getDaoSession().getDeviceStateBeanDao();
        accountBeanDao = MyApplication.getMyApplication().getDaoSession().getAccountBeanDao();
        userName = DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getLoginedAccount();
    }

    public AccountBean getLoginAccount(){
        List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.IsLogined.eq(1)).list();
        if(accountBeans != null && accountBeans.size() != 0){
            for (AccountBean accountBean : accountBeans){
                return accountBean;
            }
        }
        return null;
    }


    public void quitLogin(){
        //只会查找出一个结果
        userName = "";


        //只会查找出一个结果
        List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.IsLogined.eq(1)).list();
        for(AccountBean accountBean : accountBeans){
            accountBean.setIsLogined(false);
            accountBean.setToken(null);
            accountBeanDao.update(accountBean);
        }

        accountBeans = accountBeanDao.loadAll();
        for(AccountBean accountBean: accountBeans){
            if(accountBean.getUsername()==null || !accountBean.getUsername().isEmpty()){
                accountBeanDao.delete(accountBean);
            }
        }
    }

    //登陆成功后，更新登陆账号的数据库信息
    public void refreshLoginInfo(String userName){

        if(!userName.isEmpty()){


            List<AccountBean> accountBeans = accountBeanDao.queryBuilder().where(AccountBeanDao.Properties.Username.eq(userName)).list();
            if(accountBeans == null || accountBeans.size() == 0){
                AccountBean accountBean = new AccountBean();
                accountBean.setUsername(userName);
                accountBean.setIsLogined(true);
                accountBean.setToken(HttpRequest.getIntance().getToken());
                accountBeanDao.insert(accountBean);
            }else {
                for(AccountBean accountBean : accountBeans){

                    accountBean.setIsLogined(true);
                    accountBean.setToken(HttpRequest.getIntance().getToken());
                    accountBeanDao.update(accountBean);
                }
            }

            MyApplication.getMyApplication().getApplicationContext().getContentResolver().notifyChange(DEVICE_STATE_URI, null);
            this.userName = userName;
        }
    }


    public String getUserName() {
        return userName;
    }

}
