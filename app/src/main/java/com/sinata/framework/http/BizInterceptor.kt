package com.sinata.framework.http

import com.sinata.hi_library.log.HiLog
import com.sinata.hi_library.restful.HiInterceptor

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 14/7/2021
 */
class BizInterceptor:HiInterceptor {

    override fun interceptor(chain: HiInterceptor.Chain): Boolean {
        if (chain.isRequestPeriod){
            val request = chain.request()
            request.addHeader("auth-token","MTU5Mjg1MDg3NDcwNw==")
        }else if (chain.response() != null){
            HiLog.dt("BizInterceptor",chain.request().endPointUrl())
            HiLog.dt("BizInterceptor",chain.response()?.rawData)
        }

        return false
    }
}