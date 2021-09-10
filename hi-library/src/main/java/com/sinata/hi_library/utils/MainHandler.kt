package com.sinata.hi_library.utils

import android.os.Handler
import android.os.Looper
import android.os.Message

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/6
 */

object MainHandler {
    private var handler = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelayed(runnable: Runnable, delayMillis: Long) {
        handler.postDelayed(runnable, delayMillis)
    }

    fun sendFrontOfQueue(runnable: Runnable){
        val message = Message.obtain(handler,runnable)
        handler.sendMessageAtFrontOfQueue(message)
    }


}