package com.sinata.hi_library.restful.http

import com.alibaba.android.arouter.launcher.ARouter
import com.sinata.hi_library.restful.HiInterceptor
import com.sinata.hi_library.restful.HiResponse

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/7/2021
 */
class HttpStatusInterceptor:HiInterceptor {
    override fun interceptor(chain: HiInterceptor.Chain): Boolean {
        val response = chain.response()
        if (!chain.isRequestPeriod && response!=null){
            val code = response.code
            when(code){
                HiResponse.RC_NEED_LOGIN ->{ARouter.getInstance().build("/account/login").navigation()}
                HiResponse.RC_AUTH_TOKEN_EXPIRED , (HiResponse.RC_AUTH_TOKEN_INVALID) , (HiResponse.RC_USER_FORBIT) -> {
                    var helpUrl:String?= null
                    if (response.errorData!=null){
                        helpUrl = response.errorData!!["helpUrl"]
                    }
                    ARouter.getInstance()
                        .build("/degrade/global/activity")
                        .withString("degrade_title","非法访问")
                        .withString("degrade_desc",response.msg)
                        .withString("degrade_action",helpUrl)
                        .navigation()
                }
            }
        }
        return false
    }
}