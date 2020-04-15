package com.simba.membercenter.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.simba.membercenter.QRCodeUtil;
import com.simba.membercenter.R;
import com.simba.membercenter.presenter.HttpRequest;

public class LoginActivity extends Activity implements View.OnClickListener, ILoginView{
    private static String TAG = "LoginActivity";
    private RelativeLayout mRlQRLogin, mRlAccountLogin;
    private TextView tv_Login1, tv_Login2;
    private Button mBtLogin,bt_agress;
    private ImageView iv_QRCode_login,iv_cancel,iv_logintype;
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
        tv_Login1 = findViewById(R.id.tv_Login1);
        tv_Login2 = findViewById(R.id.tv_Login2);
        mRlQRLogin = findViewById(R.id.rl_QRLogin);
        mRlAccountLogin = findViewById(R.id.rl_AccountLogin);
        mBtLogin = findViewById(R.id.bt_login);
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_logintype = findViewById(R.id.iv_logintype);
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
                Log.e(TAG, "tv_login2 text is " + tv_Login2.getText());
                if(tv_Login2.getText().toString().equals(getResources().getString(R.string.Account_login))){
                    Log.e(TAG, "tv_login2 text is " + 1);
                    mRlQRLogin.setVisibility(View.GONE);
                    mRlAccountLogin.setVisibility(View.VISIBLE);
                    tv_Login1.setText(R.string.Account_login);
                    tv_Login2.setText(R.string.QR_login);
                    iv_logintype.setImageDrawable(getResources().getDrawable(R.drawable.icon_qclogin));
                }else {
                    Log.e(TAG, "tv_login2 text is " + 2);
                    mRlQRLogin.setVisibility(View.VISIBLE);
                    mRlAccountLogin.setVisibility(View.GONE);

                    tv_Login1.setText(R.string.QR_login);
                    tv_Login2.setText(R.string.Account_login);
                    iv_logintype.setImageDrawable(getResources().getDrawable(R.drawable.icon_passwordlogin));
                }
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
