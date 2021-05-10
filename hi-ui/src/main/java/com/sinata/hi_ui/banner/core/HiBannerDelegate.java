package com.sinata.hi_ui.banner.core;

import android.content.Context;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.sinata.hi_ui.R;
import com.sinata.hi_ui.banner.HiBanner;
import com.sinata.hi_ui.banner.indicator.HiCircleIndicator_java;
import com.sinata.hi_ui.banner.indicator.HiIndicator;

import java.util.List;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 11:35 PM
 * @Describe:
 */
public class HiBannerDelegate implements IHiBanner, ViewPager.OnPageChangeListener {
    private Context mContext;
    private HiBanner mHiBanner;
    private HiBannerAdapter mHiBannerAdapter;
    private HiIndicator<?> mHiIndicator;
    private boolean mAutoPlay;
    private boolean mLoop;
    private List<? extends HiBannerMo> mHiBannerMos;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private int mIntervalTime = 5000;
    private IHiBanner.OnBannerClickListener mOnBannerClickListener;
    private HiViewPager mHiViewPager;
    private int mScrollDuration;

    public HiBannerDelegate(Context context, HiBanner hiBanner) {
        this.mContext = context;
        this.mHiBanner = hiBanner;
    }

    @Override
    public void setBannerData(int layoutResId, @NonNull List<? extends HiBannerMo> models) {
        mHiBannerMos = models;
        init(layoutResId);
    }

    @Override
    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        setBannerData(R.layout.hi_banner_item_image, models);
    }

    @Override
    public void setHiIndicator(HiIndicator<?> hiIndicator) {
        this.mHiIndicator = hiIndicator;
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        this.mAutoPlay = autoPlay;
        if (mHiViewPager != null) mHiViewPager.setAutoPlay(autoPlay);
        if (mHiBannerAdapter != null) mHiBannerAdapter.setAutoPlay(autoPlay);
    }

    @Override
    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }

    @Override
    public void setIntervalTime(int intervalTime) {
        if (intervalTime > 0) {
            this.mIntervalTime = intervalTime;
        }
    }

    @Override
    public void setBindAdapter(IBindAdapter iBinderAdapter) {
        mHiBannerAdapter.setBindAdapter(iBinderAdapter);
    }

    @Override
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    @Override
    public void setScrollerDuration(int duration) {
        this.mScrollDuration = duration;
        if (mHiViewPager != null && duration > 0)
            mHiViewPager.setScrollDuration(duration);
    }

    private void init(int layoutResId) {
        if (mHiBannerAdapter == null) {
            mHiBannerAdapter = new HiBannerAdapter(mContext);
        }

        if (mHiIndicator == null) {
            mHiIndicator = new HiCircleIndicator_java(mContext);
        }
        mHiIndicator.onInflate(mHiBannerMos.size());
        mHiBannerAdapter.setLayoutResId(layoutResId);
        mHiBannerAdapter.setBannerData(mHiBannerMos);
        mHiBannerAdapter.setAutoPlay(mAutoPlay);
        mHiBannerAdapter.setLoop(mLoop);
        mHiBannerAdapter.setOnBannerClickListener(mOnBannerClickListener);

        mHiViewPager = new HiViewPager(mContext);
        mHiViewPager.addOnPageChangeListener(this);
        mHiViewPager.setAutoPlay(mAutoPlay);
        mHiViewPager.setIntervalTime(mIntervalTime);
        mHiViewPager.setAdapter(mHiBannerAdapter);
        if (mScrollDuration > 0) mHiViewPager.setScrollDuration(mScrollDuration);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        if ((mLoop || mAutoPlay) && mHiBannerAdapter.getRealCount() != 0) {
            //无限轮播关键点：使第一张能反向滑动到最后一张，以达到无限滚动的效果
            int firstItem = mHiBannerAdapter.getFirstItem();
            mHiViewPager.setCurrentItem(firstItem, false);
        }
        //清除所有的view
        mHiBanner.removeAllViews();
        mHiBanner.addView(mHiViewPager, layoutParams);
        mHiBanner.addView(mHiIndicator.get(), layoutParams);
//        mHiViewPager.setBackgroundResource(android.R.color.holo_red_dark);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (null != mOnPageChangeListener && mHiBannerAdapter.getRealCount() != 0) {
            mOnPageChangeListener.onPageScrolled(position % mHiBannerAdapter.getRealCount(), positionOffset, positionOffsetPixels);
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (mHiBannerAdapter.getRealCount() == 0) {
            return;
        }
        position = position % mHiBannerAdapter.getRealCount();
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (null != mHiIndicator) {
            mHiIndicator.onPointChange(position, mHiBannerAdapter.getRealCount());
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (null != mOnPageChangeListener) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
    }
}
