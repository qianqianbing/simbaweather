package com.simba.violationenquiry.ui.view.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.simba.base.base.BaseViewHolder;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.utils.ResourceUtils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
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

    public void setBackground(boolean isChecked) {
        btnKey.setBackground(ResourceUtils.getDrawable(isChecked ? R.drawable.icon_key_press : R.drawable.icon_key_normal));
    }

}
