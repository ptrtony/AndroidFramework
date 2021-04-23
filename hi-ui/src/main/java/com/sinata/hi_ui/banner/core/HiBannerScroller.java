package com.sinata.hi_ui.banner.core;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * @author cjq
 * @Date 22/4/2021
 * @Time 10:53 PM
 * @Describe:
 */
public class HiBannerScroller extends Scroller {
    private int mDuration;

    public HiBannerScroller(Context context, int duration) {
        super(context);
        mDuration = duration;
    }

    public HiBannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public HiBannerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}
