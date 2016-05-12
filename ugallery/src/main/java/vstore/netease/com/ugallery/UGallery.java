package vstore.netease.com.ugallery;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import vstore.netease.com.ugallery.activity.ActivityCropImage;
import vstore.netease.com.ugallery.activity.ActivitySelectImage;
import vstore.netease.com.ugallery.activity.ActivityTakePhotos;

/**
 * @author yuhuibin
 * @date 2016-04-28
 */
public class UGallery {
    public static final int TAKE_PHOTO = 1001;
    public static final int SELECT_PHOTO = 1101;
    //public static int SELECT_MUTIL_PHOTO = 1102;
    public static final int CROP_IMAGE = 1200;

    public static void selectSingleImage(Context context ) {
        ActivitySelectImage.startActivityForSingleImage(context, false);
    }

    public static void selectSingleImageCrop(Context context ) {
        ActivitySelectImage.startActivityForSingleImage(context, true);
    }

    public static void selectMutipleImage(Context context ) {
        ActivitySelectImage.startActivityForMutilImage(context);
    }

    public static void cropImage(Context context, Uri uri) {
        ActivityCropImage.startActivity(context, uri);
    }

    public static void takePhoto(Context context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, false );
    }

    public static void takePhotoCrop(Context context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, true );
    }

    public static String PATH = "PATH";
    public static Uri getData(Intent intent){
        String path = intent.getStringExtra(PATH);
        Uri uri = Uri.parse("file://"+path);
        return uri;
    }
}
