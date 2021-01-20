package com.margin.snap.theme;

import android.app.UiModeManager;
import android.content.Context;

/**
 * Created by : luxj at 2021-1-18,11:24
 * Description:
 */
public class SysUiModeManager implements IUiModeManager {
    private UiModeManager mUiModeManager;

    @Override
    public void init(Context context) {
        mUiModeManager = (UiModeManager) context.getSystemService(Context.UI_MODE_SERVICE);
    }

    @Override
    public void setMode(@ModeInt int mode) {
        mUiModeManager.setNightMode(mode);
    }

    @Override
    public int getCurrentMode() {
        if (mUiModeManager != null) {
            return mUiModeManager.getNightMode();
        }
        return 0;
    }
}
