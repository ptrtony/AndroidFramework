package com.sinata.hi_library.fps

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import com.sinata.hi_library.R
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_library.utils.ActivityManager
import com.sinata.hi_library.utils.AppGlobal
import java.text.DecimalFormat

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/11
 */
object FpsMonitor {
    private val fpsViewers = FpsViewer()
    fun toggle() {
        fpsViewers.toggle()
    }

    fun listener(callback: FpsCallback) {
        fpsViewers.frameMonitor.addListener(callback)
    }


    interface FpsCallback {
        fun onFrame(fps: Double)
    }


    private class FpsViewer {
        private var params = WindowManager.LayoutParams()
        private var isPlaying = false
        private val application: Application = AppGlobal.get()!!
        private val fpsView = LayoutInflater.from(application.applicationContext)
            .inflate(R.layout.fps_view, null, false) as TextView
        private val decimal = DecimalFormat("#.0 fps")
        private var windowManager: WindowManager? = null
        val frameMonitor = FrameMonitor()

        init {
            windowManager = application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.flags =
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
            params.format = PixelFormat.TRANSPARENT
            params.gravity = Gravity.END or Gravity.TOP
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                params.type = WindowManager.LayoutParams.TYPE_TOAST
            }

            frameMonitor.addListener(object:FpsCallback{
                override fun onFrame(fps: Double) {
                    fpsView.text = decimal.format(fps)
                }

            })
            ActivityManager.instance.addFontBackCallback(object : ActivityManager.FontBackCallback {
                override fun onChange(font: Boolean) {
                    if (font) {
                        play()
                    } else {
                        stop()
                    }
                }

            })
        }

        private fun play() {
            if (!hasOverlayPermission()) {
                startOverlaySettingActivity()
                HiLog.e("app has overlay permission")
                return
            }
            frameMonitor.start()
            if (!isPlaying) {
                isPlaying = true
                windowManager?.addView(fpsView, params)
            }

        }

        private fun startOverlaySettingActivity() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                application.startActivity(
                    Intent(
                        Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + application.packageName)
                    ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                )
            }
        }

        private fun stop() {
            frameMonitor.stop()
            if (isPlaying) {
                windowManager?.removeView(fpsView)
                isPlaying = false
            }
        }


        private fun hasOverlayPermission(): Boolean {
            return Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(
                application
            )
        }

        fun toggle() {
            if (isPlaying) {
                stop()
            } else {
                play()
            }
            isPlaying = !isPlaying
        }
    }


}
