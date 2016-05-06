package vstore.netease.com.ugallery.view;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;

import com.yalantis.ucrop.view.GestureCropImageView;

/**
 * 支持手势缩放、放大、平移的ImageView
 * @author yuhuibin
 * @date 2016-05-06
 */
public class GestureImageView extends GestureCropImageView{

    private static final String TAG = "GestureImageView";

    public GestureImageView(Context context) {
        super(context);
    }

    public GestureImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GestureImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 设置图像Uri
     * @param imageUri
     */
    public void setImageUri(@NonNull Uri imageUri){
        try {
            setImageUri(imageUri, imageUri);
        }catch (Exception e){
            Log.i(TAG, e.toString());
        }
    }
}
