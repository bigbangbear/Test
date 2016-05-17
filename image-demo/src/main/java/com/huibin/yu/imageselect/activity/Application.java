package com.huibin.yu.imageselect.activity;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author yuhuibin
 * @date 2016-05-12
 */
public class Application extends android.app.Application{

    private RefWatcher mRefWatcher;
    @Override
    public void onCreate() {
        super.onCreate();
        mRefWatcher = LeakCanary.install(this);


    }
}
