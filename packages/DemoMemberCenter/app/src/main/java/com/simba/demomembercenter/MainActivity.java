package com.simba.demomembercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.dialog.DialogUtil;


public class MainActivity extends Activity implements View.OnClickListener{
    private static String TAG = "MainActivity";
    private TextView tv_nickname;
    private ImageView iv_switch_account;

    public static void startAcivity() {
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Log.e(TAG, " Density is "+displayMetrics.density+" densityDpi is "+displayMetrics.densityDpi+" height: "+displayMetrics.heightPixels+
                " width: "+displayMetrics.widthPixels);
      //  if(displayMetrics.heightPixels > 350){
            //setContentView(R.layout.activity_main_400);
     //   }else {
            setContentView(R.layout.activity_main);
     //   }
        tv_nickname = findViewById(R.id.tv_nickname);
        iv_switch_account  = findViewById(R.id.iv_switch_account);
        iv_switch_account.setOnClickListener(this);
        UserInfoBean userInfoBean = UserInfoManager.getInstance().getUserInfoData();
        if(userInfoBean  == null){
            LoginActivity.startAcivity();
            finish();
        }else {
            tv_nickname.setText(userInfoBean.getNickname());

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_switch_account:
                DialogUtil.build(this)
                    .content("是否退出账号")
                    .positiveText("确定")
                    .negativeText("取消")
                        .onPositive(new DialogUtil.SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                Log.e(TAG, "Positive click");
                                UserInfoManager.getInstance().setUserInfoData(null);
                                LoginActivity.startAcivity();
                                finish();
                            }
                        })
                    .show();
                break;
        }
    }
}
