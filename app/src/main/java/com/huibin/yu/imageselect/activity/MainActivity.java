package com.huibin.yu.imageselect.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.huibin.yu.imageselect.R;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;
import vstore.netease.com.ugallery.view.GestureImageView;

public class MainActivity extends AppCompatActivity {

    private TextView openSingleImage;
    private GestureImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openSingleImage = (TextView)findViewById(R.id.bt_open_single);
        openSingleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.takePhoto(MainActivity.this, new SelectImageResult());
            }
        });

        mImage = (GestureImageView) findViewById(R.id.image);
    }

    private class SelectImageResult implements OnGalleryImageResultCallback {

        @Override
        public void onHanlderSuccess(int reqeustCode, String path) {
            Uri uri = Uri.parse("file://"+path);
            mImage.setImageUri(uri);
        }


        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {
int i = 0;
        }
    }
}
