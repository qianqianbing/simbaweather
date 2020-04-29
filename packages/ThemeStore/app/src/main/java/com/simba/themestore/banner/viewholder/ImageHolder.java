package com.simba.themestore.banner.viewholder;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;


public class ImageHolder extends RecyclerView.ViewHolder {
    public AppCompatImageView imageView;

    public ImageHolder(@NonNull View view) {
        super(view);
        this.imageView = view.findViewById(R.id.iv_banner);
    }
}