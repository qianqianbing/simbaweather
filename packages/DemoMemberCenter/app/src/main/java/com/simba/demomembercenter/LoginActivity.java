package com.simba.demomembercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.simba.base.utils.QRCodeUtil;

public class LoginActivity extends Activity implements HttpRequest.QRCodeCallback, HttpRequest.UserInfoCallback, View.OnClickListener {
    private static String TAG = "MainActivity";
    private ImageView iv_QRCode , iv_cancel ;
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
        iv_cancel = findViewById(R.id.iv_cancel);
        iv_cancel.setOnClickListener(this);
        //iv_QRCode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.baidu.com", (int)getResources().getDimension(R.dimen.QRCode_width),(int)getResources().getDimension(R.dimen.QRCode_width)));
        Log.e(TAG,"QRCode request begin " );
        HttpRequest.getIntance().requestLoginQRCode(this, this);
        handler.sendEmptyMessageDelayed(CheckQRLoginResult, 2000);
    }

    public void requestUserInfo(){
        HttpRequest.getIntance().requestUserInfo(this, this);
        handler.sendEmptyMessageDelayed(CheckQRLoginResult, 2000);
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
        ViewGroup.LayoutParams para = iv_QRCode.getLayoutParams();
        int imageWidth = para.width;
        Log.e(TAG,"QRCode request begin middle " + isSucceed  + " width is ：" + (int)getResources().getDimension(R.dimen.QRCode_width)  + " " + imageWidth + " QRCodeURI is " + QRCodeURI);
        iv_QRCode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap(QRCodeURI, (int)getResources().getDimension(R.dimen.QRCode_width),(int)getResources().getDimension(R.dimen.QRCode_width)));
        Log.e(TAG,"QRCode request end " );
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                finish();
                break;
        }
    }
}
