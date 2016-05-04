package com.huibin.yu.imageselect.vholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.huibin.yu.imageselect.R;
import com.huibin.yu.imageselect.activity.ActivitySelectImage;
import com.huibin.yu.imageselect.listener.ImageSelectListener;
import com.huibin.yu.imageselect.model.PhotoInfo;
import com.huibin.yu.imageselect.widget.ImageViewFresco;


/**
 * 显示图的ViewHolder
 * @author yuhuibin
 * @date 2016-04-22
 */
public class VHolderGalleryImage extends RecyclerView.ViewHolder{
    public static int LAYOUT_ID = R.layout.vholder_gallery_image;
    private static int mHeight = 0;
    private ImageViewFresco mImageView;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private int mImageNumbers = ActivitySelectImage.mImageColumn;


    public VHolderGalleryImage(View itemView) {
        super(itemView);
        mContext = itemView.getContext();
        mImageView = (ImageViewFresco)itemView.findViewById(R.id.image);

        mLinearLayout = (LinearLayout)itemView.findViewById(R.id.ly);
        if (mHeight == 0){
            DisplayMetrics dm = new DisplayMetrics();
            dm = itemView.getContext().getResources().getDisplayMetrics();
            mHeight = dm.widthPixels / mImageNumbers;
        }
        mLinearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));

    }

    /**
     *
     * @param info 要显示的图片
     * @param isSelect 更新选中状态
     * @param listener 选中事件处理
     */
    public void setImage(PhotoInfo info, boolean isSelect, final ImageSelectListener listener){

        mImageView.loadImageFilePath(info.getPhotoPath(), mHeight, mHeight);
        mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onImageSelectListner(getPosition(), itemView);
            }
        });
        if(isSelect){
            mLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        }else {
            mLinearLayout.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }
    }
}
