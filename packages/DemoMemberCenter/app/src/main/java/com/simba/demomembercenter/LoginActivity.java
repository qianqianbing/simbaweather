package com.simba.demomembercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.simba.base.utils.QRCodeUtil;

public class LoginActivity extends Activity implements HttpRequest.QRCodeCallback, HttpRequest.UserInfoCallback{
    private static String TAG = "MainActivity";
    private ImageView iv_QRCode ;
    //喜欢查询二维码扫码登陆的结果
    private final static int CheckQRLoginResult = 100;

    public static void startAcivity() {
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        iv_QRCode = findViewById(R.id.iv_QRCode);
        //iv_QRCode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.baidu.com", (int)getResources().getDimension(R.dimen.QRCode_width),(int)getResources().getDimension(R.dimen.QRCode_width)));
        HttpRequest.getIntance().requestLoginQRCode(this, this);
        requestUserInfo();
    }

    public void requestUserInfo(){
        HttpRequest.getIntance().requestUserInfo(this, this);
        handler.sendEmptyMessageDelayed(CheckQRLoginResult, 5000);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case CheckQRLoginResult:
                    handler.removeMessages(CheckQRLoginResult);
                    requestUserInfo();
                    break;

            }
            super.handleMessage(msg);
        }
    };


    @Override
    public void onQRCodeResult(boolean isSucceed, String QRCodeURI) {
        Log.e(TAG,"isSucceed " + isSucceed + " QRCodeURI is " + QRCodeURI );
        iv_QRCode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap(QRCodeURI, (int)getResources().getDimension(R.dimen.QRCode_width),(int)getResources().getDimension(R.dimen.QRCode_width)));
    }

    @Override
    public void onUserInfoResult(UserInfoBean userInfoBean) {
        if(userInfoBean != null){
            handler.removeMessages(CheckQRLoginResult);
            UserInfoManager.getInstance().setUserInfoData(userInfoBean);
            MainActivity.startAcivity();
            finish();
        }
    }
}
