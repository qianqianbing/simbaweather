package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.simba.base.utils.Toasty;
import com.simba.membercenter.MyApplication;
import com.simba.base.utils.QRCodeUtil;
import com.simba.membercenter.R;
import com.simba.membercenter.presenter.HttpRequest;
import com.simba.membercenter.presenter.LocalAccountManager;

public class LoginActivity extends Activity implements View.OnClickListener, ILoginView{
    private static String TAG = "LoginActivity";

    private static int QRLoginType = 1;
    private static int AccountLoginType = 2;

    private RelativeLayout mRlQRLogin, mRlAccountLogin;
    private TextView tv_Login1, tv_Login2;
    private EditText et_username, et_password;
    private Button mBtLogin,bt_agress;
    private ImageView iv_QRCode_login,iv_cancel,iv_logintype;
    private static String USERNAME = "USERNAME";
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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HttpRequest.getIntance().registerLoginViews(this);

        initView();
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
    }

    @Override
    protected void onDestroy() {
        HttpRequest.getIntance().unRegisterLoginViews();
        super.onDestroy();
    }

    private void initView(){
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
    }

    private void setListener(){
        tv_Login1.setOnClickListener(this);
        tv_Login2.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
        iv_cancel.setOnClickListener(this);
        iv_logintype.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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

                HttpRequest.getIntance().loginWithPassword(userName,password);
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
    public void onLoginSucceed() {

        MainActivity.startAcivity();

        ((Activity) this).overridePendingTransition(0, 0);
        finish();
    }

    @Override
    public void onLoginFailed() {

    }
}
