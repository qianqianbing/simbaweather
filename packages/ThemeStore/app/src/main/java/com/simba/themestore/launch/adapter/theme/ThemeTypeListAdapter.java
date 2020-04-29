package com.simba.themestore.launch.adapter.theme;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.utils.img.GlideImageLoader;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class ThemeTypeListAdapter extends BaseQuickAdapter<ThemeDetailBean, BaseViewHolder> implements LoadMoreModule {


    public ThemeTypeListAdapter() {
        super(R.layout.item_personalwallpaper, null);
    }


    @Override
    protected void convert(BaseViewHolder holder, ThemeDetailBean themeDetailBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_wallpaper), themeDetailBean.getCoverurl());
        holder.setText(R.id.tv_name, themeDetailBean.getTitle());

    }


}
