package com.sinata.hi_library.utils

import android.os.Build
import android.text.TextUtils

/**

Title:适配折叠设备
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 2022/1/13
 */
class FoldableDeviceUtil {
    //1.官方没有给我们提供api
    //2.只能去检测针对的机型
    val application = AppGlobal.get()!!
    fun isFold(): Boolean {
        return if (TextUtils.equals(Build.BRAND,"samsung") && TextUtils.equals(Build.DEVICE,"Galaxy Z Fold2")){
            return HiDisplayUtil.getDisplayWidthInPx(application) != 1780
        }else if (TextUtils.equals(Build.BRAND,"huawei") && TextUtils.equals(Build.DEVICE,"MateX")){
            return HiDisplayUtil.getDisplayWidthInPx(application) != 2200
        } else if (TextUtils.equals(Build.BRAND,"google") && TextUtils.equals(Build.DEVICE,"generic_x86")){
            return HiDisplayUtil.getDisplayWidthInPx(application) != 2200
        }else{
            return true
        }
    }
}