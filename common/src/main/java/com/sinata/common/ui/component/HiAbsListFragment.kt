package com.sinata.common.ui.component

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.core.widget.ContentLoadingProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.R
import com.sinata.common.ui.view.EmptyView
import com.sinata.hi_ui.recyclerview.HiAdapter
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiRecyclerView
import com.sinata.hi_ui.refresh.HiOverView
import com.sinata.hi_ui.refresh.HiRefresh
import com.sinata.hi_ui.refresh.HiRefreshLayout
import com.sinata.hi_ui.refresh.HiTextOverView
import kotlinx.android.synthetic.main.fragment_list.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 31/7/2021
 */
open class HiAbsListFragment : HiBaseFragment(), HiRefresh.HiRefreshListener {
    var pageIndex: Int = 1
    private lateinit var hiAdapter: HiAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var refreshHeaderView: HiTextOverView
    private var refreshLayout: HiRefreshLayout? = null
    private var loadingView: ContentLoadingProgressBar? = null
    private var emptyView: EmptyView? = null
    private var recyclerView: HiRecyclerView? = null

    companion object {
        const val PREFETCH_SIZE = 5
    }


    override fun getLayoutId(): Int = R.layout.fragment_list

    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        this.refreshLayout = refresh_layout
        this.emptyView = empty_view
        this.loadingView = loading
        this.recyclerView = recycler_view

        refreshHeaderView = HiTextOverView(context)
        refreshLayout?.setRefreshOverView(refreshHeaderView)
        refreshLayout?.setRefreshListener(this)
        layoutManager = createLayoutManager()
        hiAdapter = HiAdapter(view.context)
        recyclerView?.apply {
            layoutManager = this@HiAbsListFragment.layoutManager
            adapter = this@HiAbsListFragment.hiAdapter
        }
        emptyView?.apply {
            visibility = View.GONE
            setIcon(R.string.list_empty)
            setDesc(context.getString(R.string.list_empty_desc))
            setButton(context.getString(R.string.list_empty_action), View.OnClickListener {
                onRefresh()
            })
        }

        pageIndex = 1
    }

    fun finishRefresh(dataItems: List<HiDataItem<*, out RecyclerView.ViewHolder>>?) {
        val success = dataItems != null && dataItems.isNotEmpty()
        //?????????????????????????????????????????????????????????????????? ??????????????????????????????????????? ????????????????????????
        val refresh = pageIndex == 1
        if (refresh) {
            loadingView?.visibility = View.GONE
            refreshLayout?.refreshFinished()
            if (success) {
                emptyView?.visibility = View.GONE
                hiAdapter.clearItems()
                hiAdapter.addItems(dataItems!!, true)
            } else {
                //????????????????????????????????????????????????????????????????????????
                if (hiAdapter.itemCount <= 0) {
                    emptyView?.visibility = View.VISIBLE
                }
            }
        } else {
            if (success) {
                hiAdapter.addItems(dataItems!!, true)
            }
            recyclerView?.loadFinished(true)
        }

    }

    open fun createLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(context)
    }

    @CallSuper
    override fun onRefresh() {
        if (recyclerView?.isLoading() == true){
            //???????????????
            refreshLayout?.post {
                refreshLayout?.refreshFinished()
            }
            return
        }
        pageIndex = 1

    }

    override fun enableRefresh(): Boolean {
        return true
    }

    fun enableLoadMore(callback: () -> Unit) {
        //????????????????????????????????????????????????
        recyclerView?.enableLoadMore({
            if (refreshHeaderView.state == HiOverView.HiRefreshState.STATE_REFRESH) {
                recyclerView?.loadFinished(false)
                return@enableLoadMore
            }
            pageIndex++
            callback()
        }, PREFETCH_SIZE)
    }

    fun disableLoadMore(){
        recyclerView?.disableLoadMore()
    }

}