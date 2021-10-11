package com.sinata.common.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.text.InputType
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.sinata.common.R


/**
@author jingqiang.cheng
@date 18/7/2021
 */

class InputItemLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet?,
    defStyleAttr: Int = 0
) : LinearLayout(context, attributeSet, defStyleAttr) {

    private var titleTextView: TextView? = null
    private lateinit var editText: EditText
    private var bottomLine: Line
    private var topLine: Line
    private var topPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var bottomPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        orientation = HORIZONTAL
        val arrays = context.obtainStyledAttributes(attributeSet, R.styleable.InputItemLayout)
        val title = arrays.getString(R.styleable.InputItemLayout_title)
        val titleStyleId = arrays.getResourceId(R.styleable.InputItemLayout_titleTextAppearance, 0)
        //解析 title资源属性
        parseTitleStyle(title, titleStyleId)
        val hint = arrays.getString(R.styleable.InputItemLayout_hint)
        val inputType = arrays.getInt(R.styleable.InputItemLayout_inputType, 0)
        val inputStyleId = arrays.getResourceId(R.styleable.InputItemLayout_inputTextAppearance, 0)
        //解析 右侧输入框资源属性
        parseInputStyle(hint, inputType, inputStyleId)
        //解析  上下分割线配置属性
        val topLineStyleId = arrays.getResourceId(R.styleable.InputItemLayout_topLineAppearance, 0)
        val bottomLineStyleId =
            arrays.getResourceId(R.styleable.InputItemLayout_bottomLineAppearance, 0)
        topLine = parseLineStyle(topLineStyleId)
        bottomLine = parseLineStyle(bottomLineStyleId)
        if (topLine.enable) {
            topPaint.alpha = 1
            topPaint.color = topLine.color
            topPaint.strokeWidth = topLine.height.toFloat()
            topPaint.style = Paint.Style.FILL_AND_STROKE
        }

        if (bottomLine.enable) {
            bottomPaint.alpha = 1
            bottomPaint.color = topLine.color
            bottomPaint.strokeWidth = bottomLine.height.toFloat()
            bottomPaint.style = Paint.Style.FILL_AND_STROKE
        }
        arrays.recycle()
    }

    private fun parseLineStyle(resId: Int): Line {
        val line = Line()
        val array = context.obtainStyledAttributes(resId, R.styleable.lineAppearance)
        line.color =
            array.getColor(R.styleable.lineAppearance_color, resources.getColor(R.color.d1d2))
        line.height = array.getDimensionPixelOffset(R.styleable.lineAppearance_height, 0)
        line.leftMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_leftMargin, 0)
        line.rightMargin = array.getDimensionPixelOffset(R.styleable.lineAppearance_rightMargin, 0)
        line.enable = array.getBoolean(R.styleable.lineAppearance_enable, false)
        array.recycle()
        return line
    }

    inner class Line {
        var color: Int = 0
        var height: Int = 0
        var leftMargin: Int = 0
        var rightMargin: Int = 0
        var enable: Boolean = false
    }

    private fun parseTitleStyle(title: String?, resId: Int) {
        val array = context.obtainStyledAttributes(resId, R.styleable.titleTextAppearance)
        val titleColor = array.getColor(
            R.styleable.titleTextAppearance_titleColor,
            resources.getColor(R.color.color_333)
        )
        val titleSize = array.getDimensionPixelSize(
            R.styleable.titleTextAppearance_titleSize,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)
        )
        val minWidth = array.getDimensionPixelOffset(
            R.styleable.titleTextAppearance_minWidth,
            applyUnit(TypedValue.COMPLEX_UNIT_DIP, 80f)
        )
        titleTextView = TextView(context)
        titleTextView?.text = title
        titleTextView?.setTextColor(titleColor)
        titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize.toFloat())
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.gravity = Gravity.CENTER_VERTICAL
        titleTextView?.layoutParams = params
        titleTextView?.gravity = Gravity.CENTER_VERTICAL
        titleTextView?.minWidth = minWidth
        addView(titleTextView)
        array.recycle()
    }

    private fun parseInputStyle(hint: String?, inputType: Int, resId: Int) {
        val array = context.obtainStyledAttributes(resId, R.styleable.inputTextAppearance)
        val hintColor = array.getColor(
            R.styleable.inputTextAppearance_hintColor,
            resources.getColor(R.color.d1d2)
        )
        val inputColor = array.getColor(
            R.styleable.inputTextAppearance_inputColor,
            resources.getColor(R.color.color_333)
        )
        val textSize = array.getDimensionPixelSize(
            R.styleable.inputTextAppearance_textSize,
            applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)
        )
        editText = EditText(context)
        editText.setPadding(0,0,0,0)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        params.weight = 1f
        params.gravity = Gravity.CENTER_VERTICAL
        editText.layoutParams = params
        editText.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize.toFloat())
        editText.background = ColorDrawable(Color.TRANSPARENT)
        editText.hint = hint
        when (inputType) {
            0 -> {
                editText.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_NORMAL
            }
            1 -> {
                editText.inputType = InputType.TYPE_CLASS_NUMBER
            }
            2 -> {
                editText.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or (InputType.TYPE_CLASS_TEXT)
            }
        }
        editText.setHintTextColor(hintColor)
        editText.setTextColor(inputColor)
        addView(editText)
        array.recycle()
    }


    fun getEditText(): EditText {
        return editText
    }


    fun getTitleText():TextView?{
        return titleTextView
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (topLine.enable) {
            canvas?.drawLine(
                topLine.leftMargin.toFloat(),
                0f,
                (measuredWidth - topLine.rightMargin).toFloat(),
                0f,
                topPaint
            )
        }
        if (bottomLine.enable) {
            canvas?.drawLine(
                bottomLine.leftMargin.toFloat(),
                (measuredHeight - bottomLine.height).toFloat(),
                (measuredWidth - bottomLine.rightMargin).toFloat(),
                (measuredHeight - bottomLine.height).toFloat(),
                bottomPaint
            )
        }
    }


    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, resources.displayMetrics).toInt()
    }
}