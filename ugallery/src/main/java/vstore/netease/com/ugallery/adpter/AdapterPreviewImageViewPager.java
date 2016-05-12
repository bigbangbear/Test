package vstore.netease.com.ugallery.adpter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author yuhuibin
 * @date 2016-05-07
 */
public class AdapterPreviewImageViewPager extends PagerAdapter{

    public AdapterPreviewImageViewPager(Context context, List<View> photoInfos) {
        mPhotoInfos =photoInfos;
        mContext = context;
    }
    private Context mContext;

    private List<View> mPhotoInfos;
    @Override
    public int getCount() {
        return mPhotoInfos.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        GestureImageView gestureImageView = new GestureImageView(mContext);
//        gestureImageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//        Uri uri = Uri.parse("file://"+mPhotoInfos.get(position).getPhotoPath());
//        gestureImageView.setImageUriNew(uri);
//        ImageView imageView = new ImageView(mContext);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        imageView.setImageResource(R.drawable.ic_gf_default_photo);
//        imageView.setLayoutParams(layoutParams);
//        Log.i("yu", ""+position);
        container.addView(mPhotoInfos.get(position));
        return mPhotoInfos.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView(mPhotoInfos.get(position));
    }
}
