package vstore.netease.com.ugallery;


import android.content.Context;
import android.net.Uri;

import vstore.netease.com.ugallery.activity.ActivityCropImage;
import vstore.netease.com.ugallery.activity.ActivitySelectImage;
import vstore.netease.com.ugallery.activity.ActivityTakePhotos;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;
import vstore.netease.com.ugallery.listener.OnGalleryImagesResultCallback;

/**
 * @author yuhuibin
 * @date 2016-04-28
 */
public class UGallery {

    public static int TAKE_PHOTO_SUCCESS = 1001;
    public static int TAKE_PHOTO_FAIL = 1002;
    public static int SELECT_SINGLE_PHOTO_SUCCESS = 1101;
    public static int SELECT_SINGLE_PHOTO_FAIL = 1102;
    public static int CROP_IMAGE = 1200;

    public static void selectSingleImage(Context context, OnGalleryImageResultCallback callback) {
        ActivitySelectImage.startActivityForSingleImage(context, callback);
    }

    public static void cropImage(Context context, OnGalleryImageResultCallback callback, Uri uri) {
        ActivityCropImage.startActivity(context, callback, uri);
    }

    public static void selectMutipleImage(Context context, OnGalleryImagesResultCallback callback) {
        ActivitySelectImage.startActivityForMutilImage(context, callback);
    }

    public static void takePhoto(Context context, OnGalleryImageResultCallback callback) {
        ActivityTakePhotos.startActivityForSingleImage(context, callback );
    }


    
}
