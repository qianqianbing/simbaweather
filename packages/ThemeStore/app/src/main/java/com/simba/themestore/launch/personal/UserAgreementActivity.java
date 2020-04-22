package com.simba.themestore.launch.personal;

import android.view.View;

import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class UserAgreementActivity extends MyBaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_useragreement;
    }

    @Override
    protected void initView() {
        findViewById(R.id.tv_title).setVisibility(View.VISIBLE);
        findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {

    }
}
