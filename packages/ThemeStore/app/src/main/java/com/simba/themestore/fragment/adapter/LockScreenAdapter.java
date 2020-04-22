package com.simba.themestore.fragment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.LockScreenBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class LockScreenAdapter extends BaseQuickAdapter<LockScreenBean, BaseViewHolder> implements LoadMoreModule {


    public LockScreenAdapter(int layoutResId, List<LockScreenBean> data) {
        super(layoutResId, data);
    }

    public LockScreenAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, LockScreenBean wallPaperBean) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_lock_screen), R.drawable.about_bg);


    }

}
