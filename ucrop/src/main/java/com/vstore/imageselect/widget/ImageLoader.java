package com.vstore.imageselect.widget;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Desction:imageloader抽象类，外部需要实现这个类去加载图片， GalleryFinal尽力减少对第三方库的依赖，所以这么干了
 * Author:pengjianbo
 * Date:15/10/10 下午5:27
 */
public interface ImageLoader extends Serializable {
    void displayImage(Activity activity, String path, GFImageView imageView, Drawable defaultDrawable, int width, int height);
    void clearMemoryCache();
}