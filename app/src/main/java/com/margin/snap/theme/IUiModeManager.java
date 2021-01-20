package com.margin.snap.theme;

import android.content.Context;

import androidx.annotation.IntDef;

public interface IUiModeManager {
    int MODE_NIGHT_AUTO = 0;
    int MODE_NIGHT_NO = 1;
    int MODE_NIGHT_YES = 2;

    void init(Context context);

    void setMode(@ModeInt int mode);

    int getCurrentMode();


    @IntDef({MODE_NIGHT_AUTO, MODE_NIGHT_NO, MODE_NIGHT_YES})
    @interface ModeInt {

    }
}
