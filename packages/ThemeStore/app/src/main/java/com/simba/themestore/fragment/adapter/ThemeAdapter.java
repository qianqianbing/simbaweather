package com.simba.themestore.fragment.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.R;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.model.theme.ThemeMainListBean;
import com.simba.themestore.utils.img.GlideImageLoader;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class ThemeAdapter extends BaseQuickAdapter<ThemeMainListBean, BaseViewHolder> implements LoadMoreModule {

    public ThemeAdapter(List<ThemeMainListBean> data) {
        super(R.layout.item_fragment_theme, data);
        addChildClickViewIds(R.id.rl_title, R.id.rl_item, R.id.rl_item_bottom);
    }


    @Override
    protected void convert(BaseViewHolder holder, ThemeMainListBean themeBean) {
        List<ThemeDetailBean> data = themeBean.getSkinNameList();
        ThemeDetailBean topBean = null;
        ThemeDetailBean bottomBean = null;
        if (data.size() == 1) {
            topBean = data.get(0);
            GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme), topBean.getCoverurl());
            holder.setText(R.id.tv_theme_name, topBean.getTitle());
            holder.setVisible(R.id.rl_item_bottom, false);
            // TODO: 2020/4/29 状态
        }
        if (data.size() == 2) {

            topBean = data.get(0);
            GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme), topBean.getCoverurl());
            holder.setText(R.id.tv_theme_name, topBean.getTitle());

            bottomBean = data.get(1);
            GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.getView(R.id.iv_theme_bottom), bottomBean.getCoverurl());
            holder.setText(R.id.tv_theme_name_bottom, bottomBean.getTitle());
        }
        holder.setVisible(R.id.rl_item, topBean != null);
        holder.setVisible(R.id.rl_item_bottom, bottomBean != null);

    }
}
