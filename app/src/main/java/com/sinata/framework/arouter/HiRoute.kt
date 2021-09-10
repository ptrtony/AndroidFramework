package com.sinata.framework.arouter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.sinata.hi_library.utils.AppGlobal

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 7/8/2021
 */
object HiRoute {

    fun startActivityBrowser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        //这个的目的是为了防止部分机型上面拉不起浏览器 比如华为手机等
        intent.addCategory(Intent.CATEGORY_BROWSABLE)
        //是为了使用Application context启动activity不会报错
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        AppGlobal.get()?.startActivity(intent)
    }


    enum class Destination(val path: String) {
        GOODS_LIST("/goods/list")
    }


    fun startActivity(
        context: Context,
        bundle: Bundle,
        destination: Destination,
        requestCode: Int = -1
    ) {
        val postcard = ARouter.getInstance().build(destination.path).with(bundle)
        if (requestCode == -1 || context !is Activity) {
            postcard.navigation(context)
        } else {
            postcard.navigation(context, requestCode)
        }
    }

    fun inject(target: Any) {
        ARouter.getInstance().inject(target)
    }

}