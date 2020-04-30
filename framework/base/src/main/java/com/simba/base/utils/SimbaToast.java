package com.simba.base.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.simba.base.R;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/29 9:19
 * 描    述：辛巴风格吐司封装类
 * 修订历史：
 * ================================================
 */
public class SimbaToast {

    /**
     * 提示类型吐司
     *
     * @param info
     * @return
     */
    public static View showInfo(Context context, String info) {
        return showInfo(context, -1, info);
    }

    /**
     * 成功类型吐司
     *
     * @param info
     * @return
     */
    public static View showSucceedInfo(Context context, String info) {
        return showInfo(context, 1, info);
    }

    /**
     * 错误类型吐司
     *
     * @param errInfo
     * @return
     */
    public static View showFailedInfo(Context context, String errInfo) {
        return showInfo(context, 0, errInfo);
    }

    private static View showInfo(Context context, int type, String info) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_simba_toast_layout, null);
        TextView base_simba_toast_tv_text = view.findViewById(R.id.base_simba_toast_tv_text);

        if (type == -1) {
            view.findViewById(R.id.base_simba_toast_ll_root).setBackgroundResource(R.drawable.base_toast_bg_two);
            base_simba_toast_tv_text.setPadding(SizeUtils.dp2px(40), SizeUtils.dp2px(15), SizeUtils.dp2px(40), SizeUtils.dp2px(15));
        } else {
            view.findViewById(R.id.base_simba_toast_ll_root).setBackgroundResource(R.drawable.base_toast_bg);

            ImageView base_simba_toast_iv_view = view.findViewById(R.id.base_simba_toast_iv_view);
            if (type == 1) {
                base_simba_toast_iv_view.setImageResource(R.drawable.base_toast_succeed_icon);
            } else if (type == 0) {
                base_simba_toast_iv_view.setImageResource(R.drawable.base_toast_failed_icon);
            }
            base_simba_toast_iv_view.setPadding(0, SizeUtils.dp2px(50), 0, 0);
            base_simba_toast_iv_view.setVisibility(View.VISIBLE);

            base_simba_toast_tv_text.setPadding(SizeUtils.dp2px(40), SizeUtils.dp2px(32), SizeUtils.dp2px(40), SizeUtils.dp2px(50));
        }

        base_simba_toast_tv_text.setText(info);
        ToastUtils.setGravity(Gravity.CENTER, 0, 0);
        return ToastUtils.showCustomLong(view);
    }

}
