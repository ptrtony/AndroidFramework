package com.sinata.hi_library.restful

import com.sinata.hi_library.cache.HiStorage
import com.sinata.hi_library.executor.HiExecutor
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_library.restful.annotation.CacheStrategy
import com.sinata.hi_library.utils.MainHandler

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 12/7/2021
 */

/**
 * 代理CallFactory创建出来的call对象，从而实现拦截器的派发工作
 */
class Scheduler(val callFactory: HiCall.Factory, val interceptors: MutableList<HiInterceptor>) {
    fun newCall(request: HiRequest): HiCall<*> {
        val newCall: HiCall<*> = callFactory.newCall(request)
        return ProxyCall(newCall, request)
    }


    internal inner class ProxyCall<T>(val delegate: HiCall<T>, val request: HiRequest) : HiCall<T> {
        override fun execute(): HiResponse<T> {
            dispatchInterceptor(request, null)
            val response = delegate.execute()
            dispatchInterceptor(request, response)
            return response
        }

        private fun dispatchInterceptor(request: HiRequest, response: HiResponse<T>?) {
            InterceptorChain(request, response).dispatch()
        }

        override fun enqueue(callback: HiCallback<T>) {
            dispatchInterceptor(request, null)
            if (request.cacheStrategy == CacheStrategy.CACHE_FIRST) {
                HiExecutor.executor(runnable = Runnable {
                    val cacheResponse = readCache<T>()
                    if (cacheResponse.data != null) {
                        //抛到主线程里面
                        MainHandler.sendFrontOfQueue(Runnable {
                            callback.onSuccess(cacheResponse)
                        })

                        HiLog.d("enqueue ,cache : " + request.getCacheKey())
                    }
                })

            }
            delegate.enqueue(object : HiCallback<T> {
                override fun onSuccess(response: HiResponse<T>) {
                    dispatchInterceptor(request, response)
                    saveCacheIfNeed(response)
                    callback.onSuccess(response)
                }

                override fun onFailed(throwable: Throwable) {
                    callback.onFailed(throwable)
                }

            })

        }

        private fun <T> readCache(): HiResponse<T> {
            //historage 查询缓存 需要提供一个cache_key
            //1、request 的url拼接参数
            val cacheKey = request.getCacheKey()
            val cache = HiStorage.getCache<T>(cacheKey)
            val cacheResponse = HiResponse<T>()
            cacheResponse.data = cache
            cacheResponse.code = HiResponse.CACHE_SUCCESS
            cacheResponse.msg = "缓存获取成功"
            return cacheResponse
        }

        private fun <T> saveCacheIfNeed(response: HiResponse<T>) {
            if (request.cacheStrategy == CacheStrategy.CACHE_FIRST || request.cacheStrategy == CacheStrategy.NET_CACHE && response.data != null) {
                HiStorage.saveCache(request.getCacheKey(),response.data)
            }
        }

        internal inner class InterceptorChain(
            val request: HiRequest,
            val response: HiResponse<T>?
        ) : HiInterceptor.Chain {

            //分发的是第几个拦截器
            var callIndex = 0
            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response
            }

            fun dispatch() {
                val interceptor = interceptors[callIndex]
                val intercept = interceptor.interceptor(this)
                callIndex++
                if (!intercept && callIndex < interceptors.size) {
                    dispatch()
                }
            }

        }

    }


}