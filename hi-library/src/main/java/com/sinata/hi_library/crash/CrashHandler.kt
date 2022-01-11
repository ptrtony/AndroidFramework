package com.sinata.hi_library.crash

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.os.Process
import android.os.StatFs
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_library.utils.ActivityManager
import com.sinata.hi_library.utils.AppGlobal
import rx.android.BuildConfig
import java.io.*
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2022/1/4
 */
object CrashHandler {
    var CRASH_DIR = "crash_dir"
    val context = AppGlobal.application
    fun init(crashDir: String) {
        Thread.setDefaultUncaughtExceptionHandler(DefaultCaughtExceptionHandler())
        CRASH_DIR = crashDir
    }

    private class DefaultCaughtExceptionHandler : Thread.UncaughtExceptionHandler {
        private val formatter = "yyyy-MM-dd HH:mm:ss"
        val sdf = SimpleDateFormat(formatter, Locale.CHINA)
        private val LAUNCH_TIME = sdf.format(Date())
        val defaultCaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()
        override fun uncaughtException(t: Thread, e: Throwable) {
            if (!handlerException(e) && defaultCaughtExceptionHandler != null) {
                defaultCaughtExceptionHandler.uncaughtException(t, e)
            }
            restartApp()
        }

        private fun restartApp() {
            val intent = context?.packageManager?.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            context?.startActivity(intent)
            Process.killProcess(Process.myPid())
            exitProcess(10)
        }

        /**
         * 设备信息、OS版本、线程名、前后台、使用时长、App版本、升级渠道
         * CPU架构、内存信息、存储信息、premission权限
         */
        private fun handlerException(e: Throwable?): Boolean {
            if (e == null) return false
            val log = collectDeviceInfo(e)
            if (BuildConfig.DEBUG) {
                HiLog.e(log)
            }
            saveCrashInfo2File(log)
            return true
        }

        private fun saveCrashInfo2File(log: String) {
            val cacheDir = File(CRASH_DIR)
            if (!cacheDir.exists()) {
                cacheDir.mkdirs()
            }
            val crashFile = File(cacheDir, sdf.format(Date()) + "-crash.txt")
            crashFile.createNewFile()
            val fos = FileOutputStream(crashFile)
            try {
                fos.write(log.toByteArray())
                fos.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                fos.close()
            }
        }

        private fun collectDeviceInfo(e: Throwable): String {
            val sb = StringBuilder()
            sb.append("brand=${Build.BOARD}\n") //huawei、xiaomi
                .append("rom=${Build.MODEL}\n")//sm-G950
                .append("os=${Build.VERSION.RELEASE}\n")//9.0
                .append("sdk=${Build.VERSION.SDK_INT}\n")//28
                .append("launch_time=${LAUNCH_TIME}\n")//启动App的时间
                .append("crash_time=${sdf.format(Date())}\n")//crash上报时间
                .append("forground=${ActivityManager.instance.front}\n")//应用处于前后台
                .append("thread=${Thread.currentThread().name}\n")//异常线程名
                .append("cpu_arch=${Build.CPU_ABI}\n")//armv7 armv8

            //app信息
            val packageManager = context?.packageManager?.getPackageInfo(context.packageName, 0)
            sb.append("version_code=${packageManager?.versionCode}\n")
                .append("version_name=${packageManager?.versionName}\n")
                .append("package_name=${packageManager?.packageName}\n")
                .append("requested_permission=${Arrays.toString(packageManager?.requestedPermissions)}\n")//已申请到那些权限

            //统计一波 存储空间信息
            val memoryInfo = android.app.ActivityManager.MemoryInfo()
            val ams =
                context?.getSystemService(Context.ACTIVITY_SERVICE) as android.app.ActivityManager
            ams.getMemoryInfo(memoryInfo)
            sb.append(
                "availMem=${
                    android.text.format.Formatter.formatFileSize(
                        context,
                        memoryInfo.availMem
                    )
                }\n"
            )//可用内存
                .append(
                    "totalMem=${
                        android.text.format.Formatter.formatFileSize(
                            context,
                            memoryInfo.totalMem
                        )
                    }\n"
                )//设备总内存
            val file = Environment.getExternalStorageDirectory()
            val statFs = StatFs(file.path)
            val availableSize = statFs.availableBlocks * statFs.blockSize
            sb.append(
                "availStorage=${
                    android.text.format.Formatter.formatFileSize(
                        context,
                        availableSize.toLong()
                    )
                }\n"
            )//存储空间
            val write: Writer = StringWriter()
            val printWriter = PrintWriter(write)
            e.printStackTrace(printWriter)
            var cause = e.cause
            while (cause != null) {
                cause.printStackTrace(printWriter)
                cause = cause.cause
            }
            printWriter.close()
            sb.append(write.toString())
            return sb.toString()
        }
    }

    fun crashFile(): Array<File>? {
        return File(context?.cacheDir, CRASH_DIR).listFiles()
    }
}