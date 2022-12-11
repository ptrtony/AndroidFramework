package com.sinata.hi_library.fps

import android.icu.util.TimeUnit
import android.view.Choreographer
import com.sinata.hi_library.log.HiLog

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/11
 */
internal class FrameMonitor : Choreographer.FrameCallback {
    private val choreographer = Choreographer.getInstance()
    private var frameStartTime : Long = 0 // 这个是记录上一帧到达的时间戳
    private val listeners = arrayListOf<FpsMonitor.FpsCallback>()
    private var frameCount = 0 //1s中绘制多少帧

    override fun doFrame(frameTimeNanos: Long) {
        val currentTimeMills = java.util.concurrent.TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)

        if (frameStartTime > 0){
            //计算两针之间的时间差
            val timeSpec = currentTimeMills - frameStartTime
            //fps 每秒多少帧 frame pre second
            frameCount++
            if (timeSpec > 1000){
                val fps = frameCount * 1000 / timeSpec.toDouble()
                HiLog.e("FrameMonitor",fps)
                for (listener in listeners){
                    listener.onFrame(fps)
                    frameCount = 0
                    frameStartTime = 0
                }
            }
        }else{
            frameStartTime = currentTimeMills
        }
    }

    fun start(){
        choreographer.postFrameCallback(this)
    }

    fun stop(){
        frameStartTime = 0
        listeners.clear()
        choreographer.removeFrameCallback(this)
    }

    fun addListener(callback: FpsMonitor.FpsCallback) {
        listeners.add(callback)
    }

}