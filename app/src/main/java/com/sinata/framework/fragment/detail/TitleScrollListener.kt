package com.sinata.framework.fragment.detail

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.sinata.hi_library.utils.HiDisplayUtil
import kotlin.math.abs
import kotlin.math.min

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/20
 */
class TitleScrollListener(val thresholdDp: Float = 100f, val callback: (Int) -> Unit) :
    RecyclerView.OnScrollListener() {
    private var lastFraction: Float = 0f
    private val thresholdPx = HiDisplayUtil.dp2Px(thresholdDp)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        //在这里面 我们需要判断列表滑动的距离 然后跟thresholdDp进行运算，计算滑动的状态
        //计算出新的颜色值 transparent ---white的渐变

        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0) ?: return
        val top = abs(viewHolder.itemView.top).toFloat()
        val fraction = top / thresholdPx
        if (lastFraction > 1f){
            lastFraction = fraction
            return
        }
        val newColor = ColorUtil.getCurrentColor(Color.TRANSPARENT, Color.WHITE, min(fraction, 1f))
        callback(newColor)
        lastFraction = fraction
    }
}