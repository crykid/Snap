package com.margin.snap.theme.themeres;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by : luxj at 2021-1-20,17:05
 * Description:
 */
public enum ThemeResManager {
    INSTANCE;
    private List<String> mThemesName = new ArrayList<>();
    private List<ThemeRes> mThemeResList = new ArrayList<>();

    /**
     * 加载主题资源
     *
     * @param path 路径
     */
    public void loadThemeRes(String path) {
        ThemeRes themeRes = ThemeResParser.parse(path);
        if (themeRes != null) {
            mThemeResList.add(themeRes);
        }
    }

    /**
     * 批量加载主题资源
     *
     * @param dir SnapFileUtil.getThemeDir();
     */
    public void loadThemeReses(String dir) {

        loadThemeRes("");
    }
}
