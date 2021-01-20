package com.margin.snap.theme;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

public interface IThemeEngine {

    void init(Context context);

    void switchTheme(@IUiModeManager.ModeInt int uiMode);

    void addThemeListener(IThemeSwitchListener listener);

    void removeThemeListener(IThemeSwitchListener listener);

    boolean isDayMode();

    int getTheme();

    int getColor(@ColorRes int resId);

    Drawable getDrawable(@DrawableRes int resId);

    void release();

    void executeOnConfigurationChanged(Configuration configuration);
}
