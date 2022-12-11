package com.sinata.framework.flutter.view

import android.content.Context
import android.view.View
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.platform.PlatformView

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/12/6
 */
class HiImageViewControl constructor(
    context: Context?,
    messenger: BinaryMessenger,
    id: Int,
    args: Any?
) : PlatformView, MethodChannel.MethodCallHandler {
    val hiImageView: HiImageView = HiImageView(context!!)
    val methodChannel: MethodChannel
    var url: String? = null

    init {
        methodChannel = MethodChannel(messenger, "HiImageView_$id")
        methodChannel.setMethodCallHandler(this)
        if (args is Map<*, *>) {
            url = args["url"] as String?
        }
        hiImageView.setUrl(url)
    }

    override fun getView(): View {
        return hiImageView
    }

    override fun dispose() {

    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when (call.method) {
            "setUrl" -> {
                val url = call.argument<String>("url")
                if (url != null){
                    hiImageView.setUrl(url)
                    result.success("setUrl success")
                }else{
                    result.error("-1","url cannot be null",null)
                }
            }
        }
    }
}