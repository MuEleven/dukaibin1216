package com.bawei.day16_demo.utils;

import android.widget.ImageView;

import com.bawei.day16_demo.R;
import com.bawei.day16_demo.app.MyApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtils {

    //加载图片的方法并且设置
    public static void loadImage(String url, ImageView imageView) {
        Glide.with(MyApp.mContext).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .priority(Priority.LOW)
                //开启缓存
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //设置成圆形图片
//                .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                //设置成圆角矩形
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(60)))
                .into(imageView);
    }
}
