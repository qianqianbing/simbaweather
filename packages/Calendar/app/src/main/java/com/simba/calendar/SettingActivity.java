package com.simba.calendar;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.blankj.utilcode.util.SPStaticUtils;
import com.simba.base.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    public static final String KEY_SETTING_HOLIDAY_PUSH = "setting_holiday_push";
    public static final String KEY_SETTING_ALMANAC = "setting_almanac";

    @BindView(R.id.tv_setting_back)
    TextView mTvSettingBack;
    @BindView(R.id.sw_setting_holiday_push)
    Switch mSwSettingHolidayPush;
    @BindView(R.id.sw_setting_almanac)
    Switch mSwSettingAlmanac;
    @BindView(R.id.tv_setting_version)
    TextView mTvSettingVersion;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {
    }

    @Override
    protected void initData() {
        mSwSettingHolidayPush.setChecked(SPStaticUtils.getBoolean(SettingActivity.KEY_SETTING_HOLIDAY_PUSH, true));
        mSwSettingAlmanac.setChecked(SPStaticUtils.getBoolean(SettingActivity.KEY_SETTING_ALMANAC, true));
        mTvSettingVersion.setText(getVersionCode());
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
                SPStaticUtils.put(KEY_SETTING_HOLIDAY_PUSH, isChecked);
            }
        });

        mSwSettingAlmanac.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPStaticUtils.put(KEY_SETTING_ALMANAC, isChecked);
                AppWidget.updateLunarView(SettingActivity.this, isChecked);
            }
        });
    }

    private String getVersionCode() {
        try {
            // 包管理器 可以获取清单文件信息
            PackageManager packageManager = getPackageManager();
            // 获取包信息
            // 参1 包名 参2 获取额外信息的flag 不需要的话 写0
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
