package com.sinata.hi_ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @author cjq
 * @Date 10/5/2021
 * @Time 6:21 PM
 * @Describe:
 */
public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D>{
    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     * @param height
     */
    void resetTabHeight(@Px int height);
}
