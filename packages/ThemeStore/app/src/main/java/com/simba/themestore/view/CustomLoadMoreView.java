package com.simba.themestore.view;


import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ResourceUtils;
import com.chad.library.adapter.base.loadmore.BaseLoadMoreView;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public final class CustomLoadMoreView extends BaseLoadMoreView {

    @Override
    public View getRootView(ViewGroup parent) {
        // 整个 LoadMore 布局
        return ResourceUtils.getViewFollowParent(R.layout.view_load_more, parent);
    }


    @Override
    public View getLoadingView(BaseViewHolder holder) {
        // 布局中 “加载中”的View
        return holder.findView(R.id.load_more_loading_view);
    }


    @Override
    public View getLoadComplete(BaseViewHolder holder) {
        // 布局中 “当前一页加载完成”的View
        return holder.findView(R.id.load_more_load_complete_view);
    }


    @Override
    public View getLoadEndView(BaseViewHolder holder) {
        // 布局中 “全部加载结束，没有数据”的View
        return holder.findView(R.id.load_more_load_end_view);
    }


    @Override
    public View getLoadFailView(BaseViewHolder holder) {
        // 布局中 “加载失败”的View
        return holder.findView(R.id.load_more_load_fail_view);
    }
}
