package com.sinata.framework.flutter.view

import android.content.Context
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.platform.PlatformView
import io.flutter.plugin.platform.PlatformViewFactory

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/12/6
 */
class  HiImageViewControlFactory constructor(private val messenger:BinaryMessenger): PlatformViewFactory(StandardMessageCodec.INSTANCE) {
    override fun create(context: Context?, viewId: Int, args: Any?): PlatformView {
        return HiImageViewControl(context,messenger,viewId,args)
    }
}