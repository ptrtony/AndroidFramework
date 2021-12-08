package com.sinata.framework.fragment.search

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.framework.R
import com.sinata.hi_ui.recyclerview.HiAdapter
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.layout_quick_search_list_item.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/16
 */
class QuickSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = HiAdapter(context)
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

     fun bindData(keywords:List<Keyword>,callback:(Keyword) -> Unit){
        val dataItems = mutableListOf<QuickSearchItem>()
         for (keyword in keywords){
             dataItems.add(QuickSearchItem(keyword,callback))
         }
         val hiAdapter = adapter as HiAdapter
         hiAdapter.clearItems()
         hiAdapter.addItems(dataItems,false)

     }

    private inner class QuickSearchItem(val keyword:Keyword,val callback: (Keyword) -> Unit):HiDataItem<Keyword,HiViewHolder>(){

        override fun getItemLayoutRes(): Int {
            return R.layout.layout_quick_search_list_item
        }

        override fun onBindData(holder: HiViewHolder, position: Int) {
            holder.item_title.text = mData?.keyword
            holder.itemView.setOnClickListener {
                callback(keyword)
            }
        }

    }
}