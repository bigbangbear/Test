package com.huibin.yu.imageselect.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.huibin.yu.imageselect.R;

import java.util.List;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;
import vstore.netease.com.ugallery.listener.OnGalleryImagesResultCallback;
import vstore.netease.com.ugallery.model.PhotoInfo;
import vstore.netease.com.ugallery.view.GestureImageView;

public class MainActivity extends AppCompatActivity {
    private TextView openSingleImage;
    private GestureImageView mImage;
    private Uri mImageUri = null;
    private LinearLayout mLy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);




        mLy = (LinearLayout)findViewById(R.id.ly);
        mImage = (GestureImageView)findViewById(R.id.image);

        openSingleImage = (TextView)findViewById(R.id.bt_open_single);
        openSingleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.selectSingleImage(MainActivity.this, new SelectImageResult());
            }
        });


        findViewById(R.id.bt_open_mutil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.selectMutipleImage(MainActivity.this, new SelectMutilImages());
            }
        });

        findViewById(R.id.bt_open_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mImageUri != null) {
                    UGallery.cropImage(MainActivity.this, new SelectImageResult(), mImageUri);
                }
            }
        });


    }

    private SimpleDraweeView mFrescoImgae;

    private class SelectImageResult implements OnGalleryImageResultCallback {
        @Override
        public void onHanlderSuccess(int reqeustCode, String path) {
            if (reqeustCode == UGallery.SELECT_SINGLE_PHOTO){
                mImageUri = Uri.parse("file://"+path);
                mImage.setmImagePath(path);

            }

            if (reqeustCode == UGallery.CROP_IMAGE){
                mImage.setmImagePath(path);
            }

            if (reqeustCode == UGallery.SELECT_MUTIL_PHOTO){
                mImageUri = Uri.parse("file://"+path);
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    }

    private class SelectMutilImages implements OnGalleryImagesResultCallback{
        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }

        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {

        }
    }
}
