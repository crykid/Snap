package com.margin.snap.theme;

import com.margin.snap.theme.themeres.ApkThemeEngineImpl;

/**
 * Created by : luxj at 2021-1-18,18:16
 * Description:
 */
public class ThemeEngineFactory {

    public final static String ENGINE_SYSTEM = "engine_system";
    public final static String ENGINE_DYNAMIC = "engine_dynamic";

    public static IThemeEngine create(String themeEngine) {
        IThemeEngine engine;
        switch (themeEngine) {
            case ENGINE_SYSTEM:
                engine = SysThemeEngineImpl.create();
                break;
            case ENGINE_DYNAMIC:
                engine = ApkThemeEngineImpl.create();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + themeEngine);
        }
        return engine;
    }
}
