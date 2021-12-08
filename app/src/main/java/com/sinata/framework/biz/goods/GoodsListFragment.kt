package com.sinata.framework.biz.goods

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.component.HiAbsListFragment
import com.sinata.framework.fragment.home.GoodsItem
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.framework.http.api.GoodsApi
import com.sinata.framework.model.SubcategoryGoodsModel
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/8/2021
 */
class GoodsListFragment : HiAbsListFragment() {

    private var categoryId: String? = null
    private var subcategoryId: String? = null

    companion object {
        const val CATEGORY_ID = "CATEGORY_ID"
        const val SUBCATEGORY_ID = "SUBCATEGORY_ID"
        fun newInstance(categoryId: String?, subcategoryId: String?): GoodsListFragment {
            val args = Bundle()
            args.putString(CATEGORY_ID, categoryId)
            args.putString(SUBCATEGORY_ID, subcategoryId)
            val fragment = GoodsListFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments
        categoryId = args?.getString(CATEGORY_ID, "")
        subcategoryId = args?.getString(SUBCATEGORY_ID, "")
        enableLoadMore {
            loadData()
        }

        loadData()
    }

    override fun onRefresh() {
        super.onRefresh()
        loadData()
    }

    private fun loadData() {
        ApiFactory.create(GoodsApi::class.java)
            .queryCategoryGoodsList(
                categoryId = categoryId ?: "",
                subcategoryId = subcategoryId ?: "",
                pageSize = 10,
                pageIndex = pageIndex
            )
            .enqueue(object : HiCallback<SubcategoryGoodsModel> {
                override fun onSuccess(response: HiResponse<SubcategoryGoodsModel>) {
                    if (response.successful() && response.data != null) {
                        onQueryCategoryListSuccess(response.data!!)
                    } else {
                        finishRefresh(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    finishRefresh(null)
                }

            })
    }

    private fun onQueryCategoryListSuccess(data: SubcategoryGoodsModel) {

        val dataItems = mutableListOf<GoodsItem>()
        for (goodsModel in data.list) {
            val goodsItem = GoodsItem(goodsModel, false)
            dataItems.add(goodsItem)
        }
        finishRefresh(dataItems)
    }

    override fun createLayoutManager(): RecyclerView.LayoutManager {
        return GridLayoutManager(context,2)
    }


}