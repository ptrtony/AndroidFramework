package com.sinata.hi_library.camera

import android.content.Intent
import android.graphics.Bitmap
import androidx.annotation.Nullable
import rx.functions.Action1

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/9/18
 */
internal interface PhotoInterceptor {
    fun checkPermission()
    fun openCamera()
    fun openGallery()
    fun onRequestPermissionsResult(requestCode:Int,permissions:Array<out String>, grantResults:IntArray)
    fun onActivityResult(requestCode:Int, resultCode:Int, @Nullable data: Intent?,callback:Action1<Bitmap>)
}