package com.sinata.hi_ui.recyclerview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_ui.R

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 31/7/2021
 */
class HiRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : RecyclerView(context, attrs, defStyleAttr) {
    private var loadMoreScrollListener: LoadMoreScrollListener? = null
    private var isLoadingMore: Boolean = false
    private var footerView: View? = null

    inner class LoadMoreScrollListener(val prefetchSize: Int, val callback: () -> Unit) :
        OnScrollListener() {
        private val hiAdapter = adapter as HiAdapter

        //咱们这里的强转，因为前面，会有前置的检查
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            //需要根据当前的滑动状态 已决定要不要添加footer view，要不执行上拉加载分页的动作
            if (isLoadingMore) {
                return
            }
            val totalItemCount = hiAdapter.itemCount
            //咱们需要判断当前类标上已经显示的item个数，如果列表上已经已显示的item数量小于0
            if (totalItemCount <= 0) return
            //此时，咱们需要在滑动状态下，拖动状态时，就要判断要不要添加footer view
            //目的就是为了防止列表滑动到底部了 但是footer view 还是没有显示出来
            //需要判断列表是否能够滑动，如何判断RecyclerView是否可以向下滑动
            val canScrollVertical = recyclerView.canScrollVertically(1)
            //还有一种情况，canScrollVertical 咱们是检查他是否能够继续向下滑动
            //特殊情况，咱们的列表已经滑动到底部了，但是分页失败了
            val lastVerticalItem = findLastVerticalItem(recyclerView)
            val arriveBottom = lastVerticalItem >= totalItemCount - 1
            //可以向下滑动以及此时在最后一个位置
            if (newState == SCROLL_STATE_DRAGGING && (canScrollVertical || arriveBottom)) {
                addFooterView()
            }

            if (newState == SCROLL_STATE_IDLE) {
                return
            }

            //预加载,就是，不需要等待，滑动到最后一个item的时候，会触发下一页的加载动作
            val arrivePrefetchPosition = totalItemCount - lastVerticalItem <= prefetchSize
            if (!arrivePrefetchPosition) return

            isLoadingMore = true
            callback()


        }

        private fun findLastVerticalItem(recyclerView: RecyclerView): Int {
            when (val layoutManager = recyclerView.layoutManager) {
                //layoutManager is GridLayoutManager
                is LinearLayoutManager -> {
                    return layoutManager.findLastVisibleItemPosition()
                }

                is StaggeredGridLayoutManager -> {
                    return layoutManager.findFirstVisibleItemPositions(null)[0]
                }
            }
            return -1
        }

        private fun addFooterView() {
            val footerView = getFooterView()
            //但是 这里有一个坑，在一些边界的情况下，会出现多次添加的情况 ,添加之前先remove ->

            //主要是为了规避removeFooterView不及时，在边界下会出现这种情况，footerView 还没从recyclerView上移除，但我们又调用了addFooterView
            //造成重复添加的情况，会抛出add view must call removeview form it parent first exception
            if (footerView.parent != null) {
                footerView.post {
                    hiAdapter.addFooterView(footerView)
                }
            } else {
                hiAdapter.addFooterView(footerView)
            }

        }


        private fun getFooterView(): View {
            if (footerView == null) {
                footerView = LayoutInflater.from(context)
                    .inflate(R.layout.layout_footer_loading, this@HiRecyclerView, false)
            }
            return footerView!!
        }
    }


    fun enableLoadMore(callback: () -> Unit, prefetchSize: Int = 5) {
        if (adapter !is HiAdapter) {
            HiLog.e("enableLoadMore is must use HiAdapter")
            return
        }

        loadMoreScrollListener = LoadMoreScrollListener(prefetchSize, callback)
        addOnScrollListener(loadMoreScrollListener!!)
    }

    fun disableLoadMore(){
        if (adapter !is HiAdapter) {
            HiLog.e("disableLoadMore is must use HiAdapter")
            return
        }

        val hiAdapter  = adapter as HiAdapter
        footerView?.let {
            if (footerView!!.parent != null){
                hiAdapter.removeFooterView(footerView!!)
            }
        }

        loadMoreScrollListener?.let {
            removeOnScrollListener(loadMoreScrollListener!!)
            loadMoreScrollListener = null
            footerView = null
            isLoadingMore = false
        }

    }


    fun isLoading():Boolean{
        return isLoadingMore
    }

    fun loadFinished(success:Boolean){
        if (adapter !is HiAdapter) {
            HiLog.e("loadFinished is must use HiAdapter")
            return
        }
        isLoadingMore = false
        val hiAdapter = adapter as HiAdapter
        if (!success){
            footerView?.let {
                if (footerView!!.parent != null){
                    hiAdapter.removeFooterView(footerView!!)
                }
            }
        }else{
            //nothing to do
        }

    }
}