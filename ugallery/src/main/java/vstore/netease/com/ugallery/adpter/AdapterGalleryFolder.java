package vstore.netease.com.ugallery.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import vstore.netease.com.ugallery.listener.FolderSelectListener;
import vstore.netease.com.ugallery.model.PhotoFolderInfo;
import vstore.netease.com.ugallery.vholder.VHolderGalleryFolder;


/**
 * 图片文件夹的适配器
 *
 * @author yuhuibin
 * @date 2016-04-22
 */
public class AdapterGalleryFolder extends RecyclerView.Adapter {
    List<PhotoFolderInfo> mAllFolder;
    private Context mContext;

    private FolderSelectListener mFolderSelectListener;

    public AdapterGalleryFolder(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(VHolderGalleryFolder.LAYOUT_ID, parent, false);
        return new VHolderGalleryFolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VHolderGalleryFolder vholder = (VHolderGalleryFolder) holder;
        vholder.setGallertFolder(mAllFolder.get(position), mFolderSelectListener);
    }

    @Override
    public int getItemCount() {
        return mAllFolder.size();
    }

    public void setFolderSelectListener(FolderSelectListener folderSelectListener) {
        mFolderSelectListener = folderSelectListener;
    }

    public void setmAllFolder(List<PhotoFolderInfo> allFolder) {
        mAllFolder = allFolder;
    }
}
