package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.simba.base.base.BaseActivity;
import com.simba.membercenter.R;

public class JumpToMemberCenterActivity extends BaseActivity implements  View.OnClickListener{

    private static String TAG = "JumpToMemberCenterActivity";
    private Button mBtJump;
    private ImageView mIvCancel;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_jumptomembercenter;
    }

    @Override
    protected void initView() {
        mIvCancel = findViewById(R.id.iv_cancel);
        mBtJump = findViewById(R.id.bt_jump);

        mIvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "finish");
                Intent intent = new Intent();
                // 获取用户计算后的结果

                intent.putExtra("three", 65); //将计算的值回传回去
                // 通过intent对象返回结果，必须要调用一个setResult方法，
                // setResult(888, data);第一个参数表示结果返回码，一般只要大于1就可以
                setResult(2, intent);
                finish(); //结束当前的activity的生命周期

            }
        });
        mBtJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "jump");
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                break;
        }
    }
}
