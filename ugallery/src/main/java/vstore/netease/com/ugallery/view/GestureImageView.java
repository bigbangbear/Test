package vstore.netease.com.ugallery.view;

import android.content.Context;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

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
            int size = calculateMaxBitmapSize();
            Log.i("size", size+"");
        //    setMaxBitmapSize(size);
            setImageUri(imageUri, imageUri);
        }catch (Exception e){
            Log.i(TAG, e.toString());
        }
    }




    protected int calculateMaxBitmapSize() {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        int width, height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            display.getSize(size);
            width = size.x;
            height = size.y;
        } else {
            width = display.getWidth();
            height = display.getHeight();
        }
        return (int) Math.sqrt(Math.pow(width, 2) + Math.pow(height, 2)) * 2;
    }
}
