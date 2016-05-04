package com.vstore.imageselect;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huibin.yu.imageselect.R;
import com.huibin.yu.imageselect.listener.OnSelectImageResultCallback;
import com.huibin.yu.imageselect.model.PhotoInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView openSingleImage;
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openSingleImage = (TextView)findViewById(R.id.bt_open_single);
        openSingleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivitySelectImage.startActivityForSingleImage(MainActivity.this, new SelectImageResult());
            }
        });

        mImage = (ImageView) findViewById(R.id.image);
    }

    private class SelectImageResult implements OnSelectImageResultCallback{
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
           // mImage.loadImageFilePath(resultList.get(0).getPhotoPath());
            mImage.setImageURI(Uri.parse(resultList.get(0).getPhotoPath()));
            mImage.invalidate();
            mImage.postInvalidate();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    }
}
