package com.simba.themestore.banner.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;
import com.yinglan.shadowimageview.ShadowImageView;

public class ImageHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = view.findViewById(R.id.iv_banner);
    }
}