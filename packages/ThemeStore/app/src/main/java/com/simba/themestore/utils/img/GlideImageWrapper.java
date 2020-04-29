package com.simba.themestore.utils.img;

import android.content.Context;
import android.net.Uri;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public interface GlideImageWrapper {

    /**
     * 加载指定大小，并进行放大原图后对有效部分进行裁剪
     *
     * @param context
     * @param imageView 视图
     * @param uri       图片的uri
     */
    void loadImage(Context context, AppCompatImageView imageView, Uri uri);


    /**
     * 从资源文件加载图片并填充
     *
     * @param context
     * @param imageView
     * @param res
     */
    void loadImage(Context context, AppCompatImageView imageView, int res);


    void loadImage(Context context, AppCompatImageView imageView, String imgPath, int resize);

    /**
     * 从文件加载图片
     *
     * @param context
     * @param imageView 视图
     * @param imgPath   图片路径
     */
    void loadCongestedImage(Context context, AppCompatImageView imageView, String imgPath);


    /**
     * 加载网络图片
     *
     * @param context
     * @param imageView 视图
     * @param uri
     */
    void loadImage(Context context, AppCompatImageView imageView, String uri);

}
