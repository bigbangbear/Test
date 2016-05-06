package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.yalantis.ucrop.UCrop;

import java.io.File;

import vstore.netease.com.ugallery.R;
import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;

/**
 * @author yuhuibin
 * @date 2016-05-06
 */
public class ActivityCropImage extends Activity{

    private static OnGalleryImageResultCallback mSingleImageCallBack;
    private static Uri mUri;
    /**
     * 选择单张照片
     * @param context
     * @param callBack
     */
    public static void startActivity(Context context, OnGalleryImageResultCallback callBack, Uri uri){
        mSingleImageCallBack = callBack;
        mUri = uri;
        Intent intent = new Intent(context, ActivityCropImage.class);
        ( (Activity)context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startCropImage();
    }

    /**
     * 开始剪裁图片
     */
    private void startCropImage(){
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));

        UCrop.of(mUri, destinationUri)
                .withAspectRatio(1, 1)
                .withOptions(setCropOption())
                .start(ActivityCropImage.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            //剪裁单张图像后，通过回调返回结果
            final Uri resultUri = UCrop.getOutput(data);
            mSingleImageCallBack.onHanlderSuccess(UGallery.CROP_IMAGE, resultUri.getPath());
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    /**
     * 剪裁图片参数配置
     * @return
     */
    private UCrop.Options setCropOption(){
        UCrop.Options options = new UCrop.Options();
        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        options.setToolbarColor(getResources().getColor(R.color.colorPrimary));
        return options;
    }
}
