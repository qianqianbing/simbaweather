package com.simba.membercenter.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.simba.membercenter.MyApplication;
import com.simba.membercenter.QRCodeUtil;
import com.simba.membercenter.R;

public class VersionDetailsActivity extends Activity implements View.OnClickListener{
    private ImageView iv_qrcode;
    private RelativeLayout rl_back;
    public static void startAcivity(){
        Intent intent = new Intent(MyApplication.getMyApplication().getApplicationContext(), VersionDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getMyApplication().getApplicationContext().startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_versiondetail);
        setUpView();
        super.onCreate(savedInstanceState);
    }

    private void setUpView(){
        rl_back = findViewById(R.id.rl_back);
        rl_back.setOnClickListener(this);
        iv_qrcode = findViewById(R.id.iv_qrcode);
        iv_qrcode.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 233,233));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }
}
