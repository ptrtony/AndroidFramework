package com.sinata.hi_library.utils

import android.widget.Toast
import androidx.annotation.IdRes
import androidx.annotation.StringRes

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/16
 */
object ToastUtils {
    private val toast = Toast.makeText(AppGlobal.get()?.applicationContext,"",Toast.LENGTH_SHORT)
    fun showShortToast(message:String?){
        if (message.isNullOrBlank())return
        toast.setText(message)
        toast.show()
    }


    fun showShortToast(@StringRes resId:Int){
        toast.setText(resId)
        toast.show()
    }

    fun showLongToast(message:String?){
        if (message.isNullOrBlank())return
        toast.setText(message)
        toast.duration = Toast.LENGTH_LONG
        toast.show()
    }


    fun showLongToast(@StringRes resId:Int){
        toast.setText(resId)
        toast.show()
    }

}