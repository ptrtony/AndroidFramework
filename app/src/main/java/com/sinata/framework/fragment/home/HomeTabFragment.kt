package com.sinata.framework.fragment.home

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.component.HiAbsListFragment
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.HomeApi
import com.sinata.framework.model.HomeModel
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_ui.recyclerview.HiDataItem

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 5/8/2021
 */
class HomeTabFragment : HiAbsListFragment() {

    private var categoryId: String? = null

    companion object {
        const val DEFAULT_HOT_TAB_CATEGORY_ID = "1"
        fun newInstance(categoryId: String): HomeTabFragment {
            val args = Bundle()
            args.putString("categoryId", categoryId)
            val fragment = HomeTabFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        categoryId = arguments?.getString("categoryId", DEFAULT_HOT_TAB_CATEGORY_ID)
        super.onViewCreated(view, savedInstanceState)
        queryTabCategoryList()

        enableLoadMore {
            queryTabCategoryList()
        }
    }



    override fun onRefresh() {
        super.onRefresh()
        queryTabCategoryList()
    }


    override fun createLayoutManager(): RecyclerView.LayoutManager {
        val isHotTab = TextUtils.equals(categoryId, DEFAULT_HOT_TAB_CATEGORY_ID)
        return if (isHotTab){
            super.createLayoutManager()
        }else{
            GridLayoutManager(context,2)
        }
    }


    private fun queryTabCategoryList() {
        ApiFactory
            .create(HomeApi::class.java)
            .queryTabCategoryList(categoryId!!,pageIndex,10)
            .enqueue(object : HiCallback<HomeModel> {
                override fun onSuccess(response: HiResponse<HomeModel>) {
                    if (response.successful() && response.data != null) {
                        updateUI(response.data!!)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    throwable.printStackTrace()
                }

            })

    }

    private fun updateUI(data: HomeModel) {
        if (!isAlive) return
        val dataItems = mutableListOf<HiDataItem<*,RecyclerView.ViewHolder>>()
        data.bannerList?.let {
            dataItems.add(BannerItem(it))
        }

        data.subCategoryList?.let {
            dataItems.add(GridItem(it))
        }

        data.goodsList?.forEach {
            dataItems.add(GoodsItem(it,TextUtils.equals(categoryId, DEFAULT_HOT_TAB_CATEGORY_ID)) as HiDataItem<*, RecyclerView.ViewHolder>)
        }
        finishRefresh(dataItems)
    }
}