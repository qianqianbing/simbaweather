package com.simba.themestore.launch.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class PersonalWallPaperAdapter extends BaseQuickAdapter<String, BaseViewHolder> implements LoadMoreModule {

    public PersonalWallPaperAdapter(int layoutResId, List<String> data) {
        super(layoutResId, data);
    }

    public PersonalWallPaperAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, String s) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_wallpaper), R.mipmap.test_icon);
    }
}
