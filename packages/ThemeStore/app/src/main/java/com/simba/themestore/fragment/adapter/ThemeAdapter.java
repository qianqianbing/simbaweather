package com.simba.themestore.fragment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.ThemeBean;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class ThemeAdapter extends BaseQuickAdapter<ThemeBean, BaseViewHolder> implements LoadMoreModule {

    public ThemeAdapter(List<ThemeBean> data) {
        super(R.layout.item_fragment_theme, data);
        addChildClickViewIds(R.id.rl_title,R.id.rl_item,R.id.rl_item_bottom);
    }


    @Override
    protected void convert(BaseViewHolder holder, ThemeBean themeBean) {


    }
}
