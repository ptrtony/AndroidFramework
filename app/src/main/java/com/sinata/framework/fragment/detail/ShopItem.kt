package com.sinata.framework.fragment.detail

import android.annotation.SuppressLint
import android.content.Context
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.R
import com.sinata.framework.fragment.home.GoodsItem
import com.sinata.framework.model.DetailModel
import com.sinata.framework.model.GoodsModel
import com.sinata.hi_ui.recyclerview.HiAdapter
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_goods_item.*
import kotlinx.android.synthetic.main.layout_detail_item_shop.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/17
 */

/**
 * 店铺模块 = 基础信息 + 关联商品
 */
class ShopItem(val detailModel: DetailModel) : HiDataItem<DetailModel, HiViewHolder>() {
    private val SHOP_GOODS_ITEM_SPAN_COUNT = 3
    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_shop
    }

    @SuppressLint("SetTextI18n")
    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val shop: DetailModel.Shop? = detailModel.shop
        shop?.let {
            holder.shop_logo.loadUrl(it.logo)
            holder.shop_title.text = it.name
            holder.shop_desc.text = "${it.goodsNum}件"
        }

        val evaluation: String? = shop?.evaluation
        var index = 0
        evaluation?.let {
            val serviceTags = it.split(" ")
            val tabContainer = holder.container_tags
            tabContainer.visibility = View.VISIBLE
            for (tagIndex in 0 until serviceTags.size / 2) {
                val textView = if (tagIndex < tabContainer.childCount){
                    tabContainer.getChildAt(tagIndex) as TextView
                }else{
                    val textView = TextView(context)
                    val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT)
                    params.weight = 1f
                    textView.layoutParams = params
                    textView.setTextColor(ContextCompat.getColor(context, R.color.color_666))
                    textView.textSize = 14f
                    textView.gravity = Gravity.CENTER
                    tabContainer.addView(textView)
                    textView
                }
                val serviceName = serviceTags[index]
                val serviceTag = serviceTags[index + 1]
                textView.text = spanServiceTag(context, serviceName, serviceTag)
                index += 2
            }
        }
        val flowGoods : List<GoodsModel>? = detailModel.flowGoods
        flowGoods?.let {
            val flowRecyclerView = holder.flow_recycler_view
            flowRecyclerView.visibility = View.VISIBLE
            if (flowRecyclerView.layoutManager == null){
                flowRecyclerView.layoutManager = GridLayoutManager(context,SHOP_GOODS_ITEM_SPAN_COUNT)
            }
            if (flowRecyclerView.adapter ==  null){
                flowRecyclerView.adapter = HiAdapter(context)
            }

            val dataItems = mutableListOf<GoodsItem>()
            it.forEach { goodsModel->
                dataItems.add(ShopGoodsItem(goodsModel))
            }
            val hiAdapter = (flowRecyclerView.adapter as HiAdapter)
            hiAdapter.clearItems()
            hiAdapter.addItems(dataItems, true)
        }
    }


    private inner class ShopGoodsItem(goodsModel:GoodsModel):GoodsItem(goodsModel,false){
        override fun getItemLayoutRes(): Int {
            return R.layout.layout_detail_item_goods_item
        }

        override fun onViewAttachedToWindow(holder: HiViewHolder) {
            super.onViewAttachedToWindow(holder)
            val parentGroup = holder.itemView.parent as ViewGroup
            val itemWidth = (parentGroup.measuredWidth - parentGroup.paddingLeft - parentGroup.paddingRight)
            val imageWidth = itemWidth / SHOP_GOODS_ITEM_SPAN_COUNT
            val layoutParams = holder.item_image.layoutParams
            layoutParams.width = imageWidth
            layoutParams.height = imageWidth
            holder.item_image.layoutParams = layoutParams
        }
    }


    private fun spanServiceTag(
        context: Context,
        serviceName: String,
        serviceTag: String
    ): CharSequence {
        val ss = SpannableString(serviceTag)

        val ssb = SpannableStringBuilder()
        ss.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_dd2)),
            0,
            serviceTag.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        ss.setSpan(
            BackgroundColorSpan(ContextCompat.getColor(context, R.color.color_f8e)),
            0,
            serviceTag.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        ssb.append(serviceName).append(ss)
        return ssb
    }

}