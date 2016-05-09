package vstore.netease.com.ugallery.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import vstore.netease.com.ugallery.R;
import vstore.netease.com.ugallery.UGallery;
import vstore.netease.com.ugallery.adpter.AdapterGalleryFolder;
import vstore.netease.com.ugallery.adpter.AdapterGalleryImages;
import vstore.netease.com.ugallery.listener.FolderSelectListener;
import vstore.netease.com.ugallery.listener.ImageSelectListener;
import vstore.netease.com.ugallery.listener.OnGalleryImageResultCallback;
import vstore.netease.com.ugallery.listener.OnGalleryImagesResultCallback;
import vstore.netease.com.ugallery.model.PhotoFolderInfo;
import vstore.netease.com.ugallery.model.PhotoInfo;
import vstore.netease.com.ugallery.utils.PhotoTools;


/**
 * 基于GalleryFinale重新封装选择页面
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class ActivitySelectImage extends Activity implements  FolderSelectListener, ImageSelectListener {
    //是否单选图片
    public static boolean mIsSingleImagePick ;
    //是否截图
    //public static boolean mIsCrop = true;
    //设置显示图片的列数
    public static int mImageColumn = 3;
    //设置最多选择几张图片
    public static int mMaxSelectImage = 9;

    //返回结果
    private static OnGalleryImageResultCallback mSingleImageCallBack;
    private static OnGalleryImagesResultCallback mMutilImageCallBack;
    private static final int HANDLER_REFRESH_LIST_EVENT = 1002;

    List<PhotoFolderInfo> mAllFolder = new ArrayList<>();
    ArrayList<PhotoInfo> mSelectPhoto = new ArrayList<>();

    private RecyclerView mFolderRecyclerView;
    private RecyclerView mImageRecyclerView;
    private AdapterGalleryFolder mAdapterGalleryFolder;
    private AdapterGalleryImages mAdapterGalleryImages;
    private TextView mFolderName;
    private Context mContext;
    private LinearLayout mLinearLayoutFolder;

    private TextView mSelectFinish;
    private TextView mSelectPreview;

    private ScanImageHandler mHandler;

    /**
     * 选择单张照片
     * @param context
     * @param callBack
     */
    public static void startActivityForSingleImage(Context context, OnGalleryImageResultCallback callBack){
        mSingleImageCallBack = callBack;
        mIsSingleImagePick = true;
        Intent intent = new Intent(context, ActivitySelectImage.class);
        ( (Activity)context).startActivity(intent);
    }

    /**
     * 选择多张照片
     * @param context
     * @param callBack
     */
    public static void startActivityForMutilImage(Context context, OnGalleryImagesResultCallback callBack){
        mMutilImageCallBack = callBack;
        mIsSingleImagePick = false;
        Intent intent = new Intent(context, ActivitySelectImage.class);
        ( (Activity)context).startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        mContext = this;
        initFresco();
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
    public void onFolderSelectListner(int position) {
        mFolderName.setText(mAllFolder.get(position).getFolderName());
        changeFolderStatus();
        mAdapterGalleryImages.setFolderInfo(mAllFolder.get(position));
        mAdapterGalleryImages.notifyDataSetChanged();
    }

    /** 点击图片回调函数，处理单选，复选*/
    @Override
    public void onImageSelectListner( int position,View view ) {
        PhotoInfo info = mAdapterGalleryImages.getFolderInfo().getPhotoList().get(position);

        if (mIsSingleImagePick){
                    mSingleImageCallBack.onHanlderSuccess(UGallery.SELECT_SINGLE_PHOTO, info.getPhotoPath());
                    finish();
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
            mSelectPreview.setText(getResources().getText(R.string.select_preview)+"("+mSelectPhoto.size()+")");
        }

        mAdapterGalleryImages.notifyItemChanged(position);
    }

    /**
     * 点击文件夹，改变状态
     */
    private void changeFolderStatus(){
        if (mLinearLayoutFolder.getVisibility() == View.VISIBLE){
            mLinearLayoutFolder.setVisibility(View.GONE);
        }else {
            mLinearLayoutFolder.setVisibility(View.VISIBLE);
        }
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
        mFolderName = (TextView)findViewById(R.id.folder_name);
        mFolderName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFolderStatus();
            }
        });

        mLinearLayoutFolder = (LinearLayout)findViewById(R.id.ly_folder_layer);
        mLinearLayoutFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFolderStatus();
            }
        });
        //显示当前文件夹下的所有图像
        mImageRecyclerView = (RecyclerView)findViewById(R.id.recyclerview_image);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, mImageColumn);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        mImageRecyclerView.setLayoutManager(gridLayoutManager);
        mImageRecyclerView.setHasFixedSize(true);
        mAdapterGalleryImages = new AdapterGalleryImages(mContext, mSelectPhoto, this);
        mImageRecyclerView.setAdapter(mAdapterGalleryImages);

        mSelectFinish = (TextView)findViewById(R.id.bt_finish);
        mSelectFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectPhoto.size() > 0){
                    ActivityPreviewImage.startActivity(mContext, mSelectPhoto);
                }
            }
        });
        mSelectPreview = (TextView)findViewById(R.id.bt_preview);
        mSelectPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectPhoto.size() > 0){
                    ActivityPreviewImage.startActivity(mContext, mSelectPhoto);
                }
            }
        });
        if (!mIsSingleImagePick){
            mSelectFinish.setVisibility(View.VISIBLE);
            mSelectPreview.setVisibility(View.VISIBLE);
        }else {
            mSelectFinish.setVisibility(View.GONE);
            mSelectPreview.setVisibility(View.GONE);
        }

        mHandler = new ScanImageHandler(this);
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
                //mSelectPhoto.clear();

                refreshAdapter();
            }
        }.start();
    }

    private void refreshAdapter(){
        mHandler.sendEmptyMessageAtTime(HANDLER_REFRESH_LIST_EVENT, 100);
    }

    private void initFresco(){
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
             //   .setBitmapsConfig( Bitmap.Config.RGB_565)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }

    /**
     * 使用静态内部类，防止内存溢出
     */
    private static class ScanImageHandler extends Handler{
        private  WeakReference<ActivitySelectImage> mActivity;

        public ScanImageHandler(ActivitySelectImage activity){
            mActivity = new WeakReference<ActivitySelectImage>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final ActivitySelectImage actiivty = mActivity.get();
            if (actiivty == null){
                return;
            }
            if ( msg.what == HANDLER_REFRESH_LIST_EVENT ){
                if (actiivty.mAllFolder.size() > 0){
                    actiivty.mFolderName.setText(actiivty.mAllFolder.get(0).getFolderName());
                    actiivty.mAdapterGalleryImages.setFolderInfo(actiivty.mAllFolder.get(0));
                }
                actiivty.mAdapterGalleryFolder.setmAllFolder(actiivty.mAllFolder);
                actiivty.mAdapterGalleryImages.notifyDataSetChanged();
                actiivty.mAdapterGalleryFolder.notifyDataSetChanged();
            }
        }
    }
}
