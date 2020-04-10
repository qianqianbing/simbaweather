package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.simba.base.base.BaseActivity;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static String TAG = "MainActivity";
    private Button bt_message;

    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getmApplication().getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getmApplication().getApplicationContext().startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        bt_message = findViewById(R.id.bt_message);
        bt_message.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_message:
                Log.e(TAG, "  bt_message click " );
                MessageActivity.startAcivity();
                break;
        }
    }
}
