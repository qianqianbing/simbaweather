package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.membercenter.QRCodeUtil;
import com.simba.membercenter.R;

public class UnbindPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "RealNameAuthenticationPopupWindow";

    private ImageView iv_QRCode_unbind;
    private TextView tv_iknow;
    @Override
    public int getWidth() {
        return 1080;
    }

    @Override
    public int getHeight() {
        return 502;
    }

    @Override
    public View setUpView(Context context) {
        Log.i(TAG, "setUp view");
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_unbind, null);

        tv_iknow = view.findViewById(R.id.tv_iknow);
        tv_iknow.setOnClickListener(this);
        iv_QRCode_unbind = view.findViewById(R.id.iv_QRCode_unbind);
        iv_QRCode_unbind.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 184,184));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_iknow:
                hidePopupWindow();
                break;
        }
    }
}
