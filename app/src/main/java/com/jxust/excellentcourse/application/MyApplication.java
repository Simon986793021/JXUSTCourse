package com.jxust.excellentcourse.application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Simon on 2017/5/12.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"r1sfMn5TebgurDvrxN7O2Ukk-gzGzoHsz","7P8Gv1aorMHWEgk0IWLaS98J");
        AVOSCloud.setDebugLogEnabled(true);
    }
}
