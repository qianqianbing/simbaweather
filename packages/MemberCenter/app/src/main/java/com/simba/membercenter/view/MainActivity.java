package com.simba.membercenter.view;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.greendao.gen.AccountBeanDao;
import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.picker.SinglePickerManager;
import com.simba.base.network.ConstantDefine;
import com.simba.membercenter.bean.AccountBean;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;
import com.simba.membercenter.bean.VehicleInfoBean;
import com.simba.membercenter.presenter.HttpRequest;
import com.simba.membercenter.presenter.LocalAccountManager;
import com.simba.membercenter.ui.popupwindow.UnbindPopupWindow;


import java.util.List;

import static com.simba.base.network.ConstantDefine.CAR_OWNER;

public class MainActivity extends BaseActivity implements View.OnClickListener , HttpRequest.UserInfoCallback ,HttpRequest.UserCertificatedInfoCallback, HttpRequest.UserVehicleInfoCallback{

    private static String TAG = "MainActivity";
    private RelativeLayout rl_message, rl_more, rl_bind_wechat;
    private TextView tv_nickname, tv_id, tv_owned, tv_cartype;
    private ImageView iv_switch_account, iv_sex, iv_member;
    AccountBeanDao accountBeanDao;
    private Context mContext;

    public static void startAcivity() {
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mContext = this;
        tv_cartype = findViewById(R.id.tv_cartype);
        tv_nickname = findViewById(R.id.tv_nickname);
        tv_id = findViewById(R.id.tv_id);
        tv_owned = findViewById(R.id.tv_owned);
        iv_sex = findViewById(R.id.iv_sex);
        iv_member = findViewById(R.id.iv_member);
        rl_message = findViewById(R.id.rl_message);
        rl_message.setOnClickListener(this);
        rl_more = findViewById(R.id.rl_more);
        rl_more.setOnClickListener(this);
        iv_switch_account = findViewById(R.id.iv_switch_account);
        iv_switch_account.setOnClickListener(this);
        rl_bind_wechat = findViewById(R.id.rl_bind_wechat);
        rl_bind_wechat.setOnClickListener(this);

    }

    @Override
    protected void initData() {
       /* if(! DeviceAccountManager.getInstance(mContext).isRealNameAuthentication()){
            RealNameAuthenticationPopupWindow realNameAuthenticationPopupWindow = new RealNameAuthenticationPopupWindow();
            realNameAuthenticationPopupWindow.showPopupWindow(getApplicationContext());
        }
        */

        //在数据库中查询是否已经登陆
        AccountBean accountBean = LocalAccountManager.getIntance().getLoginAccount();
        if (accountBean == null) {
            LoginActivity.startAcivity();
            finish();
        } else {
            HttpRequest.getIntance().setToken(accountBean.getToken());
            HttpRequest.getIntance().getUserInfo(this, this);
            //用户认证
            HttpRequest.getIntance().getUserCertificatedInfo(this,this);
            HttpRequest.getIntance().requestVehicleInfo(this, this);
        }

        testInsertAccountInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_message:
                Log.e(TAG, "  rl_message click ");
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
                final List<AccountBean> accountBeans = accountBeanDao.loadAll();
                final SinglePickerManager pickerManager = new SinglePickerManager(this, accountBeans);

                pickerManager.setTitle(R.string.account_manage);
                pickerManager.setConfirmText(mContext.getResources().getString(R.string.switch_account));
                pickerManager.setConfirmTextColor(mContext.getResources().getColor(R.color.base_white));
                pickerManager.setOnConfirmListener(new SinglePickerManager.onConfirmClickListener() {
                    @Override
                    public void onClick(int checkedItemPosition) {
                        pickerManager.dismiss();
                        LocalAccountManager.getIntance().quitLogin();
                        LoginActivity.startAcivityWithLoginId(accountBeans.get(checkedItemPosition).getUsername());
                        finish();
                    }
                });
                pickerManager.show();
                break;
        }
    }

    //测试代码，插入用户账号信息
    public void testInsertAccountInfo() {
        accountBeanDao = MyApplication.getMyApplication().getDaoSession().getAccountBeanDao();
        if (accountBeanDao.loadAll().size() < 2) {
            Log.e(TAG, "insert account info");
            AccountBean accountBean1 = new AccountBean("11111111", "大爷1", false);
            accountBeanDao.insert(accountBean1);
            AccountBean accountBean2 = new AccountBean("22222222", "大娘2", false);
            accountBeanDao.insert(accountBean2);
            AccountBean accountBean3 = new AccountBean("33333333", "哥哥3", false);
            accountBeanDao.insert(accountBean3);
            AccountBean accountBean4 = new AccountBean("444444444", "爷爷4", false);
            accountBeanDao.insert(accountBean4);
        }
    }

    @Override
    public void onUserInfoResult(boolean isSucceed, AccountBean accountBean) {
        if(isSucceed){
            if (accountBean != null) {
                tv_nickname.setText(accountBean.getNickname());
                String userName = accountBean.getPhone();
                String id = userName.substring(0, 3) + "****" + userName.substring(7, userName.length());
                tv_id.setText(id);
                //是否为车主：0否 1是
                if (accountBean.getOwned() == CAR_OWNER) {
                    tv_owned.setText(R.string.car_owner);
                } else {
                    tv_owned.setText(R.string.not_car_onwer);
                }
                //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
                switch (accountBean.getSex()) {
                    case ConstantDefine.MAN:
                    case ConstantDefine.SEX_UNKNOW:
                        iv_sex.setImageDrawable(getResources().getDrawable(R.drawable.icon_man));
                        break;
                    case ConstantDefine.WOMAN:
                        iv_sex.setImageDrawable(getResources().getDrawable(R.drawable.icon_woman));
                        break;
                }
                RequestOptions options = new RequestOptions()
                        .placeholder(R.drawable.icon_wechat)//图片加载出来前，显示的图片
                        .fallback(R.drawable.icon_wechat) //url为空的时候,显示的图片
                        .error(R.drawable.icon_wechat);//图片加载失败后，显示的图片
                Glide.with(this).load(accountBean.getHeadimgurl()).apply(options).into(iv_member);

            }
        }
    }

    @Override
    public void onUserCertificatedInfoResult(boolean isCertificated) {
        Log.e(TAG, "usecertificate " + isCertificated);

    }

    @Override
    public void onUserVehicleInfoResult(VehicleInfoBean vehicleInfoBean) {
        if(vehicleInfoBean != null){
            tv_cartype.setText(vehicleInfoBean.getVehicleType());
        }
    }
}
