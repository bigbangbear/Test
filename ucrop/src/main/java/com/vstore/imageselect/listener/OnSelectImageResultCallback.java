package com.vstore.imageselect.listener;

import com.huibin.yu.imageselect.model.PhotoInfo;

import java.util.List;

/**
 * 选择图片回掉
 *
 * @author yuhuibin
 * @date 2016-04-25
 */
public  interface OnSelectImageResultCallback {
    /**
     * 处理成功
     * @param reqeustCode
     * @param resultList
     */
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList);

    /**
     * 处理失败或异常
     * @param requestCode
     * @param errorMsg
     */
    public void onHanlderFailure(int requestCode, String errorMsg);
}