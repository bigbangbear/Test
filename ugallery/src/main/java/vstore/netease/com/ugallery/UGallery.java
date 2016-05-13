package vstore.netease.com.ugallery;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import vstore.netease.com.ugallery.activity.ActivityCropImage;
import vstore.netease.com.ugallery.activity.ActivitySelectImage;
import vstore.netease.com.ugallery.activity.ActivityTakePhotos;

/**
 * 发起Gallery后，结果在 onActivityResult() 中返回，调用{@link UGallery#getData(Intent)}获得Uri
 * @author yuhuibin
 * @date 2016-04-28
 */
public class UGallery {
    public static final int TAKE_PHOTO = 1001;
    public static final int SELECT_PHOTO = 1101;
    public static final int SELECT_MUTIL_PHOTO = 1102;
    public static final int CROP_IMAGE = 1200;

    public static String PATH = "PATH";

    /**
     * 选择单张图像
     * @param context
     */
    public static void selectSingleImage(Context context ) {
        ActivitySelectImage.startActivityForSingleImage(context, false);
    }

    /**
     * 选择单张图像并裁剪
     * @param context
     */
    public static void selectSingleImageCrop(Context context ) {
        ActivitySelectImage.startActivityForSingleImage(context, true);
    }

    /**
     * 选择多张图像
     * @param context
     */
    public static void selectMutipleImage(Context context ) {
        ActivitySelectImage.startActivityForMutilImage(context);
    }

    /**
     * 裁剪图像
     * @param context
     * @param uri
     */
    public static void cropImage(Context context, Uri uri) {
        ActivityCropImage.startActivity(context, uri);
    }

    /**
     * 拍照
     * @param context
     */
    public static void takePhoto(Context context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, false );
    }

    /**
     * 拍照并裁剪
     * @param context
     */
    public static void takePhotoCrop(Context context) {
        ActivityTakePhotos.startActivityForTakePhoto(context, true );
    }

    /**
     * 对于onActivityResult中返回结果，从intent(data)中解析数据
     * @param data
     * @return
     */
    public static Uri getData(Intent data){
        String path = data.getStringExtra(PATH);
        Uri uri = Uri.parse("file://"+path);
        return uri;
    }
}
