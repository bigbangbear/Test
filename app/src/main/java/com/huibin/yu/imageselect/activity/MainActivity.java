package com.huibin.yu.imageselect.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.huibin.yu.imageselect.R;
import com.yalantis.ucrop.view.GestureCropImageView;

import java.util.List;

import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.listener.OnSelectImageResultCallback;
import vstore.netease.com.ugallery.model.PhotoInfo;

public class MainActivity extends AppCompatActivity {

    private TextView openSingleImage;
    private GestureCropImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openSingleImage = (TextView)findViewById(R.id.bt_open_single);
        openSingleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UGallery.selectMutipleImage(MainActivity.this, new SelectImageResult());
            }
        });

        mImage = (GestureCropImageView) findViewById(R.id.image);
    }

    private class SelectImageResult implements OnSelectImageResultCallback {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
           // mImage.loadImageFilePath(resultList.get(0).getPhotoPath());
            Uri uri = Uri.parse("file://"+resultList.get(0).getPhotoPath());
            mImage.setImageUri(uri);
            for (PhotoInfo info: resultList){
                Log.i("hzyuhuibin", info.getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    }
}
