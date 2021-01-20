package com.margin.snap;

import androidx.appcompat.app.AppCompatActivity;

import android.app.UiModeManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.margin.snap.theme.IThemeEngine;
import com.margin.snap.theme.IThemeSwitchListener;
import com.margin.snap.theme.IUiModeManager;
import com.margin.snap.theme.SnapThemeManager;
import com.margin.snap.utils.TestMethodUtils;
import com.margin.snap.utils.testmethod.ClickTest;

public class ThemeTestActivity extends AppCompatActivity implements IThemeSwitchListener {
    private static final String TAG = "ThemeTestActivity";
    UiModeManager mUiModeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_test);
        TestMethodUtils.find(this, findViewById(R.id.cl_content));
        mUiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
    }

    @Override
    public void onSwitchTheme(String theme, boolean isDay) {
        Log.d(TAG, "onSwitchTheme: ");
    }


    @ClickTest("switchTheme")
    public void a() {
//        int currentTheme = SnapThemeManager.INSTANCE.getCurrentTheme();
        SnapThemeManager.INSTANCE.switchNightMode(IUiModeManager.MODE_NIGHT_YES);

//        if (mUiModeManager.getNightMode() == UiModeManager.MODE_NIGHT_YES) {
//            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
//        } else {
//            mUiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
//        }
    }

    @ClickTest("currentTheme")
    public void b() {
        int currentTheme = SnapThemeManager.INSTANCE.getCurrentTheme();
        Log.d(TAG, "b: currentTheme = " + currentTheme);
    }
}
