package com.sinata.hi_ui.top;

import android.graphics.Bitmap;

import androidx.fragment.app.Fragment;

/**
 * @author cjq
 * @Date 10/5/2021
 * @Time 5:58 PM
 * @Describe:
 */
public class HiTabTopInfo<Color> {
    enum TabType {
        BITMAP, TEXT
    }

    public Class<Fragment> fragment;
    public String name;
    public Bitmap defaultBitmap;
    public Bitmap selectBitmap;
    public Color defaultColor;
    public Color tintColor;
    public TabType tabType;

    public HiTabTopInfo(String name,Bitmap defaultBitmap,Bitmap selectBitmap){
        this.name = name;
        this.defaultBitmap = defaultBitmap;
        this.selectBitmap = selectBitmap;
        tabType = TabType.BITMAP;
    }

    public HiTabTopInfo(String name,Color defaultColor,Color tintColor){
        this.name = name;
        this.defaultColor = defaultColor;
        this.tintColor = tintColor;
        tabType = TabType.TEXT;
    }

}
