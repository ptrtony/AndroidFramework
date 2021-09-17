package com.sinata.framework

import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.sinata.common.ui.component.HiBaseApplication
import com.sinata.hi_library.log.HiConsolePrinter
import com.sinata.hi_library.log.HiLogConfig
import com.sinata.hi_library.log.HiLogManager
import com.sinata.framework.lifecycleDemo.ActivityManager

/**
@author cjq
@Date 4/4/2021
@Time 7:36 PM
@Describe:
 */
class HiApplication : HiBaseApplication() {

    override fun onCreate() {
        super.onCreate()
        // 主要是添加下面这句代码
        if (BuildConfig.DEBUG){
            ARouter.openDebug()
            ARouter.openLog()
        }
        ARouter.init(this)
        ActivityManager.instance.init(this)
        MultiDex.install(this);
        HiLogManager.init(object : HiLogConfig() {
            override fun injectJsonParser(): JsonParser {
                return JsonParser { src -> Gson().toJson(src) }
            }

            override fun getGlobalConfigTag(): String {
                return "HiApplication"
            }

            override fun enable(): Boolean {
                return true
            }

            override fun includeThread(): Boolean {
                return true
            }

            override fun stackTraceDepth(): Int {
                return 5
            }
        }, HiConsolePrinter())


//        if (BuildConfig.DEBUG){
//            ARouter.openDebug()
//            ARouter.openLog()
//        }
//        ARouter.init(this)
    }

}