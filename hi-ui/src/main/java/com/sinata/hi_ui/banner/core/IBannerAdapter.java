package com.sinata.hi_ui.banner.core;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 10:16 PM
 * @Describe: HiBanner的数据绑定接口，基于该接口可以实现数据的绑定和框架层的解藕
 */
public interface IBannerAdapter {
    void onBind(HiBannerAdapter.HiBannerViewHolder viewHolder, HiBannerMo mo, int position);
}
