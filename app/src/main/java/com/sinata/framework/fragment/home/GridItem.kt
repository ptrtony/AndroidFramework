package com.sinata.framework.fragment.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.R
import com.sinata.framework.arouter.HiRoute
import com.sinata.framework.model.SubCategory
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.banner_item_layout.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 6/8/2021
 */
class GridItem(data: List<SubCategory>) :
    HiDataItem<List<SubCategory>, RecyclerView.ViewHolder>(data) {
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val gridView = holder.itemView as RecyclerView
        gridView.adapter = GridAdapter(mData!!)
    }

    override fun getItemView(parent: ViewGroup?): View? {
        val gridView = RecyclerView(parent!!.context)
        val params = RecyclerView.LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = HiDisplayUtil.dp2Px(10f)
        gridView.layoutParams = params
        gridView.layoutManager = GridLayoutManager(parent.context, 5)
        gridView.setBackgroundColor(Color.WHITE)
        return gridView
    }

    inner class GridAdapter(val data:List<SubCategory>) : RecyclerView.Adapter<HiViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):HiViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_home_op_grid_item,parent,false)
            return HiViewHolder(view)
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            holder.iv_image.loadUrl(data[position].subCategoryIcon)
            holder.tv_title.text = data[position].subCategoryName

            holder.itemView.setOnClickListener {
                val category = data[position]
                val bundle = Bundle()
                bundle.putString("categoryId", category.categoryId)
                bundle.putString("subcategoryId", category.subCategoryId)
                bundle.putString("categoryTitle", category.subCategoryName)
                HiRoute.startActivity(holder.itemView.context,bundle, HiRoute.Destination.GOODS_LIST)
            }
        }

        override fun getItemCount(): Int = data.size

    }


//    inner class GridViewHolder constructor(itemView:View) : RecyclerView.ViewHolder(itemView){
//        val ivImage = itemView.findViewById<ImageView>(R.id.item_image)
//        val tvTitle = itemView.findViewById<TextView>(R.id.item_title)
//    }
}