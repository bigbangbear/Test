package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.File;

import vstore.netease.com.ugallery.R;
import vstore.netease.com.ugallery.UGallery;
import vstore.netease.ucrop.view.GestureCropImageView;
import vstore.netease.ucrop.view.OverlayView;
import vstore.netease.ucrop.view.UCropView;

/**
 * @author yuhuibin
 * @date 2016-05-12
 */
public class ActivityCropImageNew extends ActivityUGalleryBase{

    private static Uri mUri;
    public static void startActivity(Context context, Uri uri){
        mUri = uri;
        Intent intent = new Intent(context, ActivityCropImageNew.class);
        ( (Activity)context).startActivityForResult(intent, UGallery.CROP_IMAGE);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        initiateRootViews();
        setImagedata();
    }

    private void setImagedata(){
        try {
            Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
            mGestureCropImageView.setImageUri(mUri, destinationUri);
        } catch (Exception e) {
            finish();
        }
    }

    private UCropView mUCropView;
    private GestureCropImageView mGestureCropImageView;
    private OverlayView mOverlayView;

    private void initiateRootViews() {
        mUCropView = (UCropView) findViewById(R.id.ucropview);
        mGestureCropImageView = mUCropView.getCropImageView();
        mOverlayView = mUCropView.getOverlayView();

    }
}
