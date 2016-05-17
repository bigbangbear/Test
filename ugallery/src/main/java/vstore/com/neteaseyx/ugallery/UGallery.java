package vstore.com.neteaseyx.ugallery;


import android.content.Context;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

import vstore.com.neteaseyx.ugallery.activity.ActivityCropImage;
import vstore.com.neteaseyx.ugallery.activity.ActivitySelectImage;
import vstore.com.neteaseyx.ugallery.activity.ActivityTakePhotos;

/**
 * 发起Gallery后，结果在 onActivityResult() 中返回，调用{@link UGallery#getSingleImage(Intent)}获得Uri
 * 裁剪图像、记得删除缓存图像{@link UGallery#deleteTmpImage(Context)}
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
     * 对于onActivityResult中返回单张图像结果，从intent(data)中解析数据
     * @param data
     * @return
     */
    public static Uri getSingleImage(Intent data){
        String path = data.getStringExtra(PATH);
        Uri uri = Uri.parse("file://"+path);
        return uri;
    }

    /**
     * 对于onActivityResult中返回多张图像结果，从intent(data)中解析数据
     * @param data
     * @return
     */
    public static ArrayList<Uri> getMutilImage(Intent data){
        ArrayList<Uri> uriArrayList = new ArrayList<>();
        ArrayList<String> pathList = data.getStringArrayListExtra(PATH);
        for (int i=0; i<pathList.size(); i++){
            Uri uri = Uri.parse("file://"+pathList.get(i));
            uriArrayList.add(uri);
        }
        return uriArrayList;
    }

    /**
     * 裁剪图像、记得删除缓存图像
     * @param context
     */
    private void deleteTmpImage(Context context) {

        Uri destinationUri = Uri.fromFile(new File(context.getCacheDir(), "SampleCropImage.jpeg"));
        File file = new File(destinationUri.getPath());
        file.delete();
        MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    @Override
                    public void onScanCompleted(String path, Uri uri) {
                    }
                });
    }
}
