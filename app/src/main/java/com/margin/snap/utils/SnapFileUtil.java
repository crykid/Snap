package com.margin.snap.utils;

/**
 * Created by : luxj at 2021-1-20,17:15
 * Description:
 */
public class SnapFileUtil {

    private final static String ROOT_DIR = "com.margin.snap";
    private final static String SNAP_THEME_DIR = "/theme";

    public static String getThemeDir() {
        return "/sdcard/" + ROOT_DIR + SNAP_THEME_DIR;
    }
}
