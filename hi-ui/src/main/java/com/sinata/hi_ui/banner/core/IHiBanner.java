package com.sinata.hi_ui.banner.core;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.sinata.hi_ui.banner.indicator.HiIndicator;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 10:11 PM
 * @Describe:
 */
public interface IHiBanner {

    void setHiIndicator(HiIndicator<?> hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBannerAdapter iBinderAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    interface OnBannerClickListener{
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder,@NonNull HiBannerMo mo,int position);
    }
}
