package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.greendao.gen.AccountBeanDao;
import com.greendao.gen.DeviceStateBeanDao;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;
import com.simba.membercenter.DB.AccountBean;
import com.simba.membercenter.DB.DeviceStateBean;
import com.simba.membercenter.presenter.LocalAccountManager;
import com.simba.membercenter.ui.popupwindow.RealNameAuthenticationPopupWindow;
import com.simba.membercenter.ui.popupwindow.SwitchAccountPopupWindow;
import com.simba.membercenter.ui.popupwindow.UnbindPopupWindow;

public class MainActivity extends Activity implements View.OnClickListener, SwitchAccountPopupWindow.AccountSwitchHander {

    private static String TAG = "MainActivity";
    private RelativeLayout rl_message, rl_more, rl_bind_wechat;
    private ImageView iv_switch_account;
    AccountBeanDao accountBeanDao;
    private Context mContext;
    SwitchAccountPopupWindow switchAccountPopupWindow;
    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        mContext = this;
        rl_message = findViewById(R.id.rl_message);
        rl_message.setOnClickListener(this);
        rl_more = findViewById(R.id.rl_more);
        rl_more.setOnClickListener(this);
        iv_switch_account = findViewById(R.id.iv_switch_account);
        iv_switch_account.setOnClickListener(this);
        rl_bind_wechat = findViewById(R.id.rl_bind_wechat);
        rl_bind_wechat.setOnClickListener(this);
        if(! DeviceAccountManager.getInstance(mContext).isRealNameAuthentication()){
            RealNameAuthenticationPopupWindow realNameAuthenticationPopupWindow = new RealNameAuthenticationPopupWindow();
            realNameAuthenticationPopupWindow.showPopupWindow(getApplicationContext());
        }

        testInsertAccountInfo();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_message:
                Log.e(TAG, "  rl_message click " );
                MessageActivity.startAcivity();
                break;
            case R.id.rl_more:
                Log.e(TAG, "rl_more click");
                VersionDetailsActivity.startAcivity();
                break;
            case R.id.rl_bind_wechat:
                new UnbindPopupWindow().showPopupWindow(getApplicationContext());
                break;
            case R.id.iv_switch_account:

               switchAccountPopupWindow = new SwitchAccountPopupWindow(mContext,accountBeanDao.loadAll());
               switchAccountPopupWindow.setAccountSwitchHander(this);
               switchAccountPopupWindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0,0);
                break;
        }
    }

    @Override
    public void accountSwitch(AccountBean accountBean) {
        LocalAccountManager.getIntance().quitLogin();
        LoginActivity.startAcivityWithLoginId(accountBean.getUserId());
        finish();
    }

    //测试代码，插入用户账号信息
    public void testInsertAccountInfo(){
         accountBeanDao = MyApplication.getMyApplication().getDaoSession().getAccountBeanDao();
        if (accountBeanDao.loadAll().size() == 0){
            Log.e(TAG, "insert account info");
            AccountBean accountBean1 = new AccountBean(11111111,"大爷1", true);
            accountBeanDao.insert(accountBean1);
            AccountBean accountBean2 = new AccountBean(22222222,"大娘2", false);
            accountBeanDao.insert(accountBean2);
            AccountBean accountBean3 = new AccountBean(33333333,"哥哥3", false);
            accountBeanDao.insert(accountBean3);
            AccountBean accountBean4 = new AccountBean(444444444,"爷爷4", false);
            accountBeanDao.insert(accountBean4);
        }
        DeviceStateBeanDao deviceStateBeanDao = MyApplication.getMyApplication().getDaoSession().getDeviceStateBeanDao();
        if(deviceStateBeanDao.loadAll().size() == 0){
            Log.e(TAG, "insert deviceState info");
            DeviceStateBean deviceStateBean = new DeviceStateBean(1);
            deviceStateBeanDao.insert(deviceStateBean);
        }
    }
}
