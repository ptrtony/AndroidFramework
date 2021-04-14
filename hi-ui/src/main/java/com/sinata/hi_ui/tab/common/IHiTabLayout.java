package com.sinata.hi_ui.tab.common;

import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 9:17 PM
 * @Describe:
 */
public interface IHiTabLayout<Tab extends ViewGroup, D> {
    Tab findTab(@NonNull D data);

    void addTabSelectedChangeListener(OnTabSelectedListener<D> listener);

    void defaultSelected(@NonNull D defaultInfo);

    void inflateInfo(@NonNull List<D> infoList);

    interface OnTabSelectedListener<D>{
        void onTabSelectedChange(int index, @NonNull D preInfo, @NonNull D nextInfo);
    }
}
