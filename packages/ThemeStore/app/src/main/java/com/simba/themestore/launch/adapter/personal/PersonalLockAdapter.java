package com.simba.themestore.launch.adapter.personal;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.base.dialog.view.SingleCheckBox;
import com.simba.themestore.R;
import com.simba.themestore.model.personal.PersonalLockScreenBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class PersonalLockAdapter extends BasePersonalAdapter<PersonalLockScreenBean> {

    public PersonalLockAdapter(List<PersonalLockScreenBean> data) {
        super(R.layout.item_personal_theme, data);

    }

    public PersonalLockAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, PersonalLockScreenBean personalLockScreenBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme), R.drawable.test_theme);

        if (isEdit()) {//编辑状态
            holder.setVisible(R.id.cb_single, true);
            SingleCheckBox singleCheckBox = holder.getView(R.id.cb_single);
            singleCheckBox.setChecked(personalLockScreenBean.isChecked());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleCheckBox.setChecked(!personalLockScreenBean.isChecked());
                    personalLockScreenBean.setChecked(!personalLockScreenBean.isChecked());
                }
            });
        } else {
            holder.setVisible(R.id.cb_single, false);
        }
    }


}
