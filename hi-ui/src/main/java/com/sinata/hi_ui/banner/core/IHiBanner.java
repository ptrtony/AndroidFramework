package com.sinata.hi_ui.banner.core;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.sinata.hi_ui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 10:11 PM
 * @Describe:
 */
public interface IHiBanner {
    void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models);

    void setBannerData(@NonNull List<? extends HiBannerMo> models);

    void setHiIndicator(HiIndicator<?> hiIndicator);

    void setAutoPlay(boolean autoPlay);

    void setLoop(boolean loop);

    void setIntervalTime(int intervalTime);

    void setBindAdapter(IBindAdapter iBinderAdapter);

    void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener);

    void setOnBannerClickListener(OnBannerClickListener onBannerClickListener);

    void setScrollerDuration(int duration);

    interface OnBannerClickListener{
        void onBannerClick(@NonNull HiBannerAdapter.HiBannerViewHolder viewHolder,@NonNull HiBannerMo mo,int position);
    }
}
