package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import vstore.netease.com.ugallery.R;
import vstore.netease.com.ugallery.adpter.AdapterPreviewImageViewPager;
import vstore.netease.com.ugallery.model.PhotoInfo;
import vstore.netease.com.ugallery.view.GestureImageView;

/**
 * @author yuhuibin
 * @date 2016-05-07
 */
public class ActivityPreviewImage extends Activity{

    private ViewPager mViewPager;
    private static List<PhotoInfo> mListPhotos;

    public static void startActivity(Context context, List<PhotoInfo> photoInfos){
        mListPhotos = photoInfos;
        Intent intent = new Intent(context, ActivityPreviewImage.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_image);
        mContext = this;
        initView();
    }

    private List<View> mViewList = new ArrayList<>();
    private Context mContext;
    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.viewpage);
        for (PhotoInfo info:mListPhotos){
            GestureImageView gestureImageView = new GestureImageView(mContext);
            Uri uri = Uri.parse("file://"+info.getPhotoPath());
            gestureImageView.setImageUri(uri);
            mViewList.add(gestureImageView);
        }


        mViewPager.setAdapter(new AdapterPreviewImageViewPager(this, mViewList));
//        mViewPager.setCurrentItem(0);

//        mViewPager.setAdapter(new PagerAdapter() {
//            @Override
//            public int getCount() {
//                return 2;
//            }
//
//            @Override
//            public boolean isViewFromObject(View view, Object object) {
//                return view == object;
//            }
//        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
