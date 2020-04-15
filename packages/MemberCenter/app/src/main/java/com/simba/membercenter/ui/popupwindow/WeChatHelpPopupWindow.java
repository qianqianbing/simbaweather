package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.membercenter.MyApplication;
import com.simba.membercenter.QRCodeUtil;
import com.simba.membercenter.R;

public class WeChatHelpPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "DeviceActivationPopupWindow";
    private TextView bt_close;
    private GlobalPopupWindow fatherGlobalPopupWindow ;
    private ImageView iv_QR_help;
    @Override
    public int getWidth() {
        return 888;
    }

    @Override
    public int getHeight() {
        return 494;
    }

    @Override
    public View setUpView(Context context) {
        Log.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_wechat_help, null);
        bt_close = view.findViewById(R.id.tv_close);
        bt_close.setOnClickListener(this);
        iv_QR_help = view.findViewById(R.id.iv_QR_help);
        iv_QR_help.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 184,184));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_close:
                hidePopupWindow();
                if (fatherGlobalPopupWindow != null){
                    fatherGlobalPopupWindow.showPopupWindow(MyApplication.getMyApplication().getApplicationContext());
                }
                break;
        }
    }

    public void setFatherGlobalPopupWindow(GlobalPopupWindow fatherGlobalPopupWindow) {
        this.fatherGlobalPopupWindow = fatherGlobalPopupWindow;
    }
}
