package com.sinata.hi_ui.refresh;

/**
 * @author cjq
 * @Date 17/4/2021
 * @Time 12:07 PM
 * @Describe:
 */

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 下拉刷新的OverView视图，可以重载这个类来定义自己的OverView
 */
public abstract class HiOverView extends FrameLayout {

    public enum HiRefreshState {
        /**
         * 初始化
         */
        STATE_INIT,

        /**
         * Header展示的状态
         */
        STATE_VISIBLE,

        /**
         * 超出可以刷新距离的状态
         */
        STATE_REFRESH,

        /**
         * 超出可刷新距离的状态
         */
        STATE_OVER,
        /**
         * 超出刷新位置松开后的状态
         */
        STATE_OVER_RELEASE
    }

    protected HiRefreshState mState = HiRefreshState.STATE_INIT;

    /**
     * 触发下拉刷新最小的距离
     */
    public int mPullRefreshHeight;

    /**
     * 最小阻尼系数 ：下拉的高度和滑动距离之间的比值
     */
    public float minDump = 1.6f;

    /**
     * 最大阻尼
     */
    public float maxDump = 2.2f;


    public HiOverView(@NonNull Context context) {
        super(context);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public abstract void init();

    public abstract void onScroll(int Y,int pullRefreshHeight);
    /**
     * 显示OverLay
     */
    public abstract void onVisible();

    /**
     * 超过Overlay ，释放就会加载
     */
    public abstract void onOver();

    /**
     * 开始刷新
     */
    public abstract void onRefresh();

    /**
     * 刷新完成
     */
    public abstract void onFinish();

    /**
     * 设置状态的方法
     */
    public void setStatus(HiRefreshState status){
        this.mState = status;
    }

    /**
     * 获取状态的方法
     */
    public HiRefreshState getState(){
        return mState;
    }


}
