package com.sinata.hi_ui.sku_counter

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.TypedValue
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_ui.R

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/23
 */
object AmountAttrsParse {

    fun parseAmountAttrsAttrs(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):AmountAttrs{
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.hiAmountStyle,value,true)
        val defStyleRes = if (value.resourceId != 0) value.resourceId else R.style.AmountStyle
        val typedValue = context.obtainStyledAttributes(attrs,R.styleable.AmountView,defStyleAttr, defStyleRes)
        val btnTextSize = typedValue.getDimensionPixelSize(R.styleable.AmountView_btn_text_size,HiDisplayUtil.sp2px(14f))
        val btnColor = typedValue.getColor(R.styleable.AmountView_btn_color, Color.parseColor("#999999"))
        val btnSize = typedValue.getDimensionPixelSize(R.styleable.AmountView_btn_size,HiDisplayUtil.dp2Px(20f))
        val btnBackground = typedValue.getColor(R.styleable.AmountView_btn_background,Color.parseColor("#EEEEEE"))
        val btnMargin = typedValue.getDimensionPixelSize(R.styleable.AmountView_btn_margin,HiDisplayUtil.dp2Px(5f))
        val amountTextSize = typedValue.getDimensionPixelSize(R.styleable.AmountView_amount_text_size,HiDisplayUtil.sp2px(16f))
        val amountColor = typedValue.getColor(R.styleable.AmountView_amount_color,Color.parseColor("#000000"))
        val amountSize = typedValue.getDimensionPixelSize(R.styleable.AmountView_amount_size,HiDisplayUtil.dp2Px(20f))
        val amountBackground = typedValue.getColor(R.styleable.AmountView_amount_background,Color.WHITE)
        val count = typedValue.getInteger(R.styleable.AmountView_value,1)
        val maxValue = typedValue.getInteger(R.styleable.AmountView_max_value,100)
        val minValue = typedValue.getInteger(R.styleable.AmountView_min_value,1)
        typedValue.recycle()
        return AmountAttrs(
            btnTextSize,
            btnColor,
            btnSize,
            btnBackground,
            btnMargin,
            amountTextSize,
            amountColor,
            amountSize,
            amountBackground,
            count,
            maxValue,
            minValue)
    }


    data class AmountAttrs(
        val btnTextSize: Int,
        val btnTextColor: Int,
        val btnSize: Int,
        val margin: Int,
        val btnBackground: Int,
        val amountTextSize: Int,
        val amountTextColor: Int,
        val amountSize: Int,
        val amountBackground: Int,
        val value: Int,
        val maxValue: Int,
        val minValue: Int
    )

}