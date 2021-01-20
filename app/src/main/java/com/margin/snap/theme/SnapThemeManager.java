package com.margin.snap.theme;

import android.app.UiModeManager;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by : luxj at 2021-1-20,14:06
 * Description: 主题管理
 * <p>
 * 包含昼夜模式切换、主题/皮肤切换
 */
public enum SnapThemeManager {
    INSTANCE;
    private final static Map<Integer, Integer> theme = new HashMap<>();


    public static SnapThemeManager getInstance() {
        return INSTANCE;
    }

    private IThemeEngine mNightEngine;
    private IThemeEngine mThemeEngine;

    public void init(Context context) {
        mThemeEngine = ThemeEngineFactory.create(ThemeEngineFactory.ENGINE_DYNAMIC);
        mNightEngine = ThemeEngineFactory.create(ThemeEngineFactory.ENGINE_SYSTEM);
        mThemeEngine.init(context);
    }

    public void addThemeSwitchListener(IThemeSwitchListener listener) {
        mThemeEngine.addThemeListener(listener);
    }

    public void removeThemeSwitchListener(IThemeSwitchListener listener) {
        mThemeEngine.removeThemeListener(listener);
    }

    /**
     * 设置深色/浅色模式
     *
     * @param theme
     */
    public void switchNightMode(int theme) {
        int localTheme = UiModeManager.MODE_NIGHT_NO;
        switch (theme) {
            case IUiModeManager.MODE_NIGHT_AUTO:
                localTheme = UiModeManager.MODE_NIGHT_AUTO;
                break;
            case IUiModeManager.MODE_NIGHT_NO:
                localTheme = UiModeManager.MODE_NIGHT_NO;
                break;
            case IUiModeManager.MODE_NIGHT_YES:
                localTheme = UiModeManager.MODE_NIGHT_YES;
                break;
        }
        mThemeEngine.switchTheme(localTheme);
    }

    /**
     * 设置主题
     *
     * @param theme
     */
    public void switchTheme(int theme) {

    }

    /**
     * 获取当前主题
     *
     * @return
     */
    public int getCurrentTheme() {
        int theme = mThemeEngine.getTheme();

        return theme;
    }

    /**
     * 获取当前深色/浅色模式
     *
     * @return
     */
    public int getNightMode() {
        return mNightEngine.getTheme();
    }
}
