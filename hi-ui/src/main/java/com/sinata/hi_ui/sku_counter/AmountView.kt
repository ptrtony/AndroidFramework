package com.sinata.hi_ui.sku_counter

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/23
 */
class AmountView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private lateinit var amountValueChangeCallback: (Int) -> Unit
    private var amountValue: Int = 0
    private var amountAttrs: AmountAttrsParse.AmountAttrs = AmountAttrsParse.parseAmountAttrsAttrs(
        context, attrs, defStyleAttr
    )


    init {
        orientation = HORIZONTAL
        gravity = Gravity.CENTER
        applyAttrs()
    }


    private fun applyAttrs() {
        val increaseBtn = generateBtnView()
        increaseBtn.text = "+"
        val decreaseBtn = generateBtnView()
        decreaseBtn.text = "-"

        val amountView = generateAmountTextView()
        amountView.text = amountValue.toString()

        addView(increaseBtn)
        addView(decreaseBtn)
        addView(amountView)

        decreaseBtn.setOnClickListener {
            amountValue--
            amountView.text = amountValue.toString()
            //如果减到最小值，则不允许继续减
            decreaseBtn.isEnabled = amountValue > amountAttrs.minValue
            increaseBtn.isEnabled = true

            //通知回调，计数发生变化
            amountValueChangeCallback(amountValue)
        }

        increaseBtn.setOnClickListener {
            amountValue++
            amountView.text = amountValue.toString()
            //如果增加到最大值，则不允许继续增加
            increaseBtn.isEnabled = amountValue < amountAttrs.maxValue
            decreaseBtn.isEnabled = true

            //通知回调，计数发生变化
            amountValueChangeCallback(amountValue)
        }

    }

    private fun setAmountValueChangedCallback(amountValueChangeCallback:(Int) -> Unit) {
        this.amountValueChangeCallback = amountValueChangeCallback
    }

    private fun generateAmountTextView(): TextView {
        val textView = TextView(context)
        textView.setPadding(0, 0, 0, 0)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, amountAttrs.amountTextSize.toFloat())
        textView.setTextColor(amountAttrs.amountTextColor)
        textView.setBackgroundColor(amountAttrs.amountBackground)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.leftMargin = amountAttrs.margin
        params.rightMargin = amountAttrs.margin
        textView.layoutParams = params
        textView.gravity = Gravity.CENTER
        return textView
    }

    private fun generateBtnView(): Button {
        val button = Button(context)
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, amountAttrs.btnTextSize.toFloat())
        button.setTextColor(amountAttrs.btnTextColor)
        button.setBackgroundResource(0)
        button.includeFontPadding = false
        button.setBackgroundColor(amountAttrs.btnBackground)
        button.gravity = Gravity.CENTER
        button.layoutParams = LayoutParams(amountAttrs.btnSize, amountAttrs.btnSize)
        return button
    }
}