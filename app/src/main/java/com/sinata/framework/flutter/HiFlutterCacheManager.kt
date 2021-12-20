package com.sinata.framework.flutter

import android.content.Context
import android.os.Looper
import com.sinata.framework.flutter.view.HiImageViewPlugin
import io.flutter.FlutterInjector
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/11/24
 */
class HiFlutterCacheManager private constructor() {
    /**
     *预加载Flutter
     */
    fun preLoad(context: Context) {
        //在线程空闲的时执行预加载任务
        Looper.myQueue().addIdleHandler {
            initFlutterEngine(context, MODULE_NAME_FAVORITE)
            initFlutterEngine(context, MODULE_NAME_RECOMMEND)
            false
        }
    }

    /**
     * 获取预加载的FlutterEngine
     */
    fun getCacheFlutterEngine(context: Context?, moduleName: String): FlutterEngine {
        var engine = FlutterEngineCache.getInstance()[moduleName]
        if (engine == null && context != null) {
            engine = initFlutterEngine(context, moduleName)
        }
        return engine!!
    }

    /**
     * 初始化
     */
    private fun initFlutterEngine(context: Context, moduleName: String): FlutterEngine {
        val flutterEngine = FlutterEngine(context)
        HiFlutterBridge.init(flutterEngine)
        HiImageViewPlugin.registrarWith(flutterEngine)
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint(
                FlutterInjector.instance().flutterLoader().findAppBundlePath(), moduleName
            )
        )
        FlutterEngineCache.getInstance().put(moduleName, flutterEngine)
        return flutterEngine
    }


    fun hastCached(moduleName: String):Boolean{
        return FlutterEngineCache.getInstance().contains(moduleName)
    }

    /**
     * 销毁FlutterEngine
     */
    fun destroyCached(moduleName: String){
        FlutterEngineCache.getInstance()[moduleName]?.apply {
            destroy()
        }
        FlutterEngineCache.getInstance().remove(moduleName)
    }

    companion object {

        const val MODULE_NAME_FAVORITE = "main"
        const val MODULE_NAME_RECOMMEND = "recommend"



        @JvmStatic
        @get:Synchronized
        var instance: HiFlutterCacheManager? = null
            get() {
                if (field == null) {
                    field = HiFlutterCacheManager()
                }
                return field
            }

    }

}