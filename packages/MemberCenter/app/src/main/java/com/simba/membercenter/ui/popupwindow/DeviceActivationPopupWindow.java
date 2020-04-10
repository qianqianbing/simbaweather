package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;
import com.simba.membercenter.ui.popupwindow.WeChatHelpPopupWindow;

public class DeviceActivationPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "DeviceActivationPopupWindow";
    private ImageView iv_help;

    @Override
    public int getWidth() {
        return 800;
    }

    @Override
    public int getHeight() {
        return 300;
    }

    @Override
    public View setUpView(Context context) {
        Log.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_deviceactivation, null);
        iv_help = view.findViewById(R.id.iv_help);
        iv_help.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_help:
                hidePopupWindow();
                WeChatHelpPopupWindow helpPopupWindow = new WeChatHelpPopupWindow();
                helpPopupWindow.setFatherGlobalPopupWindow(this);
                helpPopupWindow.showPopupWindow(MyApplication.getmApplication().getApplicationContext());
                break;
        }
    }
}