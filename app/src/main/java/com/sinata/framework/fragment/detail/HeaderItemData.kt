package com.sinata.framework.fragment.detail

import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.widget.ImageView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.R
import com.sinata.framework.model.DetailModel
import com.sinata.framework.model.SliderImages
import com.sinata.hi_ui.banner.core.HiBannerMo
import com.sinata.hi_ui.banner.indicator.HiNumberIndicator
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_header.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/16
 */
class HeaderItemData(
    val sliderImages: List<SliderImages>?,
    val price: String,
    val completedNumText: String,
    val goodName: String
) : HiDataItem<DetailModel, HiViewHolder>() {


    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_header
    }


    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        val bannerItems = arrayListOf<HiBannerMo>()
        sliderImages?.forEach {
            val hiBannerMo = object : HiBannerMo() {}
            hiBannerMo.bannerUrl = it.url
            bannerItems.add(hiBannerMo)
        }

        holder.hi_banner.setHiIndicator(HiNumberIndicator(context))
        holder.hi_banner.setBannerData(bannerItems)
        holder.hi_banner.setBindAdapter { viewHolder, mo, position ->
            val imageView = viewHolder.rootView as? ImageView
            imageView?.loadUrl(mo.bannerUrl)
        }

        holder.price.text = spanPrice(price)
        holder.sale_desc.text = completedNumText
        holder.title.text = goodName
    }


    private fun spanPrice(price: String): String {
        if (TextUtils.isEmpty(price)) return ""
        val ss = SpannableString(price)
        ss.setSpan(AbsoluteSizeSpan(18), 1, ss.length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return ss.toString()
    }
}