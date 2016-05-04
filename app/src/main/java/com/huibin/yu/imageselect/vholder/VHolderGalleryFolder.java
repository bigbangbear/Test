package com.huibin.yu.imageselect.vholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huibin.yu.imageselect.R;
import com.huibin.yu.imageselect.listener.FolderSelectListener;
import com.huibin.yu.imageselect.model.PhotoFolderInfo;
import com.huibin.yu.imageselect.widget.ImageViewFresco;


/**
 * gallery选择时候的 ViewHolder
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class VHolderGalleryFolder extends RecyclerView.ViewHolder{

    public static int LAYOUT_ID = R.layout.vholder_gallery;
    public VHolderGalleryFolder(View itemView) {
        super(itemView);
        mFolderName = (TextView)itemView.findViewById(R.id.folder_name);
        mImageNumbers = (TextView)itemView.findViewById(R.id.image_numbers);
        mImageView = (ImageViewFresco)itemView.findViewById(R.id.image);
        mLinaerLayout = (LinearLayout)itemView.findViewById(R.id.ly);
    }

    private TextView mFolderName;
    private TextView mImageNumbers;
    private ImageViewFresco mImageView;
    private LinearLayout mLinaerLayout;


    /**
     * 图片文件夹
     * @param folderInfo 文件夹信息
     * @param listener 选择文件夹事件处理
     */
    public void setGallertFolder(PhotoFolderInfo folderInfo, final FolderSelectListener listener){
        mFolderName.setText(folderInfo.getFolderName());
        mImageNumbers.setText(folderInfo.getPhotoList().size() + "Photos");
        if (folderInfo.getPhotoList().size() != 0) {
            mImageView.loadImageFilePath(folderInfo.getCoverPhoto().getPhotoPath(), 50, 50);
        }else {
            mImageView.setImageResource(R.drawable.ic_gf_default_photo);
        }
        mLinaerLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onFolderSelectListner(getPosition());

            }
        });

    }


}
