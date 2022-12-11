package com.sinata.hi_ui.banner.indicator

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.sinata.hi_library.utils.HiDisplayUtil
import com.sinata.hi_ui.R

/**
@author cjq
@Date 26/4/2021
@Time 10:39 PM
@Describe:
 */
class HiCircleIndicator @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    FrameLayout(context, attrs), HiIndicator<FrameLayout> {

    companion object {
        private const val VWC = LayoutParams.WRAP_CONTENT
    }

    /**
     * 正常状态下的指示器
     */
    @DrawableRes
    private val mPointNormal = R.drawable.shape_point_normal

    /**
     * 选中状态下的指示器
     */
    @DrawableRes
    private val mPointSelected = R.drawable.shape_point_select

    /**
     * 指示器点左右间距
     */
    private var mPointLeftRightPadding: Int = 0

    /**
     * 指示器点上下内间距
     */
    private var mPointTopBottomPadding: Int = 0

    init {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(context.resources, 5f)
        mPointTopBottomPadding = HiDisplayUtil.dp2px(context.resources, 15f)
    }


    override fun get(): FrameLayout = this

    override fun onInflate(count: Int) {
        removeAllViews()
        if (count <= 0) return
        val groupView = LinearLayout(context)
        var imageView: ImageView? = null
        for (i in 0..count) {
            val params = LinearLayout.LayoutParams(VWC, VWC)
            imageView = ImageView(context)
            params.setMargins(mPointLeftRightPadding,mPointTopBottomPadding,mPointLeftRightPadding,mPointTopBottomPadding)
            params.gravity = Gravity.CENTER_VERTICAL
            imageView.layoutParams = params
            groupView.addView(imageView)
            if (i == 0){
                imageView.setImageResource(mPointSelected)
            }else{
                imageView.setImageResource(mPointNormal)
            }
        }
        val groupLayoutParams = LayoutParams(VWC,VWC)
        groupLayoutParams.gravity = Gravity.CENTER or Gravity.BOTTOM
        addView(groupView,groupLayoutParams)
    }

    override fun onPointChange(current: Int, count: Int) {
        val viewGroup = getChildAt(0) as ViewGroup
        for (i in 0 until viewGroup.childCount){
            val imageView = viewGroup.getChildAt(i) as ImageView
            if (i == current){
                imageView.setImageResource(mPointSelected)
            }else{
                imageView.setImageResource(mPointNormal)
            }
            imageView.requestLayout()
        }
    }
}