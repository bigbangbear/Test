package com.vstore.imageselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huibin.yu.imageselect.R;
import com.huibin.yu.imageselect.adpter.AdapterGalleryFolder;
import com.huibin.yu.imageselect.adpter.AdapterGalleryImages;
import com.huibin.yu.imageselect.listener.FolderSelectListener;
import com.huibin.yu.imageselect.listener.ImageSelectListener;
import com.huibin.yu.imageselect.listener.OnSelectImageResultCallback;
import com.huibin.yu.imageselect.model.PhotoFolderInfo;
import com.huibin.yu.imageselect.model.PhotoInfo;
import com.huibin.yu.imageselect.utils.PhotoTools;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 基于GalleryFinale重新封装选择页面
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class ActivitySelectImage extends Activity implements View.OnClickListener, FolderSelectListener, ImageSelectListener {
    //是否单选图片
    public static boolean mIsSingleImagePick = true;
    //设置显示图片的列数
    public static int mImageColumn = 2;
    //设置最多选择几张图片
    public static int mMaxSelectImage = 2;

    private static OnSelectImageResultCallback mCallBack;

    List<PhotoFolderInfo> mAllFolder = new ArrayList<>();
    ArrayList<PhotoInfo> mSelectPhoto = new ArrayList<>();

    private RecyclerView mFolderRecyclerView;
    private RecyclerView mImageRecyclerView;
    private AdapterGalleryFolder mAdapterGalleryFolder;
    private AdapterGalleryImages mAdapterGalleryImages;
    private TextView mFolderName;
    private Context mContext;
    private final int HANDLER_REFRESH_LIST_EVENT = 1002;
    public static void startActivityForSingleImage(Context context, OnSelectImageResultCallback callBack){
        mCallBack = callBack;
        mIsSingleImagePick = true;
        Intent intent = new Intent(context, ActivitySelectImage.class);
        ( (Activity)context).startActivity(intent);
    }

    public static void startActivityForMutilImage(Context context, OnSelectImageResultCallback callBack){
        mCallBack = callBack;
        mIsSingleImagePick = false;
        Intent intent = new Intent(context, ActivitySelectImage.class);
        ( (Activity)context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        mContext = this;

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPhotos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSelectPhoto.clear();
        mAllFolder.clear();
        System.gc();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectImages", mSelectPhoto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectPhoto = (ArrayList<PhotoInfo>) getIntent().getSerializableExtra("selectImages");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            //暂时考虑一张图片的情况
            mSelectPhoto.get(0).setPhotoPath(resultUri.getPath());
            mCallBack.onHanlderSuccess(UCrop.RESULT_ERROR, mSelectPhoto);
            finish();
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.folder_name:
            case R.id.ly_bottom:
                if (mFolderRecyclerView.getVisibility() == View.VISIBLE){
                    mFolderRecyclerView.setVisibility(View.GONE);
                    mFolderRecyclerView.setFocusable(false);
                }else {
                    mFolderRecyclerView.setVisibility(View.VISIBLE);
                    mFolderRecyclerView.setFocusable(true);
                }

                break;
        }
    }

    @Override
    public void onFolderSelectListner(int position) {
        mFolderName.setText(mAllFolder.get(position).getFolderName());
        mFolderRecyclerView.setVisibility(View.GONE);
        mAdapterGalleryImages.setFolderInfo(mAllFolder.get(position));
        mAdapterGalleryImages.notifyDataSetChanged();
    }

    /** 点击图片回调函数，处理单选，复选*/
    @Override
    public void onImageSelectListner( int position,View view ) {
        mFolderRecyclerView.setVisibility(View.GONE);
        PhotoInfo info = mAdapterGalleryImages.getFolderInfo().getPhotoList().get(position);

        if (mIsSingleImagePick){
            mSelectPhoto.clear();
            mSelectPhoto.add(info);
            startCropImage(info.getPhotoPath());
            }else {
            if (mSelectPhoto.contains(info)){
                mSelectPhoto.remove(info);
            }else {
                if (mSelectPhoto.size() >= mMaxSelectImage){
                    Toast.makeText(mContext, "最多只能选择"+mMaxSelectImage+"张图片",Toast.LENGTH_SHORT).show();
                }else {
                    mSelectPhoto.add(info);
                }
            }
        }

        mAdapterGalleryImages.notifyItemChanged(position);
    }

    private void initView() {
        //设置图片文件夹
        mFolderRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_folder);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mFolderRecyclerView.setLayoutManager(layoutManager);
        mAdapterGalleryFolder = new AdapterGalleryFolder(mContext);
        mAdapterGalleryFolder.setmAllFolder(mAllFolder);
        mAdapterGalleryFolder.setFolderSelectListener(this);

        mFolderRecyclerView.setAdapter(mAdapterGalleryFolder);
        mFolderRecyclerView.setVisibility(View.GONE);
        mFolderName = (TextView)findViewById(R.id.folder_name);
        mFolderName.setOnClickListener(this);


        //显示当前文件夹下的所有图像
        mImageRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mImageColumn);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mImageRecyclerView.setLayoutManager(gridLayoutManager);
        mImageRecyclerView.setHasFixedSize(true);
        mAdapterGalleryImages = new AdapterGalleryImages(mContext, mSelectPhoto, this);
        mImageRecyclerView.setAdapter(mAdapterGalleryImages);

        LinearLayout linearLayoutBottom = (LinearLayout)findViewById(R.id.ly_bottom);
        linearLayoutBottom.setOnClickListener(this);
        getPhotos();
    }

    /**
     * 扫描本地文件夹，获得所以图片
     */
    private void getPhotos() {
        new Thread() {
            @Override
            public void run() {
                super.run();

                mAllFolder.clear();
                mAllFolder = PhotoTools.getAllPhotoFolder(mContext, null);
                mSelectPhoto.clear();

                refreshAdapter();
            }
        }.start();
    }

    private void refreshAdapter(){
        mHanlder.sendEmptyMessageAtTime(HANDLER_REFRESH_LIST_EVENT, 100);
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

    /**
     * 开始剪裁图片
     * @param path 图片路径
     */
    private void startCropImage(String path){
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "SampleCropImage.jpeg"));
        Uri srcUri = Uri.parse("file://"+path);
        UCrop.of(srcUri, destinationUri)
                .withAspectRatio(1, 1)
                .withOptions(setCropOption())
                .start((Activity) mContext);
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if ( msg.what == HANDLER_REFRESH_LIST_EVENT ){
                if (mAllFolder.size() > 0){
                    mFolderName.setText(mAllFolder.get(0).getFolderName());
                    mAdapterGalleryImages.setFolderInfo(mAllFolder.get(0));
                }else {
                    //处理没有图片时的状态
                }
                mAdapterGalleryFolder.setmAllFolder(mAllFolder);
                mAdapterGalleryImages.notifyDataSetChanged();
                mAdapterGalleryFolder.notifyDataSetChanged();
            }
        }
    };
}
