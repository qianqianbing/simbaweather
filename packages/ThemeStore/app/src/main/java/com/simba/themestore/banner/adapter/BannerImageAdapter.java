package com.simba.themestore.banner.adapter;

import android.view.ViewGroup;

import com.simba.themestore.R;
import com.simba.themestore.banner.viewholder.ImageHolder;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.utils.img.GlideImageLoader;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class BannerImageAdapter extends com.youth.banner.adapter.BannerAdapter<ThemeDetailBean, ImageHolder> {

    public BannerImageAdapter() {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(null);
    }

    //更新数据
    public void refresh(List<ThemeDetailBean> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_layout));
    }

    @Override
    public void onBindView(ImageHolder holder, ThemeDetailBean data, int position, int size) {
        GlideImageLoader.getInstance().loadImage(holder.itemView.getContext(), holder.imageView, data.getCoverurl());
    }


}
