package com.sinata.hi_library.crash

import com.sinata.hi_library.utils.AppGlobal
import java.io.File

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/7
 */
object CrashMgr {
    private const val CRASH_DIR_JAVA = "java_crash"
    private const val CRASH_DIR_NATIVE = "native_crash"

    fun init(){
        val javaCrashDir = getJavaCrashDir()
        val nativeCrashDir = getNativeCrashDir()
        NativeCrashHandler.init(nativeCrashDir.absolutePath)
        CrashHandler.init(javaCrashDir.absolutePath)
    }

    private fun getJavaCrashDir(): File {
        val javaCrashFile = File(AppGlobal.get()?.cacheDir,CRASH_DIR_JAVA)
        if (!javaCrashFile.exists()){
            javaCrashFile.mkdirs()
        }
        return javaCrashFile
    }

    private fun getNativeCrashDir():File{
        val nativeCrashFile = File(AppGlobal.get()?.cacheDir,CRASH_DIR_NATIVE)
        if (!nativeCrashFile.exists()){
            nativeCrashFile.mkdirs()
        }
        return nativeCrashFile
    }

    fun crashFiles():Array<File>{
        return getJavaCrashDir().listFiles() + getNativeCrashDir().listFiles()
    }
}