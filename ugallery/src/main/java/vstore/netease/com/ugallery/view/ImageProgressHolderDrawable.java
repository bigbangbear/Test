package vstore.netease.com.ugallery.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;

/**
 * fresco 图像加载进度
 *
 * @author hzwangyufei
 */
public class ImageProgressHolderDrawable extends Drawable {

    private static final int BG_COLOR = Color.parseColor("#f9f9f9");

    private Paint mHolderPaint = new Paint();       // 进度背景画笔
    private Paint mProgressPaint = new Paint();     // 进度画笔

    private int pathContainerLength;
    private int w, h;
    int ax, ay, bx, by;
    int rx, ry;

    private Handler mProgressHandler;

    private float mPercentage = 0;

    private int mMaxContainerLength;

    public ImageProgressHolderDrawable(Context context) {

        mMaxContainerLength = dip2px(context, 15);

        mProgressHandler = new Handler();
        Runnable progressCallback = new Runnable() {
            @Override
            public void run() {
                mPercentage += 0.035f;
                if (mPercentage < 0.8f) {
                    invalidateSelf();
                    mProgressHandler.postDelayed(this, 35);
                } else {
                    mProgressHandler.removeCallbacks(this);
                }
            }
        };

        mHolderPaint.setColor(Color.parseColor("#dddddd"));
        mHolderPaint.setStrokeWidth(15);
        mHolderPaint.setAntiAlias(true);
        mHolderPaint.setStyle(Paint.Style.STROKE);

        mProgressPaint.setColor(Color.parseColor("#bbbbbb"));
        mProgressPaint.setStrokeWidth(15);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);

        mProgressHandler.post(progressCallback);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(BG_COLOR);

        rx = (int) ((w / 2 - pathContainerLength / 2) + pathContainerLength * (mPercentage % 1f));
        ry = (int) ((h / 2 - pathContainerLength / 2) + pathContainerLength * (1 - mPercentage % 1f));

        canvas.drawLine(ax, ay, bx, by, mHolderPaint);
        canvas.drawLine(ax, ay, rx, ry, mProgressPaint);
    }

    @Override
    public void setAlpha(int alpha) {
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
    }

    @Override
    public int getOpacity() {
        return 0;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        w = bounds.width();
        h = bounds.height();
        int length = Math.min(bounds.width(), bounds.height());
        pathContainerLength = (int) (length * 0.07);
        if (pathContainerLength > mMaxContainerLength) {
            pathContainerLength = mMaxContainerLength;
        } else if (pathContainerLength < mMaxContainerLength / 2) {
            pathContainerLength = mMaxContainerLength / 2;
        }

        ax = w / 2 - pathContainerLength / 2;
        ay = h / 2 + pathContainerLength / 2;
        bx = w / 2 + pathContainerLength / 2;
        by = h / 2 - pathContainerLength / 2;

        int strokeWidth = (int) (length / 1500f * 35);
        if (strokeWidth > 11) {
            strokeWidth = 11;
        } else if (strokeWidth < 5) {
            strokeWidth = 5;
        }

        mHolderPaint.setStrokeWidth(strokeWidth);
        mProgressPaint.setStrokeWidth(strokeWidth);

        rx = ax;
        ry = ay;

        super.onBoundsChange(bounds);
    }

    public static int dip2px(Context context, float dipValue) {
        if(context == null){
            return 0;
        }

        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}
