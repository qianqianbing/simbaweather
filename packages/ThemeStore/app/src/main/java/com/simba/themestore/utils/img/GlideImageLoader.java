package com.simba.themestore.utils.img;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.net.URI;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class GlideImageLoader implements GlideImageWrapper {
    private static final GlideImageLoader ourInstance = new GlideImageLoader();

    public static GlideImageLoader getInstance() {
        return ourInstance;
    }

    private GlideImageLoader() {
    }

    @Override
    public void loadImage(Context context, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, int res) {
        RequestOptions options = new RequestOptions();
        Glide.with(context)
                .asBitmap()
                .load(res)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, String imgPath, int resize) {
        RequestOptions options = new RequestOptions().override(resize, resize).centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(new File(imgPath))
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadCongestedImage(Context context, ImageView imageView, String imgPath) {
        Glide.with(context)
                .asBitmap()
                .load(new File(imgPath))
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, ImageView imageView, URI uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .into(imageView);
    }

    public void loadResImage(Context context, ImageView imageView, int res) {
        Glide.with(context).load(res).into(imageView);
    }

}
