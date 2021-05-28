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
abstract class HiDataItem<DATA, VH : RecyclerView.ViewHolder?>(data:DATA) {
    private lateinit var mHiAdapter: HiAdapter
    var mData: DATA? = null

    init {
        this.mData = data
    }

    abstract fun onBindData(holder: VH, position: Int)

    /**
     * 获取该item的布局资源
     */
    open fun getItemLayoutRes(): Int{
        return -1
    }

    /**
     * 获取该item的视图
     */
    open fun getItemView(parent: ViewGroup?): View?{
        return null
    }

    fun setAdapter(hiAdapter: HiAdapter){
        this.mHiAdapter = hiAdapter
    }

    /**
     * 刷新列表
     */
    fun refreshItem(){
        mHiAdapter.refreshItem(this)
    }

    /**
     * 从列表中移除
     */
    fun removeItem(){
        mHiAdapter.removeItem(this)
    }

    /**
     * 占几列
     */
    fun getSpanSize(): Int {
        return -1
    }
}