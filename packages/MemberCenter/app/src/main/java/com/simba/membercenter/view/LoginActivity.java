package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.base.base.BaseActivity;
import com.simba.base.network.ConstantDefine;
import com.simba.base.network.JsonCallback;
import com.simba.base.utils.Toasty;
import com.simba.membercenter.MyApplication;
import com.simba.base.utils.QRCodeUtil;
import com.simba.membercenter.R;
import com.simba.membercenter.bean.LoginResultBean;
import com.simba.membercenter.bean.WeCharUrlBean;
import com.simba.membercenter.presenter.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import static com.simba.base.network.ConstantDefine.QRTYPE_LOGIN;
import static com.simba.base.network.SimbaUrl.ACCOUNT_GET_QRCODE;
import static com.simba.base.network.SimbaUrl.ACCOUNT_WEBAUTHLOGIN;

public class LoginActivity extends BaseActivity implements View.OnClickListener, HttpRequest.LoginCallback, HttpRequest.QRCodeCallback {
    private static String TAG = "LoginActivity";

    private static int QRLoginType = 1;
    private static int AccountLoginType = 2;

    private RelativeLayout mRlQRLogin, mRlAccountLogin;
    private TextView tv_Login1, tv_Login2,tv_useragreement;
    private EditText et_username, et_password;
    private Button mBtLogin,bt_agress;

    private ImageView iv_QRCode_login,iv_cancel,iv_logintype;

    private static String USERNAME = "USERNAME";
    //喜欢查询二维码扫码登陆的结果
    private final static int CheckQRLoginResult = 100;
    // 二维码的登陆id，随机数
    private int vehicleLoginId ;
    private Handler loginHander = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case CheckQRLoginResult:
                    checkQRloginResult();
                    loginHander.sendEmptyMessageDelayed(CheckQRLoginResult, 5000);
                    break;

            }
            super.handleMessage(msg);
        }
    };

    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    public static void startAcivityWithLoginId( String userName){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(USERNAME,userName);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

        setListener();
        String userName = "" ;
        try {
            userName = getIntent().getStringExtra(USERNAME);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(userName != null && !userName.isEmpty()){
            initLoginType(userName);
        }


        Random random = new Random();//指定种子数字2147483647
        vehicleLoginId = random.nextInt(  1000000000);
        Log.e(TAG, "vehicleLoginId " + vehicleLoginId );
        HttpRequest.getIntance().requestLoginQRCode( this,  vehicleLoginId, this);
    }

    @Override
    protected void onDestroy() {
        loginHander.removeMessages(CheckQRLoginResult);
        super.onDestroy();
    }

    protected void initView(){
        tv_Login1 = findViewById(R.id.tv_Login1);
        tv_Login2 = findViewById(R.id.tv_Login2);
        mRlQRLogin = findViewById(R.id.rl_QRLogin);
        mRlAccountLogin = findViewById(R.id.rl_AccountLogin);
        mBtLogin = findViewById(R.id.bt_login);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_logintype = findViewById(R.id.iv_logintype);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        bt_agress = findViewById(R.id.bt_agress);
        bt_agress.setOnClickListener(this);
        iv_QRCode_login = findViewById(R.id.iv_QRCode_login);
        iv_QRCode_login.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 225,225));
        tv_useragreement = findViewById(R.id.tv_useragreement);
        tv_useragreement.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG ); //下划线

    }

    private void setListener(){
        tv_Login1.setOnClickListener(this);
        tv_Login2.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
        iv_cancel.setOnClickListener(this);
        iv_logintype.setOnClickListener(this);
        tv_useragreement.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_useragreement:
                UserAgreementActivity.startAcivity();
                break;
            case R.id.bt_agress:
                Log.e(TAG, "bt_agress " + bt_agress.isSelected());
                if(bt_agress.isSelected()){
                    bt_agress.setSelected(false);
                }else {
                    bt_agress.setSelected(true);
                }
                break;
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.tv_Login1:
                break;
            case R.id.tv_Login2:

                if(tv_Login2.getText().toString().equals(getResources().getString(R.string.Account_login))){
                    switchLoginType(AccountLoginType);
                }else {
                    switchLoginType(QRLoginType);
                }
                break;
            case R.id.bt_login:
                String userName = et_username.getText().toString();
                String password = et_password.getText().toString();
                if(userName == null || userName.isEmpty()){
                    Toasty.normal(getApplicationContext(),"请输入用户名").show();
                    break;
                }
                if(password == null || password.isEmpty()){
                    Toasty.normal(getApplicationContext(),"请输入密码").show();
                    break;
                }
                if(!bt_agress.isSelected()){
                    Toasty.normal(getApplicationContext(),"请阅读并同意用户协议").show();
                    break;
                }
                HttpRequest.getIntance().loginWithPassword(this, userName,password,this);
                break;
        }
    }

    //切换界面上的登陆方式
    private void switchLoginType(int loginType ){
        if (loginType == QRLoginType){
            mRlQRLogin.setVisibility(View.VISIBLE);
            mRlAccountLogin.setVisibility(View.GONE);

            tv_Login1.setText(R.string.QR_login);
            tv_Login2.setText(R.string.Account_login);
            iv_logintype.setImageDrawable(getResources().getDrawable(R.drawable.icon_passwordlogin));
        }else {

            mRlQRLogin.setVisibility(View.GONE);
            mRlAccountLogin.setVisibility(View.VISIBLE);
            tv_Login1.setText(R.string.Account_login);
            tv_Login2.setText(R.string.QR_login);
            iv_logintype.setImageDrawable(getResources().getDrawable(R.drawable.icon_qclogin));
        }
    }

    private void initLoginType(String userName){
        if( userName != null && !userName.isEmpty()){{
            switchLoginType(AccountLoginType);
            et_username.setText(userName);
        }}
    }

    @Override
    public void onLoginResult(Boolean isSucceed, int failCode, String message) {
        Log.e(TAG, "loginResult is " + isSucceed + "; failCode is " + failCode + "; message " + message);
        if(isSucceed){
            MainActivity.startAcivity();
            ((Activity) this).overridePendingTransition(0, 0);
            finish();
        }else {
            Log.e(TAG, "failCode is " + failCode);
            switch (failCode){
                case ConstantDefine.NETWORK_ERROR:
                    Toasty.normal(this,getResources().getString(R.string.network_error)).show();
                    break;
                default:
                    Toasty.normal(this, message).show();
                    break;
            }
        }
    }

    private void checkQRloginResult() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(ConstantDefine.DEVICEID, DeviceAccountManager.getInstance(MyApplication.getMyApplication().getApplicationContext()).getDeviceId());
            Log.e(TAG, "vehicleLoginId " + vehicleLoginId);
            jsonObject.put(ConstantDefine.VEHICLELOGINID, vehicleLoginId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        OkGo.<LoginResultBean>post(ACCOUNT_WEBAUTHLOGIN)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<LoginResultBean>() {
                    @Override
                    public void onSuccess(Response<LoginResultBean> response) {
                        LoginResultBean loginResultBean = response.body();
                        if (isCode200()) {

                            Log.e(TAG, "QRCode LoginResultBean " + loginResultBean.getToken());
                        }else {
                            Log.e(TAG, "QRCode onSuccess LoginResultBean " + getResponseCode());
                        }
                    }

                    @Override
                    public void onError(Response<LoginResultBean> response) {
                        Log.e(TAG, "QRCode LoginResultBean " + response.getRawResponse());
                        super.onError(response);
                    }
                });
    }

    @Override
    public void onQRCodeResult(boolean isSucceed, String QRCodeURI) {
        if(isSucceed){
            Log.e(TAG, "QRCodeURI " +QRCodeURI);
            iv_QRCode_login.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap(QRCodeURI, 225,225));
            loginHander.sendEmptyMessageAtTime(CheckQRLoginResult,2000);
        }
    }
}
