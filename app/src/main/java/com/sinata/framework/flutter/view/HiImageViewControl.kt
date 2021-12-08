package com.sinata.framework.flutter.view

import android.content.Context
import android.view.View
import io.flutter.plugin.platform.PlatformView

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/12/6
 */
class HiImageViewControl constructor(context: Context?, args: Any?) : PlatformView {
    val hiImageView: HiImageView = HiImageView(context!!)
    var url: String? = null

    init {
        if (args is Map<*, *>) {
            url = args["url"] as String?
        }
        hiImageView.setUrl(url)
    }

    override fun getView(): View {
        return hiImageView;
    }

    override fun dispose() {

    }
}