package com.simba.themestore.launch.adapter.theme;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.personal.PersonalThemeBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public class ThemeDetailAdapter extends BaseQuickAdapter<PersonalThemeBean, BaseViewHolder> {

    public ThemeDetailAdapter(List<PersonalThemeBean> data) {
        super(R.layout.item_theme_detail, data);
    }

    public ThemeDetailAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, PersonalThemeBean personalThemeBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme), R.drawable.about_bg);

    }
}
