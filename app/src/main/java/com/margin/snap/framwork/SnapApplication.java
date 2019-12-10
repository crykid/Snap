package com.margin.snap.framwork;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.margin.snap.ApplicationUtils;
import com.zxy.tiny.Tiny;

/**
 * Created by : mr.lu
 * Created at : 2019-11-08 at 09:38
 * Description:
 */
public class SnapApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationUtils.init(this);
        Tiny.getInstance().init(this);

        SnapConfigurator.getInstance()
                .appContext(this)
                .handler(new Handler(Looper.getMainLooper()));
    }
}
