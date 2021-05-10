package com.sinata.hi_ui.tab.bottom;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author cjq
 * @Date 10/5/2021
 * @Time 5:40 PM
 * @Describe:
 */
public class HiTabBottomInfo<Color> {
    public Class<Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;
    public String iconFont;
    public String defaultIconName;
    public String selectIconName;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabBottomInfo(String name,Bitmap defaultBitmap,Bitmap selectBitmap){
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
        tabType = TabType.BITMAP;
    }


    public HiTabBottomInfo(String name,String iconFont,String defaultIconName,String selectIconName, Color defaultColor ,Color tintColor){
        this.name = name;
        this.iconFont = iconFont;
        this.defaultIconName = defaultIconName;
        this.selectIconName = selectIconName;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.ICON;
    }

}
