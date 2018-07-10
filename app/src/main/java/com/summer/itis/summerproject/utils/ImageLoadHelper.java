package com.summer.itis.summerproject.utils;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.summer.itis.summerproject.R;

//КЛАСС ДЛЯ РАБОТЫ С ФОТКАМИ.НЕ ИСПОЛЬЗОВАТЬ, ПЕРЕЖИТОК ПРОШЛОГО,ЮЗАТЬ GLIDE
public final class ImageLoadHelper {

    private ImageLoadHelper() {
    }

    public static void loadPicture(@NonNull ImageView imageView, @NonNull String url) {
        Picasso.with(imageView.getContext())
                .load(url)
               /* .placeholder(R.mipmap.ic_marvel_launcher)
                .error(R.mipmap.ic_marvel_launcher)*/
                .noFade()
                .into(imageView);
    }

    public static void loadPictureByDrawable(@NonNull ImageView imageView, @DrawableRes int drawable) {
        Picasso.with(imageView.getContext())
                .load(drawable)
                .resize(1280, 720)
                .noFade()
                .into(imageView);
    }

    public static void loadPictureByDrawableDefault(@NonNull ImageView imageView, @DrawableRes int drawable) {
        Picasso.with(imageView.getContext())
                .load(drawable)
                .noFade()
                .into(imageView);
    }
}
