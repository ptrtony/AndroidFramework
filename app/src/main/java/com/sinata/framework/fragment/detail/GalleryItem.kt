package com.sinata.framework.fragment.detail

import android.graphics.Color
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.sinata.common.ui.view.loadUrl
import com.sinata.framework.model.DetailModel
import com.sinata.framework.model.SliderImages
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


/**
 * 详情页-商品相册
 */
class GalleryItem(val imagesImages: SliderImages) : HiDataItem<DetailModel, HiViewHolder>() {

    private var parentWidth: Int = 0

    override fun onBindData(holder: HiViewHolder, position: Int) {
        val imageView = holder.itemView as ImageView
        if (!TextUtils.isEmpty(imagesImages.url)) {
            imageView.loadUrl(imagesImages.url) {
                val drawableWidth = it.intrinsicWidth
                val drawableHeight = it.intrinsicHeight
                val params = imageView.layoutParams ?: RecyclerView.LayoutParams(
                    parentWidth,
                    RecyclerView.LayoutParams.WRAP_CONTENT
                )
                params.width = parentWidth
                params.height = (drawableHeight * (parentWidth / (drawableWidth * 1.0f))).toInt()
                imageView.layoutParams = params
                ViewCompat.setBackground(imageView,it)
            }
        }
    }

    override fun getItemView(parent: ViewGroup?): View {
        val imageView = ImageView(parent?.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setBackgroundColor(Color.WHITE)
        return imageView
    }


    override fun onViewAttachedToWindow(holder: HiViewHolder) {
        super.onViewAttachedToWindow(holder)
        val parentView = holder.itemView.parent as ViewGroup
        parentWidth = parentView.measuredWidth
        val params = holder.itemView.layoutParams
        if (params.width != parentWidth) {
            params.width = parentWidth
            params.height = parentWidth
            holder.itemView.layoutParams = params
        }
    }
}