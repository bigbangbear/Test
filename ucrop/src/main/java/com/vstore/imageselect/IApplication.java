/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vstore.imageselect;

import android.app.Application;
import android.graphics.Bitmap;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.github.moduth.blockcanary.BlockCanary;
import com.huibin.yu.imageselect.utils.AppBlockCanaryContext;
import com.huibin.yu.imageselect.widget.FrescoImageLoader;

/**
 * Desction:
 * Author:pengjianbo
 * Date:15/12/18 下午1:45
 */
public class IApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig( Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, imagePipelineConfig);

        //BlockCanary卡顿检测
        BlockCanary.install(this, new AppBlockCanaryContext()).start();

        FinalConfig.setImageLoader(new FrescoImageLoader(getApplicationContext()));
    }
}
