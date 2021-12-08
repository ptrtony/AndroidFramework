package com.sinata.hi_ui.iconfont

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

/**
@author jingqiang.cheng
@date 17/7/2021
 */

/**
 * 用以支持全局iconfont资源的引用，可以在布局文件中直接设置 text
 */
class IconFontTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    init {
        val typeface = Typeface.createFromAsset(context.assets, "fonts/iconfont.ttf")
        setTypeface(typeface)
    }

}