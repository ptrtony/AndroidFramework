package com.sinata.hi_ui.recyclerview

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
@author cjq
@Date 14/5/2021
@Time 12:01 AM
@Describe:
 */
open abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder?>(data: DATA? = null) {
    open lateinit var mHiAdapter: HiAdapter
    open var mData: DATA? = null

    init {
        this.mData = data
    }

    /**
     * 绑定数据
     */
    open abstract fun onBindData(holder: VH, position: Int)

    /**
     * 获取该item的布局资源
     */
    open fun getItemLayoutRes(): Int {
        return -1
    }

    /**
     * 获取该item的视图
     */
    open fun getItemView(parent: ViewGroup?): View? {
        return null
    }

    open fun setAdapter(hiAdapter: HiAdapter) {
        this.mHiAdapter = hiAdapter
    }

    /**
     * 刷新列表
     */
    open fun refreshItem() {
        if (mHiAdapter != null) mHiAdapter.refreshItem(this as HiDataItem<*, out RecyclerView.ViewHolder>)
    }

    /**
     * 从列表中移除
     */
    open fun removeItem() {
        if (mHiAdapter != null) mHiAdapter.removeItem(this as HiDataItem<*, out RecyclerView.ViewHolder>)
    }

    /**
     * 占几列
     */
    open fun getSpanSize(): Int {
        return 0
    }

    open fun onViewAttachedToWindow(holder: VH) {

    }

    open fun onViewDetachedFromWindow(holder: VH) {

    }
}