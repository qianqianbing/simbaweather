package com.simba.demomembercenter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.simba.base.dialog.DialogUtil;


public class MainActivity extends Activity implements View.OnClickListener{

    private static String TAG = "MainActivity";

    private TextView tv_nickname, tv_level, tv_id, tv_cartype;
    private ProgressBar pb_level;
    private ImageView iv_switch_account, iv_userimage;

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
        if(displayMetrics.heightPixels > 440){
            setContentView(R.layout.activity_main_480);
        }else {
            setContentView(R.layout.activity_main);
        }
        tv_nickname = findViewById(R.id.tv_nickname);
        iv_userimage = findViewById(R.id.iv_userimage);
        tv_level = findViewById(R.id.tv_level);
        pb_level = findViewById(R.id.pb_level);
        tv_id = findViewById(R.id.tv_id);
        tv_cartype = findViewById(R.id.tv_cartype);

        iv_switch_account = findViewById(R.id.iv_switch_account);
        iv_switch_account.setOnClickListener(this);
        UserInfoBean userInfoBean = UserInfoManager.getInstance().getUserInfoData();
        if(userInfoBean  == null){
            LoginActivity.startAcivity();
            finish();
        }else {
            tv_nickname.setText(userInfoBean.getNickname());
            //设置用户头像
            RequestOptions options = new RequestOptions()
                    .placeholder(R.drawable.icon_wechat)//图片加载出来前，显示的图片
                    .fallback(R.drawable.icon_wechat) //url为空的时候,显示的图片
                    .error(R.drawable.icon_wechat);//图片加载失败后，显示的图片
            Glide.with(this).load(userInfoBean.getHeadimgurl()).apply(options).into(iv_userimage);
            String strLevel = "成长等级 LV" + userInfoBean.getLevel() + " ("+ userInfoBean.getCurrentLevelPoint() + " /" + userInfoBean.getNextLevelPoint() +  ")";
            tv_level.setText(strLevel);

            int curLevel = Integer.parseInt( userInfoBean.getCurrentLevelPoint());
            int nextLevel = Integer.parseInt( userInfoBean.getNextLevelPoint());
            pb_level.setMax(nextLevel);
            pb_level.setProgress(curLevel);

            String userName = userInfoBean.getId();
            String id = "ID: " + userName.substring(0, 3) + "****" + userName.substring(7, userName.length());
            tv_id.setText(id);

            tv_cartype.setText("设备： " + userInfoBean.getVehicleType());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_switch_account:
                DialogUtil.build(this)
                    .content("确认退出账号")
                    .contentTextSize(24)
                    .positiveText("确定")
                    .positiveTextSize(22)
                    .negativeText("取消")
                    .negativeTextSize(22)
                    .negativeTextColor(getResources().getColor(R.color.base_white_trans_4d))
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
