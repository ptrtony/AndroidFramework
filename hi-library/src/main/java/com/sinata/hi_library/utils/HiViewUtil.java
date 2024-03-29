package com.sinata.hi_library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 10:20 PM
 * @Describe:
 */
public class HiViewUtil {

    /**
     * 获取指定类型的View
     *
     * @param group ViewGroup
     * @param cls
     * @param <T>
     * @return 获取指定类型的View
     */
    public static <T> T findTypeView(@Nullable ViewGroup group, Class<T> cls) {
        if (group == null) {
            return null;
        }
        Deque<View> deque = new ArrayDeque<>();
        deque.add(group);
        while (!deque.isEmpty()) {
            View node = deque.removeFirst();
            if (cls.isInstance(node)) {
                return cls.cast(node);
            } else if (node instanceof ViewGroup) {
                ViewGroup container = (ViewGroup) node;
                for (int i = 0, count = container.getChildCount(); i < count; i++) {
                    deque.add(((ViewGroup) node).getChildAt(i));
                }
            }

        }
        return null;
    }


    public static boolean isActivityDestroy(Context context) {
        Activity activity = findActivity(context);
        if (activity != null){
            return activity.isDestroyed() || activity.isFinishing();
        }
        return true;
    }


    private static Activity findActivity(Context context){
        if (context instanceof Activity){
            return (Activity) context;
        }

        if (context instanceof ContextWrapper){
            return findActivity(context);
        }
        return null;
    }


    /**
     * 浅色主题
     */
    public static boolean lightMode(){
       Context context =  AppGlobal.INSTANCE.get();
        int mode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return mode == Configuration.UI_MODE_NIGHT_NO;
    }


}
