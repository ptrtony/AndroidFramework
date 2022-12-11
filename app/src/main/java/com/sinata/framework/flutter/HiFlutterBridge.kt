package com.sinata.framework.flutter

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/11/27
 */
class HiFlutterBridge : IHiBridge<Any?,MethodChannel.Result>,MethodChannel.MethodCallHandler{

    companion object{
        private var methodChannels = mutableListOf<MethodChannel>()
        @JvmStatic
        var instance : HiFlutterBridge?=null
            private set

        @JvmStatic
        fun init(flutterEngine: FlutterEngine) : HiFlutterBridge{
            val methodChannel = MethodChannel(flutterEngine.dartExecutor,"HiFlutterBridge")
            if (instance == null){
                HiFlutterBridge().also { instance = it}
            }
            methodChannel.setMethodCallHandler(instance)
            //因为多个FlutterEngine后每个FlutterEngine需要单独注册一个MethodChannel,所以用集合将所有的MethodChannel保存起来
            methodChannels.add(methodChannel)
            return instance!!
        }
    }



    fun fire(methodName:String,arguments:Any?){
        methodChannels.forEach {
            it.invokeMethod(methodName,arguments)
        }
    }

    fun fire(methodName:String,arguments: Any?,result: MethodChannel.Result){
        methodChannels.forEach {
            it.invokeMethod(methodName,arguments, result)
        }
    }
    override fun onBack(p: Any?) {

    }

    override fun gotoNative(p: Any?) {
        if (p is Map<*,*>){
            val action = p["action"]
            if (action == "gotoDetail"){
                val goodsId = p["goodsId"]
            }
        }
    }

    override fun getHeaderParams(callback: MethodChannel.Result) {

    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        when(call.method){
            "onBack" -> onBack(call.arguments)
            "getHeaderParams" -> getHeaderParams(result)
            "gotoNative" -> gotoNative(call.arguments)
            else -> result.notImplemented()
        }
    }
}