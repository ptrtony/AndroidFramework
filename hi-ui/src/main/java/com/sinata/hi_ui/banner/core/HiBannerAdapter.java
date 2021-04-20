package com.sinata.hi_ui.banner.core;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

/**
 * @author cjq
 * @Date 20/4/2021
 * @Time 10:32 PM
 * @Describe:
 */
public class HiBannerAdapter extends PagerAdapter {
    private Context mContext;
    private SparseArray<HiBannerViewHolder> mCacheViews;
    private IHiBanner.OnBannerClickListener mOnBannerClickListener;
    private IBannerAdapter mBinderAdapter;
    private List<? extends HiBannerMo> models;


    /**
     * 是否开启自动轮播
     */
    private boolean mAutoPlay;

    /**
     * 非自动轮播状态下是否可以循环轮播
     */
    private boolean mLoop = false;

    private int mLayoutResId = -1;


    public HiBannerAdapter(Context context) {
        this.mContext = context;
    }


    public void setBannerData(@NonNull List<? extends HiBannerMo> models) {
        this.models = models;
        //初始化数据
        initCacheView();
        notifyDataSetChanged();
    }


    private void initCacheView() {
        mCacheViews = new SparseArray<>();
        for (int i = 0; i < models.size(); i++) {
            HiBannerViewHolder viewHolder = new HiBannerViewHolder(createView(LayoutInflater.from(mContext), null));
            mCacheViews.put(i, viewHolder);
        }
    }

    private View createView(LayoutInflater layoutInflater, ViewGroup parent) {
        if (mLayoutResId == -1) {
            throw new IllegalArgumentException("you must be set setLayoutResId first");
        }
        return layoutInflater.inflate(mLayoutResId, parent, false);
    }

    public void setLayoutResId(@LayoutRes int layoutResId) {
        this.mLayoutResId = layoutResId;
    }

    public void setAdapter(IBannerAdapter bannerAdapter) {
        this.mBinderAdapter = bannerAdapter;
    }

    public void setOnBannerClickListener(IHiBanner.OnBannerClickListener onBannerClickListener) {
        this.mOnBannerClickListener = onBannerClickListener;
    }

    public void setAutoPlay(boolean mAutoPlay) {
        this.mAutoPlay = mAutoPlay;
    }

    public void setLoop(boolean loop) {
        this.mLoop = loop;
    }


    @Override
    public int getCount() {
        //无限轮播的关键点
        return mAutoPlay ? Integer.MAX_VALUE : (mLoop ? Integer.MAX_VALUE : getRealCount());
    }

    /**
     * 获取初始展示的item的位置
     */

    public int getFirstItem() {
        return Integer.MAX_VALUE / 2 - (Integer.MAX_VALUE / 2) % getRealCount();
    }


    /**
     * 获取真实轮播的幻灯片数量
     *
     * @return
     */
    public int getRealCount() {
        return models == null ? 0 : models.size();
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int realPosition = position;
        if (getRealCount() > 0) {
            realPosition = position % getRealCount();
        }
        HiBannerViewHolder viewHolder = mCacheViews.get(realPosition);
        if (container.equals(viewHolder.rootView.getParent())) {
            container.removeView(viewHolder.rootView);
        }
        //绑定数据
        onBind(viewHolder, models.get(realPosition), realPosition);
        if (viewHolder.rootView.getParent() != null) {
            ((ViewGroup) viewHolder.rootView.getParent()).removeView(viewHolder.rootView);
        }
        container.addView(viewHolder.rootView);
        return super.instantiateItem(container, position);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        //让item每次都刷新
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);

    }

    protected void onBind(HiBannerAdapter.HiBannerViewHolder viewHolder, HiBannerMo mo, int position) {
        viewHolder.rootView.setOnClickListener(v -> {
            if (mOnBannerClickListener != null) {
                mOnBannerClickListener.onBannerClick(viewHolder, mo, position);
            }
        });

        if (mBinderAdapter != null) {
            mBinderAdapter.onBind(viewHolder, mo, position);
        }
    }


    public static class HiBannerViewHolder {
        SparseArray<View> viewSparseArray;
        View rootView;

        public HiBannerViewHolder(View view) {
            this.rootView = view;
        }

        public View getRootView() {
            return rootView;
        }

        public <V extends View> V findViewById(int id) {
            if (!(rootView instanceof ViewGroup)) {
                return (V) rootView;
            }
            if (viewSparseArray == null) {
                viewSparseArray = new SparseArray<>();
            }
            V childView = (V) viewSparseArray.get(id);
            if (childView == null) {
                childView = rootView.findViewById(id);
                viewSparseArray.put(id, childView);
            }
            return childView;
        }
    }
}
