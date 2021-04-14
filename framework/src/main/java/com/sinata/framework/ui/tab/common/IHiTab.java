package com.sinata.framework.ui.tab.common;

import androidx.annotation.NonNull;
import androidx.annotation.Px;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 9:26 PM
 * @Describe:
 */

public interface IHiTab<D> extends IHiTabLayout.OnTabSelectedListener<D> {

    void setHiTabInfo(@NonNull D data);

    /**
     * 动态修改某个item的大小
     * @param height
     */
    void resetTabHeight(@Px int height);
}
