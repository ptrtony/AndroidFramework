package com.sinata.framework.ui.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 9:46 PM
 * @Describe:
 */
public class HiTabBottomInfo<Color> {
    public enum TabType{
        BITMAP,ICON
    }

    public Class<? extends Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;
    public String iconFont;

    /**
     * Tips:在Java代码中直接设置iconfont字符串无效，需要定义String.xml
     */
    public String defaultIconName;
    public String selectIconName;
    public Color defaultColor;
    public Color tintColor;

    public TabType tabType;

    public HiTabBottomInfo(String name, Bitmap defaultBitmap, Bitmap selectBitmap) {
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
        tabType = TabType.BITMAP;
    }

    public HiTabBottomInfo(String name, String iconFont, String defaultIconName, String selectIconName, Color defaultColor, Color tintColor) {
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectIconName = selectIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        this.tabType = TabType.ICON;
    }
}
