package com.simba.themestore.launch;

import android.view.View;

import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;

public class NotLoggedActivity extends MyBaseActivity {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_not_logged;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    public void start(View view) {
        startActivity(MainActivity.class);
    }
}
