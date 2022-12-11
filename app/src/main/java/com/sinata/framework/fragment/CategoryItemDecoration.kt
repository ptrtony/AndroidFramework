package com.sinata.framework.fragment

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sinata.hi_library.utils.HiDisplayUtil

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/8/2021
 */
class CategoryItemDecoration(val callback: (Int) -> String, val spanSize: Int) :
    RecyclerView.ItemDecoration() {

    val groupFirstPosition = mutableMapOf<String, Int>()
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = HiDisplayUtil.sp2px(10f).toFloat()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val adapterPosition = parent.getChildAdapterPosition(view)
        if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) return
        //2.拿到当前位置adapterPosition 对应的groupName
        val groupName = callback(adapterPosition)
        //3.拿到adapterPosition之前的groupName
        val preGroupName = if (adapterPosition > 0) callback(adapterPosition - 1) else null
        val sameGroup = TextUtils.equals(groupName, preGroupName)
        if (!sameGroup && !groupFirstPosition.containsKey(groupName)) {
            //说明当前拿到的位置adapterPosition 对应的item 就是当前组的第一个位置
            //此时 咱们存储起来，记录下来，目前是方面后面的计算，计算后面的item是不是第一个
            groupFirstPosition[groupName] = adapterPosition
        }

        val firstRowPosition = groupFirstPosition[groupName] ?: 0
        val sameRow = adapterPosition - firstRowPosition in 0 until spanSize

        if (!sameGroup || sameRow) {
            outRect.set(0, HiDisplayUtil.dp2Px(40f), 0, 0)
            return
        }

        outRect.set(0, 0, 0, 0)
    }


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val itemCount = parent.adapter!!.itemCount
        for (index in 0 until itemCount){
            val view = parent.getChildAt(index)
            val adapterPosition = parent.getChildAdapterPosition(view)
            if (adapterPosition >= parent.adapter!!.itemCount || adapterPosition < 0) return
            val groupName = callback(adapterPosition)

            val groupFirstPosition = groupFirstPosition[groupName]
            //判断当前位置，是否为分组的第一个位置
            //如果是 我们在他的第一个位置绘制标题
            if (groupFirstPosition == adapterPosition){
                val decorationBounds = Rect()
                //为了拿到当前item的左上右下的坐标信息 包含了margin以及扩展空间
                parent.getDecoratedBoundsWithMargins(view,decorationBounds)

                val textBounds = Rect()
                paint.getTextBounds(groupName,0,groupName.length,textBounds)
                c.drawText(groupName,
                    HiDisplayUtil.dp2Px(16f).toFloat(),(decorationBounds.top + textBounds.height()).toFloat(),paint)
            }
        }
    }


    fun clear(){
        groupFirstPosition.clear()
    }
}