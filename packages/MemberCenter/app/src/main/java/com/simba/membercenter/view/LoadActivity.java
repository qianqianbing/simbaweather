package com.simba.membercenter.view;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.simba.base.DeviceAccountManager.DeviceAccountManager;
import com.simba.membercenter.R;

public class LoadActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        if(DeviceAccountManager.getInstance(this).getLoginState()){

            MainActivity.startAcivity();
        }else {
            LoginActivity.startAcivity();
        }
        finish();
    }
}
