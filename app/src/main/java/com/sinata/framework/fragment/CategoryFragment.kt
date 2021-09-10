package com.sinata.framework.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.sinata.common.ui.component.HiBaseFragment
import com.sinata.common.ui.view.EmptyView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.R
import com.sinata.framework.arouter.HiRoute
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.ApiFactory.create
import com.sinata.framework.http.api.CategoryApi
import com.sinata.framework.model.SubCategory
import com.sinata.framework.model.TabCategory
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_ui.bottom.HiTabBottomLayout
import kotlinx.android.synthetic.main.fragment_category.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 20/8/2021
 */
class CategoryFragment : HiBaseFragment() {
    private var SPAN_COUNT = 3
    private var emptyView: EmptyView? = null
    private var subCategoryListCache = mutableMapOf<String, List<SubCategory>>()
    override fun getLayoutId(): Int {
        return R.layout.fragment_category
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HiTabBottomLayout.clipBottomPadding(root_container)
        queryCategoryList()
    }

    private fun queryCategoryList() {
        create(CategoryApi::class.java).queryCategoryList()
            .enqueue(object : HiCallback<List<TabCategory>> {
                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    if (response.successful() && response.data != null) {
                        onQueryCategorySuccess(response.data!!)
                    } else {
                        showEmptyView()
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    showEmptyView()
                }
            })
    }

    private fun onQueryCategorySuccess(data: List<TabCategory>) {
        if (!isAlive) return
        emptyView!!.visibility = View.GONE
        slider_view.visibility = View.VISIBLE
        slider_view.bindMenuView(itemCount = data.size, onBindView = { viewHolder, position ->
            viewHolder.findViewById<TextView>(R.id.menu_item_title).text =
                data[position].categoryName
        }, onItemClick = { viewHolder, position ->
            val categoryId = data[position].categoryId
            val subcategoryList = subCategoryListCache[categoryId]
            if (subcategoryList != null && subcategoryList.isNotEmpty()) {
                onQuerySubCategoryListSuccess(subcategoryList)
            } else {
                querySubCategoryList(data[position].categoryId)
            }

        })
    }

    private fun querySubCategoryList(categoryId: String) {
        ApiFactory.create(CategoryApi::class.java).querySubcategoryList(categoryId)
            .enqueue(object : HiCallback<List<SubCategory>> {
                override fun onSuccess(response: HiResponse<List<SubCategory>>) {
                    if (response.successful() && response.data != null) {
                        onQuerySubCategoryListSuccess(response.data!!)

                        if (!subCategoryListCache.containsKey(categoryId)) {
                            subCategoryListCache.put(categoryId, response.data!!)
                        }
                    }
                }

                override fun onFailed(throwable: Throwable) {

                }

            })
    }

    private val layoutManager = GridLayoutManager(context, SPAN_COUNT)
    private val decoration =
        CategoryItemDecoration({ position -> subcategoryList[position].groupName }, SPAN_COUNT)
    private var groupSpanSizeOffset = SparseArray<Int>()
    private val spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {

        override fun getSpanSize(position: Int): Int {
            var spanSize = 1
            val groupName = subcategoryList[position].groupName
            val nextGroupName =
                if (position + 1 < subcategoryList.size) subcategoryList[position + 1].groupName else null
            if (TextUtils.equals(groupName, nextGroupName)) {
                spanSize = 1
            } else {
                val indexOfKey = groupSpanSizeOffset.get(position)
                val size = groupSpanSizeOffset.size()
                val lastGroupOffset = if (size <= 0) 0 else if (indexOfKey >= 0) {
                    //说明当前的偏移量已经记录，已经存在groupSpanSizeOffset
                    if (indexOfKey == 0) 0 else groupSpanSizeOffset.valueAt(indexOfKey - 1)
                } else {
                    //说明当前的偏移量，还没有存在于groupSpanSizeOffset，这种情况发生在第一次布局
                    //得到所有偏移量之和
                    groupSpanSizeOffset.valueAt(size - 1)

                }
                spanSize = SPAN_COUNT - (position + lastGroupOffset) % SPAN_COUNT

                if (indexOfKey < 0) {
                    //得到当前的组,和前面所有组的spanSize 偏移量之和
                    val groupOffset = lastGroupOffset + spanSize - 1
                    groupSpanSizeOffset.put(position, groupOffset)
                }
            }
            return spanSize
        }


    }

    private var subcategoryList = mutableListOf<SubCategory>()

    private fun onQuerySubCategoryListSuccess(data: List<SubCategory>) {
        if (!isAlive) return
        groupSpanSizeOffset.clear()
        subcategoryList.clear()
        decoration.clear()
        subcategoryList.addAll(data)
        if (layoutManager.spanSizeLookup != spanSizeLookup) {
            layoutManager.spanSizeLookup = spanSizeLookup
        }

        slider_view.bindContentView(itemCount = data.size,
            layoutManager = layoutManager,
            decoration = decoration,
            onBindView = { holder, position ->
                val subCategory = data[position]
                holder.findViewById<ImageView>(R.id.content_item_image)
                    .loadUrl(subCategory.subCategoryIcon)
                holder.findViewById<TextView>(R.id.content_item_title).text =
                    subCategory.subCategoryName
            }, onItemClick = { holder, position ->
                val category = data[position]
                val bundle = Bundle()
                bundle.putString("categoryId", category.categoryId)
                bundle.putString("subcategoryId", category.subCategoryId)
                bundle.putString("categoryTitle", category.subCategoryName)
                HiRoute.startActivity(context!!,bundle,HiRoute.Destination.GOODS_LIST)
            })
    }

    private fun showEmptyView() {
        if (!isAlive) return
        if (emptyView == null) {
            emptyView = EmptyView(context!!, null, 0)
            emptyView!!.setIcon(R.string.if_empty3)
            emptyView!!.setDesc(getString(R.string.lisit_empty_desc))
            emptyView!!.setButton(getString(R.string.list_empty_action),
                View.OnClickListener { v: View? -> queryCategoryList() })
            emptyView!!.setBackgroundColor(context!!.resources.getColor(R.color.color_white))
            emptyView!!.layoutParams =
                LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT
                )
        }
        emptyView?.visibility = View.VISIBLE
        slider_view.visibility = View.GONE
        root_container.addView(emptyView)
    }
}