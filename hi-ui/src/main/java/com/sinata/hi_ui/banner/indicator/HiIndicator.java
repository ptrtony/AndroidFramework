package com.sinata.hi_ui.banner.indicator;

import android.view.View;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 10:12 PM
 * @Describe: 指示器统一接口
 * 实现该接口来定义你需要样式的指示器
 */
public interface HiIndicator<T extends View> {

    T get();

    /**
     * 初始化Indicator
     *
     *
     * @param count 幻灯片数量
     */
    void onInflate(int count);


    /**
     * 幻灯片切换回掉
     *
     * @param current 切换到幻灯片位置
     *
     * @param count 幻灯片数量
     */
    void onPointChange(int current,int count);
}
