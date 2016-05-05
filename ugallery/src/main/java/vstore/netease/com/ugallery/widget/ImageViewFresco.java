package vstore.netease.com.ugallery.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import vstore.netease.com.ugallery.R;

/**
 * @author yuhuibin
 * @date 2016-04-27
 */
public class ImageViewFresco extends SimpleDraweeView{
    private Context mContext;

    public ImageViewFresco(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
        mContext = context;

    }

    public ImageViewFresco(Context context) {
        super(context);
        mContext = context;

    }

    public ImageViewFresco(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ImageViewFresco(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    public ImageViewFresco(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
    }

    public void loadImageFilePath(String path){
        Uri uri = Uri.parse("file://"+path);
        setImageURI(uri);
    }


    public void loadImageFilePath(String path, int width, int height){
        Uri uri = Uri.parse("file://"+path);
        //setImageURI(uri);

        Resources resources = mContext.getResources();
        //自定义图片的显示
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.ic_gf_default_photo);
        GenericDraweeHierarchy hierarchy = new GenericDraweeHierarchyBuilder(resources)
                .setFadeDuration(300)
                .setPlaceholderImage(drawable)
                .setFailureImage(drawable)
                .setProgressBarImage(new ProgressBarDrawable())
                .build();
        setHierarchy(hierarchy);

        DraweeHolder<GenericDraweeHierarchy> draweeHolder = DraweeHolder.create(hierarchy, mContext);

        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.getController())
                .setImageRequest(imageRequest)
                .build();

        draweeHolder.setController(controller);


    }
}
