package com.margin.snap.framwork;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by : mr.lu
 * Created at : 2019-11-25 at 14:04
 * Description:
 */
public class SnapConfigurator {

    private final static Map<Enum, String> TOP_ACTIVITY_MAP = new HashMap<>(1);
    private final static Map<Enum, Object> CONFIGURATIONS = new HashMap<>();

    private SnapConfigurator() {
    }

    public SnapConfigurator bettery(int level) {
        CONFIGURATIONS.put(SnapConfiguration.BATTERY, level);
        return this;
    }

    private final static class Holder {
        private final static SnapConfigurator INSTANCE = new SnapConfigurator();
    }

    public static SnapConfigurator getInstance() {
        return Holder.INSTANCE;
    }

    public SnapConfigurator topActivity(AppCompatActivity activity) {

        if (activity != null && activity instanceof BaseTopActivity) {
            TOP_ACTIVITY_MAP.put(SnapConfiguration.TOP_ACTIVITY, activity.getClass().getName());
        }
        return this;
    }

    public SnapConfigurator appContext(Context context) {
        if (context == null) throw new NullPointerException("Context can not be null !");
        CONFIGURATIONS.put(SnapConfiguration.APP_CONTEXT, context);
        return this;
    }

    public SnapConfigurator handler(final Handler handler) {
        if (handler == null) {
            throw new NullPointerException("android.os.Handler can not be null");
        }
        if (handler.getLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("handler must initialized by mainLooper ! ");
        }
        CONFIGURATIONS.put(SnapConfiguration.HANDLER, handler);
        return this;
    }


    public <T> T getConfiguration(SnapConfiguration configuration) {
        return (T) CONFIGURATIONS.get(configuration);
    }

    public String getTopRunningActivityName() {

        return TOP_ACTIVITY_MAP.get(SnapConfiguration.TOP_ACTIVITY);
    }


}
