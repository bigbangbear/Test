package vstore.netease.com.ugallery.vholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import vstore.netease.com.ugallery.R;
import vstore.netease.com.ugallery.activity.ActivitySelectImage;
import vstore.netease.com.ugallery.listener.ImageSelectListener;
import vstore.netease.com.ugallery.model.PhotoInfo;
import vstore.netease.com.ugallery.widget.ImageViewFresco;


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
            Log.i("yu", "height");
        }
        mLinearLayout.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeight));
    }

    /**
     *
     * @param info 要显示的图片
     * @param isSelect 更新选中状态
     * @param listener 选中事件处理
     */
    public void setImage(final PhotoInfo info, boolean isSelect, final ImageSelectListener listener){
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
