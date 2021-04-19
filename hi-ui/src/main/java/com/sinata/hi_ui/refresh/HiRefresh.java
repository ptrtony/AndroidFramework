package com.sinata.hi_ui.refresh;

/**
 * @author cjq
 * @Date 17/4/2021
 * @Time 11:55 AM
 * @Describe:
 */
public interface HiRefresh {

    /**
     * 刷新时是否禁止滚动
     * @param disableRefreshScroll 是否禁止滚动
     */
    void setDisableRefreshScroll(boolean disableRefreshScroll);

    /**
     * 刷新完成的API
     */
    void refreshFinished();

    /**
     *  刷新完成
     * @param refreshListener
     */
    void setRefreshListener(HiRefreshListener refreshListener);

    /**
     *  设置下拉刷新的视图
     * @param hiOverView
     */
    void setRefreshOverView(HiOverView hiOverView);


    interface HiRefreshListener{
        void onRefresh();
        boolean enableRefresh();
    }
}
