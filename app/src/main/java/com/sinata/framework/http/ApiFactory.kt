package com.sinata.framework.http

import com.sinata.hi_library.utils.SPUtils
import com.sinata.hi_library.restful.HiRestful

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 14/7/2021
 */
object ApiFactory {

    val KEY_GRADLE_HTTP = "gradle_http"
    val HTTPS_BASE_URL = "https://api.devio.org/as/"
    val HTTP_BASE_URL = "http://api.devio.org/as/"
    val gradle2Http = SPUtils.getBoolean(KEY_GRADLE_HTTP)
    private val baseUrl = if (gradle2Http) HTTP_BASE_URL else HTTPS_BASE_URL
    private val hiRestful: HiRestful = HiRestful(baseUrl, RetrofitCallFactory(baseUrl))

    init {
        hiRestful.addInterceptor(BizInterceptor())
        hiRestful.addInterceptor(HttpStatusInterceptor())

        SPUtils.putBoolean(KEY_GRADLE_HTTP, false)
    }

    fun <T> create(service: Class<T>): T {
        return hiRestful.create(service)
    }
}