package com.huibin.yu.imageselect.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huibin.yu.imageselect.listener.ImageSelectListener;
import com.huibin.yu.imageselect.model.PhotoFolderInfo;
import com.huibin.yu.imageselect.model.PhotoInfo;
import com.huibin.yu.imageselect.vholder.VHolderGalleryImage;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yuhuibin
 * @date 2016-04-22
 */
public class AdapterGalleryImages extends RecyclerView.Adapter{
    PhotoFolderInfo mFolderInfo;

    private int mWidth;
    private ImageSelectListener mImageSelectListener;
    List<PhotoInfo> mSelectPhoto = new ArrayList<>();

    public PhotoFolderInfo getFolderInfo() {
        return mFolderInfo;
    }

    public AdapterGalleryImages(Context context, List<PhotoInfo> selectPhotos, ImageSelectListener listener) {
        super();
        mImageSelectListener = listener;
        mWidth = 200;
        mSelectPhoto = selectPhotos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( VHolderGalleryImage.LAYOUT_ID, parent, false);
        return new VHolderGalleryImage(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        PhotoInfo info = mFolderInfo.getPhotoList().get(position);
        boolean select = mSelectPhoto.contains(info);
        ((VHolderGalleryImage)holder).setImage(info, select, mImageSelectListener);
    }

    @Override
    public int getItemCount() {
        return mFolderInfo == null ? 0 :mFolderInfo.getPhotoList().size();
    }

    public void setFolderInfo(PhotoFolderInfo folderInfo) {
        mFolderInfo = folderInfo;
    }
}
