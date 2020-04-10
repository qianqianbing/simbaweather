package com.simba.membercenter.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.simba.membercenter.R;
import com.simba.membercenter.presenter.HttpRequest;

public class LoginActivity extends Activity implements View.OnClickListener, ILoginView{

    private LinearLayout mLlQRLogin, mLlAccountLogin;
    private TextView mTvQRLogin, mTvAccountLogin;
    private Button mBtLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        HttpRequest.getIntance().registerLoginViews(this);
        initView();
        setListener();
    }

    @Override
    protected void onDestroy() {
        HttpRequest.getIntance().unRegisterLoginViews();
        super.onDestroy();
    }

    private void initView(){
        mTvQRLogin = findViewById(R.id.tv_QRLogin);
        mTvAccountLogin = findViewById(R.id.tv_AccountLogin);

        mLlQRLogin = findViewById(R.id.ll_QRLogin);
        mLlAccountLogin = findViewById(R.id.ll_AccountLogin);

        mBtLogin = findViewById(R.id.bt_login);
    }

    private void setListener(){
        mTvQRLogin.setOnClickListener(this);
        mTvAccountLogin.setOnClickListener(this);
        mBtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_QRLogin:
                mLlQRLogin.setVisibility(View.VISIBLE);
                mLlAccountLogin.setVisibility(View.GONE);
                break;
            case R.id.tv_AccountLogin:
                mLlQRLogin.setVisibility(View.GONE);
                mLlAccountLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.bt_login:
                HttpRequest.getIntance().login();
                break;
        }
    }

    @Override
    public void onLoginSucceed() {
        finish();
        MainActivity.startAcivity();
        ((Activity) this).overridePendingTransition(0, 0);
    }

    @Override
    public void onLoginFailed() {

    }
}
