package com.sinata.framework.fragment.detail

import android.view.LayoutInflater
import android.view.View
import com.sinata.common.ui.view.InputItemLayout
import com.sinata.framework.R
import com.sinata.framework.model.DetailModel
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_attr.*

/**

Title:
Description:;详情页-商品详情模块
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/19
 */


class GoodsAttrItem(val detailModel: DetailModel) : HiDataItem<DetailViewModel, HiViewHolder>() {

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_attr
    }

    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val goodsAttrs = detailModel.goodsAttr
        val attrContainer = holder.attr_container
        attrContainer.visibility = View.VISIBLE
        var index = 0
        goodsAttrs?.let {
            val iterator = it.iterator()
            while (iterator.hasNext()) {
                val hashMap = iterator.next()
                val keys = hashMap.keys
                for (key in keys) {
                    val value = hashMap[key]
                    val view = if (index < attrContainer.childCount) {
                        attrContainer.getChildAt(index) as InputItemLayout
                    } else {
                        val view = LayoutInflater.from(context)
                            .inflate(R.layout.layout_detail_item_attr_item, attrContainer, false) as InputItemLayout
                        attrContainer.addView(view)
                        view
                    }
                    view.getTitleText()?.text = key
                    view.getEditText().setText(value)
                    view.getEditText().isEnabled = false
                    index++
                }
            }
        }

        detailModel.goodsDescription?.let {
            val attrDesc = holder.attr_desc
            attrDesc.visibility = View.VISIBLE
            attrDesc.text = it
        }

    }
}