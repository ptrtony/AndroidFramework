package com.sinata.framework.fragment.detail

import android.graphics.Color

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/20
 */
object ColorUtil {
    //根据百分比 计算出 start -> end之间的中间色
    fun getCurrentColor(startColor: Int, endColor: Int, fraction: Float): Int {
        val startRed = Color.red(startColor)
        val startBlue = Color.blue(startColor)
        val startGreen = Color.green(startColor)
        val startAlpha = Color.alpha(startColor)

        val endRed = Color.red(endColor)
        val endBlue = Color.blue(endColor)
        val endGreen = Color.green(endColor)
        val endAlpha = Color.alpha(endColor)

        val newColorRed = startRed + (endRed - startRed) * fraction
        val newColorBlue = startBlue + (endBlue - startBlue) * fraction
        val newColorGreen = startGreen + (endGreen - startGreen) * fraction
        val newAlpha = startAlpha + (endAlpha - startAlpha) * fraction
        return Color.argb(
            newAlpha.toInt(),
            newColorRed.toInt(),
            newColorGreen.toInt(),
            newColorBlue.toInt()
        )
    }
}