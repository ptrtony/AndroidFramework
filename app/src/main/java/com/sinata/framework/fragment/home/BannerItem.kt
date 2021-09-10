package com.sinata.framework.fragment.home

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.arouter.HiRoute
import com.sinata.framework.model.HomeBanner
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_ui.banner.HiBanner
import com.sinata.hi_ui.banner.core.HiBannerMo
import com.sinata.hi_ui.recyclerview.HiDataItem

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 6/8/2021
 */
class BannerItem(data:List<HomeBanner>) : HiDataItem<List<HomeBanner>,RecyclerView.ViewHolder>(data) {

    override fun onBindData(holder: RecyclerView.ViewHolder, position: Int) {
        val banner = holder.itemView as HiBanner
        val models:MutableList<HiBannerMo> = mutableListOf()
        mData?.forEach {
            val bannerMo = object:HiBannerMo(){}
            bannerMo.bannerUrl = it.cover
            models.add(bannerMo)
        }
        banner.setBannerData(models)
        banner.setOnBannerClickListener { viewHolder, mo, position ->
            HiRoute.startActivityBrowser(mData!![position].url)
        }
        banner.setBindAdapter { viewHolder, mo, position ->
            (viewHolder.rootView as ImageView).loadUrl(mo.bannerUrl)
        }

    }


    override fun getItemView(parent: ViewGroup?): View? {
        val context = parent?.context
        val banner = HiBanner(context!!)
        val layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2Px(160f))
        layoutParams.bottomMargin = HiDisplayUtil.dp2Px(10f)
        banner.layoutParams = layoutParams
        banner.setBackgroundColor(Color.WHITE)
        return banner
    }

}