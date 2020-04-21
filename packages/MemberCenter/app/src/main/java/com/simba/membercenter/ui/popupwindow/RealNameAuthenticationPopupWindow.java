package com.simba.membercenter.ui.popupwindow;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.base.UI.Popupwindow.GlobalPopupWindow;
import com.simba.base.utils.QRCodeUtil;
import com.simba.membercenter.R;

public class RealNameAuthenticationPopupWindow extends GlobalPopupWindow implements View.OnClickListener {
    private static String TAG = "RealNameAuthenticationPopupWindow";

    private ImageView iv_QRCode_real;
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
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_realnameauthentication, null);

        tv_iknow = view.findViewById(R.id.tv_iknow);
        tv_iknow.setOnClickListener(this);
        iv_QRCode_real = view.findViewById(R.id.iv_QRCode_real);
        iv_QRCode_real.setImageBitmap(QRCodeUtil.createDefaultCodeBitmap("www.simbalink.cn", 184,184));
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
