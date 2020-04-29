package com.simba.themestore.utils.img;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.simba.themestore.R;

import java.io.File;

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
    public void loadImage(Context context, AppCompatImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(options)
                .placeholder(R.drawable.icon_load_default)
                .error(R.drawable.icon_load_default)
                .fallback(R.drawable.icon_load_default)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, AppCompatImageView imageView, int res) {
        RequestOptions options = new RequestOptions();
        Glide.with(context)
                .asBitmap()
                .load(res)
                .apply(options).placeholder(R.drawable.icon_load_default)
                .error(R.drawable.icon_load_default)
                .fallback(R.drawable.icon_load_default)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, AppCompatImageView imageView, String imgPath, int resize) {
        RequestOptions options = new RequestOptions().override(resize, resize).centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(new File(imgPath))
                .apply(options).placeholder(R.drawable.icon_load_default)
                .error(R.drawable.icon_load_default)
                .fallback(R.drawable.icon_load_default)
                .into(imageView);
    }

    @Override
    public void loadCongestedImage(Context context, AppCompatImageView imageView, String imgPath) {
        Glide.with(context)
                .asBitmap()
                .load(new File(imgPath)).placeholder(R.drawable.icon_load_default)
                .error(R.drawable.icon_load_default)
                .fallback(R.drawable.icon_load_default)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, AppCompatImageView imageView, String uri) {
        Glide.with(context)
                .asBitmap()
                .load(uri).placeholder(R.drawable.icon_load_default)
                .error(R.drawable.icon_load_default)
                .fallback(R.drawable.icon_load_default)

                .into(imageView);
    }

    public void loadResImage(Context context, AppCompatImageView imageView, int res) {
        Glide.with(context).load(res).into(imageView);
    }

}
