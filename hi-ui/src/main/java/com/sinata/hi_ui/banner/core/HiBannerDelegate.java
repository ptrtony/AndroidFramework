package com.sinata.hi_ui.banner.core;

import android.content.Context;

import androidx.viewpager.widget.ViewPager;

import com.sinata.hi_ui.banner.HiBanner;
import com.sinata.hi_ui.banner.indicator.HiIndicator;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 11:35 PM
 * @Describe:
 */
public class HiBannerDelegate implements IHiBanner {
    private Context mContext;
    private HiBanner mHiBanner;

    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        this.mContext = context;
        this.mHiBanner = hiBanner;
    }

    @Override
    public void setHiIndicator(HiIndicator<?> hiIndicator) {

    }

    @Override
    public void setAutoPlay(boolean autoPlay) {

    }

    @Override
    public void setLoop(boolean loop) {

    }

    @Override
    public void setIntervalTime(int intervalTime) {

    }

    @Override
    public void setBindAdapter(IBannerAdapter iBinderAdapter) {

    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {

    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {

    }
}
