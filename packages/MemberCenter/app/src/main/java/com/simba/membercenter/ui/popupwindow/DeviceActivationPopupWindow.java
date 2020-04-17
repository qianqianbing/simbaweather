package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.QRCodeUtil;
import com.simba.membercenter.R;

public class DeviceActivationPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "DeviceActivationPopupWindow";
    private ImageView iv_help;
    private ImageView iv_QRCode_activation;
    @Override
    public int getWidth() {
        return 1220;
    }

    @Override
    public int getHeight() {
        return 532;
    }

    @Override
    public View setUpView(Context context) {
        Log.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_deviceactivation, null);
        iv_help = view.findViewById(R.id.iv_help);
        iv_help.setOnClickListener(this);

        iv_QRCode_activation = view.findViewById(R.id.iv_QRCode_activation);
        iv_QRCode_activation.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 184,184));
        //测试代码，5秒后消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hidePopupWindow();
            }
        },5000);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_help:
                hidePopupWindow();

                WeChatHelpPopupWindow weChatHelpPopupWindow = new WeChatHelpPopupWindow();
                weChatHelpPopupWindow.setFatherGlobalPopupWindow(this);
                weChatHelpPopupWindow.showPopupWindow(MyApplication.getMyApplication().getApplicationContext());
                break;
        }
    }
}
