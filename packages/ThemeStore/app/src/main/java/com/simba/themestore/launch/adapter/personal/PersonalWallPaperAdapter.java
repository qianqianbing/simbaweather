package com.simba.themestore.launch.adapter.personal;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.base.dialog.view.SingleCheckBox;
import com.simba.themestore.R;
import com.simba.themestore.model.personal.PersonalWallPaperBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class PersonalWallPaperAdapter extends BaseQuickAdapter<PersonalWallPaperBean, BaseViewHolder> implements LoadMoreModule {
    private boolean isEdit;

    public PersonalWallPaperAdapter(int layoutResId, List<PersonalWallPaperBean> data) {
        super(layoutResId, data);
    }

    public PersonalWallPaperAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, PersonalWallPaperBean wallPaperBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_wallpaper), R.drawable.about_bg);

        if (isEdit) {//编辑状态
            holder.setVisible(R.id.cb_single, true);
            SingleCheckBox singleCheckBox = holder.getView(R.id.cb_single);
            singleCheckBox.setChecked(wallPaperBean.isChecked());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    singleCheckBox.setChecked(!wallPaperBean.isChecked());
                    wallPaperBean.setChecked(!wallPaperBean.isChecked());
                }
            });
        } else {
            holder.setVisible(R.id.cb_single, false);
        }

    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }

}
