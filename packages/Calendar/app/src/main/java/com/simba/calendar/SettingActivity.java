package com.simba.calendar;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.simba.base.base.BaseActivity;

public class SettingActivity extends BaseActivity {

    private TextView mTvSettingBack;
    private Switch mSwSettingHolidayPush;
    private Switch mSwSettingAlmanac;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
        mTvSettingBack = (TextView) findViewById(R.id.tv_setting_back);
        mSwSettingHolidayPush = (Switch) findViewById(R.id.sw_setting_holiday_push);
        mSwSettingAlmanac = (Switch) findViewById(R.id.sw_setting_almanac);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        mTvSettingBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mSwSettingHolidayPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        mSwSettingAlmanac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

    }
}
