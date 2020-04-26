package com.simba.membercenter.view;

import android.content.Intent;
import android.webkit.WebView;

import com.simba.base.base.BaseActivity;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;

import static com.simba.base.network.SimbaUrl.MENBERCENTER_USERAGGREMENT;

public class UserAgreementActivity extends BaseActivity {

    private WebView webView;
    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), UserAgreementActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_useragreement;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.wv_useragreement);
    }

    @Override
    protected void initData() {
        webView.loadUrl(MENBERCENTER_USERAGGREMENT);
    }
}
