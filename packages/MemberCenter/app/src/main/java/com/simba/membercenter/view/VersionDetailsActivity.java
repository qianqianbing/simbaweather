package com.simba.membercenter.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.simba.base.base.BaseActivity;
import com.simba.membercenter.MyApplication;
import com.simba.base.utils.QRCodeUtil;
import com.simba.membercenter.R;

public class VersionDetailsActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mIvQrcode, mIvUseragreement;
    private RelativeLayout mRlBack;

    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), VersionDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_versiondetail;
    }

    @Override
    protected void initView() {
        mRlBack = findViewById(R.id.rl_back);
        mRlBack.setOnClickListener(this);
        mIvUseragreement = findViewById(R.id.iv_useragreement);
        mIvUseragreement.setOnClickListener(this);
        mIvQrcode = findViewById(R.id.iv_qrcode);
        mIvQrcode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 233,233));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.iv_useragreement:
                UserAgreementActivity.startAcivity();
                break;
        }
    }
}
