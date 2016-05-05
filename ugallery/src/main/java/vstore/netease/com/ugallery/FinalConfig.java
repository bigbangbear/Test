package vstore.netease.com.ugallery;


import vstore.netease.com.ugallery.widget.ImageLoader;

/**
 * @author yuhuibin
 * @date 2016-04-28
 */
public class FinalConfig {
    private static ImageLoader imageLoader;

    public static ImageLoader getImageLoader() {
        return imageLoader;
    }

    public static void setImageLoader(ImageLoader imageLoader) {
        FinalConfig.imageLoader = imageLoader;
    }
}
