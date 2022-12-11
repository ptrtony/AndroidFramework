package com.sinata.hi_library.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/8
 */


object HiRes {
    fun getString(@StringRes id: Int): String {
        return context().getString(id)
    }


    fun getString(@StringRes id: Int, vararg formatArgs: Any): String {
        return context().getString(id, *formatArgs)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context(), id)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context(), id)
    }

    fun getColorStateList(@ColorRes id: Int): ColorStateList? {
        return ContextCompat.getColorStateList(context(), id)
    }

    private fun context(): Context {
        return AppGlobal.get() as Context
    }


}