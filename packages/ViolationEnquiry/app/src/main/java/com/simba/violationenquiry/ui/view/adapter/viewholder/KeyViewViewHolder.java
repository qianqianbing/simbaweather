package com.simba.violationenquiry.ui.view.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ResourceUtils;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.base.BaseViewHolder;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc : 键盘的ViewHolder
 */
public class KeyViewViewHolder extends BaseViewHolder<String> {
    private TextView btnKey;

    public KeyViewViewHolder(ViewGroup parent, int resId) {
        super(parent, resId);
        btnKey = getView(R.id.tv_key);
    }

    @Override
    public void setData(final String data) {
        btnKey.setText(data);
    }

    /**
     * 背景
     *
     * @param isChecked
     */
    public void setBackground(boolean isChecked) {
        btnKey.setBackground(ResourceUtils.getDrawable(isChecked ? R.drawable.icon_key_press : R.drawable.icon_key_normal));
    }

}
