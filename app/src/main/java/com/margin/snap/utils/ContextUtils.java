package com.margin.snap.utils;

import android.app.Application;
import android.content.Context;
import android.graphics.drawable.Drawable;

import java.lang.ref.WeakReference;

/**
 * Created by : luxj at 2021-1-18,11:32
 * Description:
 */
public class ContextUtils {
    private static WeakReference<Context> sContext = null;
    private static WeakReference<Context> sApp = null;


    public static void init(Context appContext) {
        if (appContext instanceof Application) {
            sApp = new WeakReference<>(appContext);
        }
        sContext = new WeakReference<>(appContext);
    }

    /**
     * 配置的路径
     */
    public static int getColor(int resColorInt) {
        Context context = sContext.get();
        if (context != null) {
            return context.getResources().getColor(resColorInt);
        } else {
            throw new IllegalArgumentException("ContextUtils sContext should not be null");
        }
    }

    public static Drawable getDrawable(int drawableId) {
        Context context = sContext.get();
        if (context != null) {
            return context.getResources().getDrawable(drawableId, context.getTheme());
        } else {
            throw new IllegalArgumentException("ContextUtils sContext should not be null");
        }
    }

}
