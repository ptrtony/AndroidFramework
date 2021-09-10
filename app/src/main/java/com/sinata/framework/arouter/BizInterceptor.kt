package com.sinata.framework.arouter

import android.content.Context
import android.widget.Toast
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.service.InterceptorService

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 4/6/2021
 */

@Route(path = "/profile/interceptor",priority = 1)
class BizInterceptor : InterceptorService {
    private lateinit var mContext:Context
    override fun init(context: Context) {
        mContext = context
    }

    override fun doInterceptions(postcard: Postcard, callback: InterceptorCallback) {
        val extra = postcard.extra
        when {
            (extra and ARouterFlag.FLAG_LOGIN)!=0 -> {
                callback.onInterrupt(RuntimeException("need login"))
                showDialog("need login")
            }
            (extra and ARouterFlag.FLAG_AUTHENTICATION)!=0 -> {
                callback.onInterrupt(RuntimeException("need authentication"))
                showDialog("need authentication")
            }
            (extra and ARouterFlag.FLAG_VIP)!=0 -> {
                callback.onInterrupt(RuntimeException("need vip"))
                showDialog("need vip")
            }
            else -> {
                callback.onContinue(postcard)
            }
        }
    }


    private fun showDialog(message:String){
        Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show()
    }
}