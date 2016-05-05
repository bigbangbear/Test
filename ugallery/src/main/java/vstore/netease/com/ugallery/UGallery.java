package vstore.netease.com.ugallery;


import android.content.Context;

import vstore.netease.com.ugallery.activity.ActivitySelectImage;
import vstore.netease.com.ugallery.listener.OnSelectImageResultCallback;

/**
 * @author yuhuibin
 * @date 2016-04-28
 */
public class UGallery {

    public static void selectSingleImage(Context context, OnSelectImageResultCallback callback) {
        ActivitySelectImage.startActivityForSingleImage(context, callback);
    }

    public static void selectMutipleImage(Context context, OnSelectImageResultCallback callback) {
        ActivitySelectImage.startActivityForMutilImage(context, callback);
    }

    
}
