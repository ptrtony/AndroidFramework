package com.sinata.framework.fragment.home

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.R
import com.sinata.framework.model.GoodsModel
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_ui.recyclerview.HiDataItem
import kotlinx.android.synthetic.main.layout_home_goods_list_item1.view.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company

@author jingqiang.cheng
@date 6/8/2021
 */
class GoodsItem(data: GoodsModel, val hotTab: Boolean) :
    HiDataItem<GoodsModel, RecyclerView.ViewHolder>(data) {
    val MAX_TAG_SIZE = 3
    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        if (mData == null) return
        val context = holder.itemView.context
        holder.itemView.item_image.loadUrl(mData!!.sliderImage)
        holder.itemView.item_title.text = mData!!.goodsName
        holder.itemView.item_price.text = mData!!.marketPrice
        holder.itemView.item_sale_desc.text = mData!!.completedNumText
        val itemLabelContainer = holder.itemView.item_label_container
        if (!TextUtils.isEmpty(mData!!.tags)) {
            itemLabelContainer.visibility = View.VISIBLE
            val split = mData!!.tags.split(" ")
            for (index in split.indices) {
                //0 ---- 3
                val childCount = itemLabelContainer.childCount
                if (index > MAX_TAG_SIZE -1){
                    //倒序
                    for (index in childCount - 1 downTo MAX_TAG_SIZE - 1){
                        itemLabelContainer.removeViewAt(index)
                    }
                }
                val labelTextView: TextView
                if (index > itemLabelContainer.childCount) {
                    labelTextView = createLabelView(context, index != 0)
                    itemLabelContainer.addView(labelTextView)
                } else {
                    labelTextView = itemLabelContainer.getChildAt(index) as TextView
                }
                labelTextView.text = split[index]
            }
        } else {
            itemLabelContainer.visibility = View.GONE
        }

        if (!hotTab) {
            val margin = HiDisplayUtil.dp2Px(2f)
            val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
            val parentLeft = mHiAdapter.getAttachedRecyclerView()?.left ?: 0
            val parentPaddingLeft = mHiAdapter.getAttachedRecyclerView()?.paddingLeft ?: 0
            val itemLeft = holder.itemView.left
            if (parentLeft + parentPaddingLeft == itemLeft) {
                params.rightMargin = margin
            } else {
                params.leftMargin = margin
            }
            holder.itemView.layoutParams = params
        }
    }


    private fun createLabelView(context: Context, withLeftMargin: Boolean): TextView {
        val labelTextView = TextView(context)
        labelTextView.setTextColor(ContextCompat.getColor(context, R.color.color_eed))
        labelTextView.textSize = 11f
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            HiDisplayUtil.dp2Px(14f)
        )
        params.gravity = Gravity.CENTER
        params.leftMargin = if (withLeftMargin) HiDisplayUtil.dp2Px(5f) else 0
        labelTextView.layoutParams = params
        labelTextView.setBackgroundResource(R.drawable.shape_goods_label)
        return labelTextView
    }


    override fun getSpanSize(): Int {
        return if (hotTab) super.getSpanSize() else 1
    }


    override fun getItemLayoutRes(): Int {
        return if (hotTab) R.layout.layout_home_goods_list_item1 else R.layout.layout_home_goods_list_item2
    }
}