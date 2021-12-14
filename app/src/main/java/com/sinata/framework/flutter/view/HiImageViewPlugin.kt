package com.sinata.framework.flutter.view

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry
import io.flutter.plugin.common.PluginRegistry

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/12/6
 */
object HiImageViewPlugin {

    /**
     * 1、如何获取PluginRegistry.Register
     * 2、非继承FlutterActivity时如何注册native view
     */
    fun registrarWith(registrar:PluginRegistry.Registrar){
        val viewFactory = HiImageViewControlFactory(registrar.messenger())
        registrar.platformViewRegistry().registerViewFactory("HiImageView",viewFactory)
    }


    fun registrarWith(flutterEngine: FlutterEngine){
        val shimPluginRegistry = ShimPluginRegistry(flutterEngine)
        registrarWith(shimPluginRegistry.registrarFor("HiFlutter"))
    }
}