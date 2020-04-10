package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.R;

public class WeChatHelpPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "DeviceActivationPopupWindow";
    private Button bt_close;
    private GlobalPopupWindow fatherGlobalPopupWindow ;

    @Override
    public int getWidth() {
        return 600;
    }

    @Override
    public int getHeight() {
        return 300;
    }

    @Override
    public View setUpView(Context context) {
        Log.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wechat_help, null);
        bt_close = view.findViewById(R.id.bt_close);
        bt_close.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_close:
                hidePopupWindow();
                if (fatherGlobalPopupWindow != null){
                    fatherGlobalPopupWindow.showPopupWindow(MyApplication.getmApplication().getApplicationContext());
                }
                break;
        }
    }

    public void setFatherGlobalPopupWindow(GlobalPopupWindow fatherGlobalPopupWindow) {
        this.fatherGlobalPopupWindow = fatherGlobalPopupWindow;
    }
}
