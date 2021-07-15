package com.sinata.framework.http

import com.sinata.hi_library.restful.HiRestful

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 14/7/2021
 */
object ApiFactory {
    private val baseUrl = "https://api.devio.org/as/"
    private val hiRestful:HiRestful = HiRestful(baseUrl,RetrofitCallFactory(baseUrl))
    init {
        hiRestful.addInterceptor(BizInterceptor())

    }

    fun <T> create(service:Class<T>):T{
        return hiRestful.create(service)
    }
}