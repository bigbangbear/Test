package com.huibin.yu.imageselect.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.huibin.yu.imageselect.R;

import java.util.ArrayList;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.view.GestureImageView;

public class MainActivity extends AppCompatActivity {
    private TextView openSingleImage;
    private GestureImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_main);

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
                UGallery.selectSingleImageCrop(MainActivity.this);
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
            mImage.setmImagePath(UGallery.getSingleImage(data));

        }

        if (requestCode == UGallery.CROP_IMAGE){
            mImage.setmImagePath(UGallery.getSingleImage(data));
        }
        if (requestCode == UGallery.TAKE_PHOTO){
            mImage.setmImagePath(UGallery.getSingleImage(data));
        }

        if (requestCode == UGallery.SELECT_MUTIL_PHOTO){
            ArrayList<Uri> uriArrayList = UGallery.getMutilImage(data);
            for (Uri uri:uriArrayList){
                Log.i("uri=", uri.toString());
            }
        }

    }

}
