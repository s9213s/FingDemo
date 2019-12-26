package com.hfy.fingdemo.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.zhihu.matisse.engine.ImageEngine;

/**
 * @author Marcus
 * @package krt.wid.tour_gz.utils
 * @description
 * @time 2017/11/25
 */

public class MGlideEngine implements ImageEngine {
    @Override
    public void loadThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().placeholder(drawable).override(i, i).centerCrop();
        Glide.with(context)
                .asBitmap()  // some .jpeg files are actually gif
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadGifThumbnail(Context context, int i, Drawable drawable, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().placeholder(drawable).override(i, i).centerCrop();
        Glide.with(context)
                .asBitmap()
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadImage(Context context, int i, int i1, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().override(i, i1).priority(Priority.HIGH);
        Glide.with(context)
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public void loadGifImage(Context context, int i, int i1, ImageView imageView, Uri uri) {
        RequestOptions options = new RequestOptions().override(i, i1).priority(Priority.HIGH);
        Glide.with(context)
                .asGif()
                .load(uri)
                .apply(options)
                .into(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }
//    @Override
//    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
//        RequestOptions options = new RequestOptions().placeholder(placeholder).override(resize, resize).centerCrop();
//        Glide.with(context)
//                .asBitmap()  // some .jpeg files are actually gif
//                .load(uri)
//                .apply(options)
//                .into(imageView);
//    }
//
//    @Override
//    public void loadAnimatedGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
//        RequestOptions options = new RequestOptions().placeholder(placeholder).override(resize, resize).centerCrop();
//        Glide.with(context)
//                .asBitmap()
//                .load(uri)
//                .apply(options)
//                .into(imageView);
//    }
//
//
//    @Override
//    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
//        RequestOptions options = new RequestOptions().override(resizeX, resizeY).priority(Priority.HIGH);
//        Glide.with(context)
//                .load(uri)
//                .apply(options)
//                .into(imageView);
//    }
//
//    @Override
//    public void loadAnimatedGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
//        RequestOptions options = new RequestOptions().override(resizeX, resizeY).priority(Priority.HIGH);
//        Glide.with(context)
//                .asGif()
//                .load(uri)
//                .apply(options)
//                .into(imageView);
//    }
//
//
//    @Override
//    public boolean supportAnimatedGif() {
//        return true;
//    }
}
