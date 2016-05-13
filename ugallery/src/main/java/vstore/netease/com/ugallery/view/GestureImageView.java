package vstore.netease.com.ugallery.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.WindowManager;

import vstore.netease.ucrop.callback.BitmapLoadCallback;
import vstore.netease.ucrop.util.BitmapLoadUtils;
import vstore.netease.ucrop.view.GestureCropImageView;

/**
 * 支持手势缩放、放大、平移的ImageView
 * @author yuhuibin
 * @date 2016-05-06
 */
public class GestureImageView extends GestureCropImageView {
    private static final String TAG = "GestureImageView";
    private Uri mImageUri ;

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
     * 处理当没有传入Uri的时候，触摸事件所导致的异常
     * @param event
     * @return
     */

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getImageUri() == null && mImageUri == null){
            return false;
        }

        //多点触控的时候，中断父控件获取到事件
        if (event.getPointerCount() > 1){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.onTouchEvent(event);
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
        return (int) Math.max(width, height);
    }

    /**
     * 为减少内存的占有，以宽高的最大值为maxBitmapSize
     * @param imageUri
     * @throws Exception
     */
    public void setImageUri(@NonNull Uri imageUri) {
        mImageUri = imageUri;
        int maxBitmapSize = calculateMaxBitmapSize();
        BitmapLoadUtils.decodeBitmapInBackground(getContext(), imageUri, imageUri, maxBitmapSize, maxBitmapSize,
                new BitmapLoadCallback() {
                    @Override
                    public void onBitmapLoaded(@NonNull final Bitmap bitmap) {
                        mBitmapDecoded = true;
                        setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(@NonNull Exception bitmapWorkerException) {
                        Log.e(TAG, "onFailure: setImageUri", bitmapWorkerException);
                        if (mTransformImageListener != null) {
                            mTransformImageListener.onLoadFailure(bitmapWorkerException);
                        }
                    }
                });
        setRotateEnabled(false);
    }

    /**
     * 设置本地图像的路径
     * @param path
     */
    public void setmImagePath(@NonNull Uri path){
        setImageUri(path);
    }
}
