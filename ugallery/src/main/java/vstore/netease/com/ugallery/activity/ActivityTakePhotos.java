package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;

/**
 * @author yuhuibin
 * @date 2016-05-05
 */
public class ActivityTakePhotos extends Activity{
    private static OnGalleryImageResultCallback mCallBack;

    private Uri mTakePhotoUri = null;
    public static void startActivityForSingleImage(Context context, OnGalleryImageResultCallback callBack){
        mCallBack = callBack;
        Intent intent = new Intent(context, ActivityTakePhotos.class);
        ( (Activity)context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        takePhotoAction();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == UGallery.TAKE_PHOTO_SUCCESS) {
            if (resultCode == RESULT_OK && mTakePhotoUri != null) {
                final String path = mTakePhotoUri.getPath();
                if (new File(path).exists()) {
                    mCallBack.onHanlderSuccess(UGallery.TAKE_PHOTO_SUCCESS, path);
                    finish();
                }
                else {
                    mCallBack.onHanlderFailure(UGallery.TAKE_PHOTO_FAIL, "take photo fail");
                }
            }
            else {
                mCallBack.onHanlderFailure(UGallery.TAKE_PHOTO_FAIL, "take photo fail");
            }
        }
    }

    private File mTakePhotoFolder = null;
    protected void takePhotoAction() {
        //mTakePhotoUri = Uri.fromFile(new File(getCacheDir(), "TakeImageTmp.jpg"));
        if (mTakePhotoFolder == null){
            mTakePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/");
        }
        if (mTakePhotoUri == null){
            File img = new File(mTakePhotoFolder, "IMG"+ ".jpg");
            mTakePhotoUri = Uri.fromFile(img);
        }
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
        startActivityForResult(captureIntent, UGallery.TAKE_PHOTO_SUCCESS);
    }
}
