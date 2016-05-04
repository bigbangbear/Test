package com.vstore.imageselect.utils;

import com.github.moduth.blockcanary.BlockCanaryContext;

/**
 * BlockCanary性能检测配置类
 *
 * @author yuhuibin
 * @date 2016-04-28
 */
public class AppBlockCanaryContext extends BlockCanaryContext{

    @Override
    public int getConfigBlockThreshold() {
        return 500;
    }

    @Override
    public boolean isNeedDisplay() {
        return true;
    }
}
