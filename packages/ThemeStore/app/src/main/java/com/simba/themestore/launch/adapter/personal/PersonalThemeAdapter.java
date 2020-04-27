package com.simba.themestore.launch.adapter.personal;

import android.view.View;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.base.dialog.view.SingleCheckBox;
import com.simba.themestore.R;
import com.simba.themestore.model.personal.PersonalThemeBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class PersonalThemeAdapter extends BasePersonalAdapter<PersonalThemeBean> {


    public PersonalThemeAdapter(List<PersonalThemeBean> data) {
        super(R.layout.item_personal_theme, data);

    }

    @Override
    protected void convert(BaseViewHolder holder, PersonalThemeBean personalThemeBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme), R.drawable.about_bg);

        if (isEdit()) {//编辑状态
            holder.setVisible(R.id.cb_single, true);
            SingleCheckBox singleCheckBox = holder.getView(R.id.cb_single);
            singleCheckBox.setChecked(personalThemeBean.isChecked());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleCheckBox.setChecked(!personalThemeBean.isChecked());
                    personalThemeBean.setChecked(!personalThemeBean.isChecked());
                }
            });
        } else {
            holder.setVisible(R.id.cb_single, false);
        }
    }


}
