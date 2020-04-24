package com.simba.themestore.fragment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.WallPaperBean;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class WallPaperAdapter extends BaseQuickAdapter<WallPaperBean, BaseViewHolder> implements LoadMoreModule {
    public WallPaperAdapter(int layoutResId, List<WallPaperBean> data) {
        super(layoutResId, data);
        addChildClickViewIds(R.id.rl_title, R.id.rl_item, R.id.rl_item_bottom);
    }

    public WallPaperAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder holder, WallPaperBean themeBean) {

    }
}
