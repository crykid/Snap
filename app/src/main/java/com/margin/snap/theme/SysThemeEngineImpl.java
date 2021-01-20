package com.margin.snap.theme;

import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;

import com.margin.snap.utils.CollectionUtils;
import com.margin.snap.utils.ContextUtils;

import java.util.ArrayList;
import java.util.List;

public class SysThemeEngineImpl implements IThemeEngine {
    private static final String TAG = "ThemeEngine";
    private Context mContext;
    private boolean mIsDayMode = true;
    private IUiModeManager mUiModeManager;
    private int mUiMode = Configuration.UI_MODE_NIGHT_NO;
    private final List<IThemeSwitchListener> mThemeSwitchListeners = new ArrayList<>();
    private ComponentCallbacks mComponentCallbacks = new ComponentCallbacks() {
        @Override
        public void onConfigurationChanged(@NonNull Configuration configuration) {
            Log.d(TAG, "onConfigurationChanged: uiMode = " + configuration.uiMode);
            executeOnConfigurationChanged(configuration);
        }

        @Override
        public void onLowMemory() {

        }
    };

    public static IThemeEngine create() {
        return new SysThemeEngineImpl();
    }

    @Override
    public void init(Context context) {
        mContext = context;
        mContext.registerComponentCallbacks(mComponentCallbacks);
        mUiModeManager = new SysUiModeManager();
        mUiModeManager.init(context);
//        mIsDayMode = ;
    }

    @Override
    public void switchTheme( int uiMode) {
        if (mUiModeManager != null) {
            mUiModeManager.setMode(uiMode);
        }
    }

    @Override
    public void release() {

    }

    @Override
    public void addThemeListener(IThemeSwitchListener listener) {
        CollectionUtils.listAdd(mThemeSwitchListeners, listener);
    }

    @Override
    public void removeThemeListener(IThemeSwitchListener listener) {
        CollectionUtils.listRemove(mThemeSwitchListeners, listener);
    }

    @Override
    public boolean isDayMode() {
//        return mUiModeManager.getCurrentMode() == IUiModeManager.MODE_NIGHT_NO;
        return mIsDayMode;
    }

    @Override
    public int getTheme() {
        return mUiModeManager.getCurrentMode();
    }

    @Override
    public int getColor(int resId) {
        return ContextUtils.getColor(resId);
    }

    @Override
    public Drawable getDrawable(int resId) {
        return ContextUtils.getDrawable(resId);
    }

    @Override
    public void executeOnConfigurationChanged(Configuration configuration) {
        if (configuration.uiMode != mUiMode) {
            boolean isDay = isDayMode();
            if (isDay != mIsDayMode) {
                mIsDayMode = isDay;
                Log.d(TAG, "executeOnConfigurationChanged: isDayMode = " + mIsDayMode);
                for (IThemeSwitchListener themeSwitchListener : mThemeSwitchListeners) {
                    try {
                        themeSwitchListener.onSwitchTheme(null, isDayMode());
                    } catch (Exception e) {
                    }
                }

            }
        }
    }
}
