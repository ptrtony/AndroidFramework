package com.sinata.framework.fragment.detail

import com.sinata.framework.R
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/20
 */
class SimilarItem : HiDataItem<Any,HiViewHolder>() {


    override fun onBindData(holder: HiViewHolder, position: Int) {

    }

    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_similar_item
    }
}