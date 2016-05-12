package com.huibin.yu.imageselect.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.huibin.yu.imageselect.R;

import vstore.netease.com.ugallery.UGallery;
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
                UGallery.selectSingleImageCrop(MainActivity.this);
            }
        });


        findViewById(R.id.bt_open_mutil).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.selectMutipleImage(MainActivity.this);
            }
        });

        findViewById(R.id.bt_open_crop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.takePhoto(MainActivity.this);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == UGallery.SELECT_PHOTO){
            //mImageUri = Uri.parse("file://"+path);
            mImage.setmImagePath(UGallery.getData(data));

        }

        if (requestCode == UGallery.CROP_IMAGE){
            mImage.setmImagePath(UGallery.getData(data));
        }
        if (requestCode == UGallery.TAKE_PHOTO){
            mImage.setmImagePath(UGallery.getData(data));
        }

        if (requestCode == UGallery.SELECT_PHOTO){
//            mImageUri = Uri.parse("file://"+path);
        }

    }

}
