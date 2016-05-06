package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;

/**
 * 拍照的Activity
 * @author liangbin
 * @date 2016-05-05
 */
public class ActivityTakePhotos extends Activity{
    private static final String TAG = "ActivityTakePhotos";
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
        if (requestCode == UGallery.TAKE_PHOTO) {
            if (resultCode == RESULT_OK && mTakePhotoUri != null) {
                final String path = mTakePhotoUri.getPath();
                if (new File(path).exists()) {
                    mCallBack.onHanlderSuccess(UGallery.TAKE_PHOTO, path);
                    finish();
                }
                else {
                    mCallBack.onHanlderFailure(UGallery.TAKE_PHOTO, "take photo fail");
                }
            }
            else {
                mCallBack.onHanlderFailure(UGallery.TAKE_PHOTO, "take photo fail");
            }
        }
    }

    private File mTakePhotoFolder = null;
    protected void takePhotoAction() {

        if (mTakePhotoFolder == null){
            mTakePhotoFolder = new File(Environment.getExternalStorageDirectory(), "/DCIM/");
        }

        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date(System.currentTimeMillis()));

        if (mTakePhotoUri == null){
            File img = new File(mTakePhotoFolder, fileName+ ".jpg");
            mTakePhotoUri = Uri.fromFile(img);
        }
        Log.v(TAG,"mTakePhotoUri---"+mTakePhotoUri);
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mTakePhotoUri);
        startActivityForResult(captureIntent, UGallery.TAKE_PHOTO);
    }
}
