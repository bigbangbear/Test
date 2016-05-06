package vstore.netease.com.ugallery.listener;

/**
 * 选择图像回调，单张图像
 * @author yuhuibin
 * @date 2016-05-05
 */
public interface OnGalleryImageResultCallback {
    /**
     * 处理成功
     * @param reqeustCode
     * @param path
     */
    public void onHanlderSuccess(int reqeustCode, String path);

    /**
     * 处理失败或异常
     * @param requestCode
     * @param errorMsg
     */
    public void onHanlderFailure(int requestCode, String errorMsg);
}
