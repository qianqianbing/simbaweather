package com.simba.themestore.launch.personal;

import android.view.View;
import android.widget.TextView;

import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.utils.AppUtils;

public class PersonalAboutActivity extends EditBaseActivity {

    private TextView tvVersion;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personal_about;
    }

    @Override
    protected void initView() {
        hideEditButton();
        setTitleName(R.string.about);
        tvVersion = findViewById(R.id.tv_version);
        findViewById(R.id.rl_user_agreement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(UserAgreementActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        String versionInfo = AppUtils.getAppVersionName();
        tvVersion.setText(versionInfo);
    }


}
