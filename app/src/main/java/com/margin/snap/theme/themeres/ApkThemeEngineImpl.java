package com.margin.snap.theme.themeres;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;

import com.margin.snap.theme.IThemeEngine;
import com.margin.snap.theme.IThemeSwitchListener;
import com.margin.snap.theme.SysThemeEngineImpl;

/**
 * Created by : luxj at 2021-1-18,18:21
 * Description:
 */
public class ApkThemeEngineImpl implements IThemeEngine {
    public static IThemeEngine create() {
        return new SysThemeEngineImpl();
    }

    @Override
    public void init(Context context) {

    }

    @Override
    public void switchTheme(int uiMode) {

    }

    @Override
    public void addThemeListener(IThemeSwitchListener listener) {

    }

    @Override
    public void removeThemeListener(IThemeSwitchListener listener) {

    }

    @Override
    public boolean isDayMode() {
        return false;
    }

    @Override
    public int getTheme() {
        return 0;
    }

    @Override
    public int getColor(int resId) {
        return 0;
    }

    @Override
    public Drawable getDrawable(int resId) {
        return null;
    }

    @Override
    public void release() {

    }

    @Override
    public void executeOnConfigurationChanged(Configuration configuration) {

    }
}
