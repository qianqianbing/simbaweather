package com.simba.themestore.banner.adapter;

import android.graphics.drawable.Drawable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.simba.themestore.R;
import com.simba.themestore.banner.viewholder.ImageHolder;
import com.simba.themestore.model.DataBean;
import com.youth.banner.util.BannerUtils;

import java.util.List;

/**
 * 自定义布局，图片
 */
public class BannerImageAdapter extends com.youth.banner.adapter.BannerAdapter<DataBean, ImageHolder> {

    public BannerImageAdapter(List<DataBean> mDatas) {
        //设置数据，也可以调用banner提供的方法,或者自己在adapter中实现
        super(mDatas);
    }

    //更新数据
    public void updateData(List<DataBean> data) {
        //这里的代码自己发挥，比如如下的写法等等
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }


    //创建ViewHolder，可以用viewType这个字段来区分不同的ViewHolder
    @Override
    public ImageHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(BannerUtils.getView(parent, R.layout.banner_layout));
    }

    @Override
    public void onBindView(ImageHolder holder, DataBean data, int position, int size) {
        //通过图片加载器实现圆角，你也可以自己使用圆角的imageview，实现圆角的方法很多，自己尝试哈
        Glide.with(holder.itemView)
                .load(data.imageRes)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
                .into(new CustomTarget<Drawable>() {

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        holder.imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Remove the Drawable provided in onResourceReady from any Views and ensure
                        // no references to it remain.
                    }
                });


    }


}
