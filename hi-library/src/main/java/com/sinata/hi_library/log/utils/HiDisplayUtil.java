package com.sinata.hi_library.log.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

/**
 * @author cjq
 * @Date 7/4/2021
 * @Time 10:19 PM
 * @Describe:
 */
public class HiDisplayUtil{

    public static int px2dp(float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pxValue, Resources.getSystem().getDisplayMetrics());
    }


    public static int dp2Px(float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, pxValue, Resources.getSystem().getDisplayMetrics());
    }

    public static int px2sp(float pxValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, pxValue, Resources.getSystem().getDisplayMetrics());
    }

    public static int dp2px(Resources resources, float dpValue) {
        float scale = resources.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int sp2px(Resources resources, float spValue) {
        float fontScale = resources.getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }



    /**
     * 获取高度
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity){
        WindowManager manager = activity.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    public static int getDisplayWidthInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.x;
        }
        return 0;

    }

    public static int getDisplayHeightInPx(@NonNull Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (wm != null) {
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            return size.y;
        }
        return 0;
    }

//    public static int getStatusBarDimensionPx() {
//        int statusBarHeight = 0;
//        Resources res = AppGlobals.INSTANCE.get().getResources();
//        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            statusBarHeight = res.getDimensionPixelSize(resourceId);
//        }
//        return statusBarHeight;
//    }



}
